package pe.com.biblioteca.gestion_biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.utils.LibroUtil;

@Controller
@RequestMapping("/")
public class InicioController {

    @Autowired
    private AuthService authService;
    @Autowired
    private LibroUtil libroUtil;

    @GetMapping()
    public String vistaInicio(Model model) {

        model.addAttribute("librosRecientes", libroUtil.ultimos4Libros());
        model.addAttribute("autenticacion", (authService.auth() == null));
        model.addAttribute("esAdminOusuario", (authService.auth() != null) ? 
                            ("ROLE_ADMIN".equals(authService.getRol()) || "ROLE_USUARIO".equals(authService.getRol())) 
                            : false);

        return "public/inicio";
    }
    
}
