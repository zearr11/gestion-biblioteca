package pe.com.biblioteca.gestion_biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.utils.AutorUtil;
import pe.com.biblioteca.gestion_biblioteca.utils.CategoriaUtil;
import pe.com.biblioteca.gestion_biblioteca.utils.EditorialUtil;
import pe.com.biblioteca.gestion_biblioteca.utils.LectorUtil;
import pe.com.biblioteca.gestion_biblioteca.utils.LibroUtil;
import pe.com.biblioteca.gestion_biblioteca.utils.UsuarioUtil;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private EditorialUtil editorialUtil;
    @Autowired
    private UsuarioUtil usuarioUtil;
    @Autowired
    private LectorUtil lectorUtil;
    @Autowired
    private LibroUtil libroUtil;
    @Autowired
    private CategoriaUtil categoriaUtil;
    @Autowired
    private AutorUtil autorUtil;
    @Autowired
    private AuthService authService;
    
    @GetMapping()
    public String verDashboard(Model model) {

        model.addAttribute("totalUsuarios", usuarioUtil.totalUsuarios()-1);
        model.addAttribute("totalLectores", lectorUtil.totalLectores());
        model.addAttribute("totalLibros", libroUtil.totalLibros());
        model.addAttribute("totalCategorias", categoriaUtil.totalCategorias());
        model.addAttribute("totalAutores", autorUtil.tAutores());
        model.addAttribute("totalEditoriales", editorialUtil.totalEditoriales());
        model.addAttribute("esAdmin", ("ROLE_ADMIN".equals(authService.getRol())));

        return "admin/dashboard";
    }

}
