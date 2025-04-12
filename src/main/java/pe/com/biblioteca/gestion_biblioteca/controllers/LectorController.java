package pe.com.biblioteca.gestion_biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.com.biblioteca.gestion_biblioteca.dtos.UsuarioDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.paginations.LectoresPaginacion;
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.utils.LectorUtil;

@Controller
@RequestMapping("/lectores")
public class LectorController {

    @Autowired
    private AuthService authService;
    @Autowired
    private LectoresPaginacion lectoresPaginacion;
    @Autowired
    private LectorUtil lectorUtil;
    
    @GetMapping()
    public String verLectores(@RequestParam(name="page", defaultValue = "1") int page,
                              @RequestParam(name="estado", defaultValue = "Activo") String estado, 
                              @RequestParam(name="filtroBusqueda", defaultValue = "") String filtroBusqueda,
                              Model model) {

        if (("ROLE_LECTOR".equals(authService.getRol()))) {
            return "redirect:/";
        }

        List<Usuario> lectoresActivos = new ArrayList<>();
        List<Usuario> lectoresInactivos = new ArrayList<>();
        int cantidadFilasActivos = lectoresPaginacion.getCantidadFilasLectores("Activo");
        int cantidadFilasInactivos = lectoresPaginacion.getCantidadFilasLectores("Inactivo");

        // Si no hay filtro de busqueda
        if (filtroBusqueda.equals("")) {
            if (estado.equals("Activo")) {
                lectoresActivos = lectoresPaginacion.getLectoresXfila(page, "Activo");
                lectoresInactivos = lectoresPaginacion.getLectoresXfila(1, "Inactivo");
            }
            else {
                lectoresInactivos = lectoresPaginacion.getLectoresXfila(page, "Inactivo");
                lectoresActivos = lectoresPaginacion.getLectoresXfila(1, "Activo");
            }
        }
        else { // Si hay filtro de busqueda
            if (estado.equals("Activo")) {
                lectoresActivos = lectoresPaginacion.filtroBusquedaLector("Activo", filtroBusqueda, page);
                cantidadFilasActivos = lectoresPaginacion.getCantidadFilasLectoresFiltradas("Activo", filtroBusqueda);
                lectoresInactivos = lectoresPaginacion.getLectoresXfila(1, "Inactivo");
                if (cantidadFilasActivos == 0) {
                    model.addAttribute("noDatosFiltroActivo", "No se encontraron coincidencias.");
                }
            }
            else {
                lectoresInactivos = lectoresPaginacion.filtroBusquedaLector("Inactivo", filtroBusqueda, page);
                cantidadFilasInactivos = lectoresPaginacion.getCantidadFilasLectoresFiltradas("Inactivo", filtroBusqueda);
                lectoresActivos = lectoresPaginacion.getLectoresXfila(1, "Activo");
                if (cantidadFilasInactivos == 0) {
                    model.addAttribute("noDatosFiltroInactivo", "No se encontraron coincidencias.");
                }
            }
        }

        model.addAttribute("lectorModel", new UsuarioDTO());
        model.addAttribute("lectoresActivos", lectoresActivos);
        model.addAttribute("lectoresInactivos", lectoresInactivos);
        model.addAttribute("filasActivos", cantidadFilasActivos);
        model.addAttribute("filasInactivos", cantidadFilasInactivos);
        model.addAttribute("estado", estado);
        model.addAttribute("filtro", filtroBusqueda);
        model.addAttribute("esAdmin", ("ROLE_ADMIN".equals(authService.getRol())));

        return "admin/lectores";
    }

    @GetMapping("/registro")
    public String vistaRegistro(Model model) {
        return "public/registro-lector";
    }

    @PostMapping("/registro-lector")
    public String registroLector(@ModelAttribute UsuarioDTO usuarioModel,
                                 RedirectAttributes redirectAttributes) {
        this.lectorUtil.agregarNuevoLector(usuarioModel);
        redirectAttributes.addFlashAttribute("cuentaCreada", "¡Registro de cuenta exitoso!");
        return "redirect:/login";
    }

    @PostMapping("/modificar-lector")
    public String modificarLector(@ModelAttribute UsuarioDTO lectorModel) {
        lectorUtil.modificarLector(lectorModel);
        return "redirect:/lectores";
    }

    @PostMapping("/cambiar-estado")
    public String inhabilitarHabilitar(@RequestParam("idUsuario") Long idUsuario) {
        lectorUtil.cambiarEstadoLector(idUsuario);
        return "redirect:/lectores";
    }
    
}
