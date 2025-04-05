package pe.com.biblioteca.gestion_biblioteca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public String verLogin(@RequestParam(value = "error", required = false) String error, 
                            Model model) {

        if ("invalido".equals(error)) {
            model.addAttribute("loginError", "Usuario y/o contraseña inválidos.");
        }

        return "public/login";
    }

}
