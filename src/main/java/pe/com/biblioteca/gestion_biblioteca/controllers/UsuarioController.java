package pe.com.biblioteca.gestion_biblioteca.controllers;

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
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.utils.UsuarioUtil;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioUtil usuarioUtil;
    @Autowired
    private AuthService authService;
    
    @GetMapping()
    public String verUsuarios(Model model) {

        if (!("ROLE_ADMIN".equals(authService.getRol()))) {
            return "redirect:/dashboard";
        }

        model.addAttribute("usuarioModel", new UsuarioDTO());
        model.addAttribute("usuariosActivos", usuarioUtil.getAllUsuariosActivos());
        model.addAttribute("usuariosInactivos", usuarioUtil.getAllUsuariosInactivos());
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
