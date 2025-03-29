package pe.com.biblioteca.gestion_biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.com.biblioteca.gestion_biblioteca.dtos.UsuarioDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.paginations.UsuariosPaginacion;
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.utils.UsuarioUtil;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioUtil usuarioUtil;
    @Autowired
    private AuthService authService;
    @Autowired
    private UsuariosPaginacion paginacionUtil;
    
    @GetMapping()
    public String verUsuarios(@RequestParam(name="page", defaultValue = "1") int page,
                              @RequestParam(name="estado", defaultValue = "Activo") String estado, 
                              @RequestParam(name="filtroBusqueda", defaultValue = "") String filtroBusqueda,
                              Model model) {

        if (!("ROLE_ADMIN".equals(authService.getRol()))) {
            return "redirect:/dashboard";
        }

        List<Usuario> usuariosActivos = new ArrayList<>();
        List<Usuario> usuariosInactivos = new ArrayList<>();
        int cantidadFilasActivos = paginacionUtil.getCantidadFilasUsuarios("Activo");
        int cantidadFilasInactivos = paginacionUtil.getCantidadFilasUsuarios("Inactivo");

        // Si no hay filtro de busqueda
        if (filtroBusqueda.equals("")) {
            if (estado.equals("Activo")) {
                usuariosActivos = paginacionUtil.getUsuariosXfila(page, "Activo");
                usuariosInactivos = paginacionUtil.getUsuariosXfila(1, "Inactivo");
            }
            else {
                usuariosInactivos = paginacionUtil.getUsuariosXfila(page, "Inactivo");
                usuariosActivos = paginacionUtil.getUsuariosXfila(1, "Activo");
            }
        }
        else { // Si hay filtro de busqueda
            if (estado.equals("Activo")) {
                usuariosActivos = paginacionUtil.filtroBusquedaUsuario("Activo", filtroBusqueda, page);
                cantidadFilasActivos = paginacionUtil.getCantidadFilasUsuariosFiltradas("Activo", filtroBusqueda);
                usuariosInactivos = paginacionUtil.getUsuariosXfila(1, "Inactivo");
                if (cantidadFilasActivos == 0) {
                    model.addAttribute("noDatosFiltroActivo", "No se encontraron coincidencias.");
                }
            }
            else {
                usuariosInactivos = paginacionUtil.filtroBusquedaUsuario("Inactivo", filtroBusqueda, page);
                cantidadFilasInactivos = paginacionUtil.getCantidadFilasUsuariosFiltradas("Inactivo", filtroBusqueda);
                usuariosActivos = paginacionUtil.getUsuariosXfila(1, "Activo");
                if (cantidadFilasInactivos == 0) {
                    model.addAttribute("noDatosFiltroInactivo", "No se encontraron coincidencias.");
                }
            }
        }

        model.addAttribute("usuarioModel", new UsuarioDTO());
        model.addAttribute("usuariosActivos", usuariosActivos);
        model.addAttribute("usuariosInactivos", usuariosInactivos);
        model.addAttribute("filasActivos", cantidadFilasActivos);
        model.addAttribute("filasInactivos", cantidadFilasInactivos);
        model.addAttribute("estado", estado);
        model.addAttribute("filtro", filtroBusqueda);
        model.addAttribute("esAdmin", ("ROLE_ADMIN".equals(authService.getRol())));

        return "admin/usuarios";
    }

    @GetMapping("/obtener-usuarios")
    @ResponseBody
    public List<UsuarioDTO> getUsuarios() {
        return usuarioUtil.getAllUsuariosDTO();
    }

    @PostMapping("/agregar-usuario")
    public String agregarNuevoUsuario(@ModelAttribute UsuarioDTO usuarioModel) {
        usuarioUtil.agregarNuevoUsuario(usuarioModel);
        return "redirect:/usuarios";
    }

    @PostMapping("/modificar-usuario")
    public String modificarUsuario(@ModelAttribute UsuarioDTO usuarioModel) {
        usuarioUtil.modificarUsuario(usuarioModel);
        return "redirect:/usuarios";
    }

    @PostMapping("/cambiar-estado")
    public String inhabilitarHabilitar(@RequestParam("idUsuario") Long idUsuario) {
        usuarioUtil.cambiarEstadoUsuario(idUsuario);
        return "redirect:/usuarios";
    }
    

}
