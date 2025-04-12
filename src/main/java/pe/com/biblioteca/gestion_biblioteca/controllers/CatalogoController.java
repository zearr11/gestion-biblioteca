package pe.com.biblioteca.gestion_biblioteca.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.com.biblioteca.gestion_biblioteca.models.Libro;
import pe.com.biblioteca.gestion_biblioteca.paginations.LibrosPaginacion;
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.services.AutorService;
import pe.com.biblioteca.gestion_biblioteca.services.CategoriaService;
import pe.com.biblioteca.gestion_biblioteca.services.EditorialService;
import pe.com.biblioteca.gestion_biblioteca.services.LibroService;
import pe.com.biblioteca.gestion_biblioteca.utils.LibroUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private EditorialService editorialService;
    @Autowired
    private LibroUtil libroUtil;
    @Autowired
    private LibroService libroService;
    @Autowired
    private LibrosPaginacion librosPaginacion;
    
    @GetMapping()
    public String vistaCatalogo(@RequestParam(name = "palabrasClave", defaultValue = "") String palabrasClave,
                                @RequestParam(name = "selector-autor", defaultValue = "todos") String selectorAutor,
                                @RequestParam(name = "selector-categoria", defaultValue = "todos") String selectorCategoria,
                                @RequestParam(name = "selector-editorial", defaultValue = "todos") String selectorEditorial,
                                @RequestParam(name = "selector-anioPublic", defaultValue = "todos") String selectorAnio,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                Model model) {

        List<Libro> libros = this.librosPaginacion.getLibrosXFiltros(palabrasClave, 
                            selectorAutor, selectorCategoria, selectorEditorial, selectorAnio, page);
        int numeroFilas = this.librosPaginacion.catalogoFilasLibros
            (this.librosPaginacion.getLibrosAllCatalago(palabrasClave, selectorAutor, selectorCategoria, selectorEditorial, selectorAnio));

        model.addAttribute("autores", autorService.findAll());
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("editoriales", editorialService.findAll());
        model.addAttribute("anios", libroUtil.aniosPublicacion());

        model.addAttribute("selectorAnio", selectorAnio);
        model.addAttribute("selectorEditorial", selectorEditorial);
        model.addAttribute("selectorCategoria", selectorCategoria);
        model.addAttribute("selectorAutor", selectorAutor);
        model.addAttribute("palabrasClave", palabrasClave);

        model.addAttribute("libros", libros);
        model.addAttribute("numeroFilas", numeroFilas);

        model.addAttribute("autenticacion", (authService.auth() == null));
        model.addAttribute("esAdminOusuario", (authService.auth() != null) ? 
                            ("ROLE_ADMIN".equals(authService.getRol()) || "ROLE_USUARIO".equals(authService.getRol())) 
                            : false);

        return "users/catalogo";
    }

    @GetMapping("/{id}")
    public String vistaLibro(@PathVariable(name = "id") Long id, Model model) {

        Libro libroVista = this.libroService.findById(id);

        model.addAttribute("libro", libroVista);
        model.addAttribute("autenticacion", (authService.auth() == null));
        model.addAttribute("esAdminOusuario", (authService.auth() != null) ? 
                            ("ROLE_ADMIN".equals(authService.getRol()) || "ROLE_USUARIO".equals(authService.getRol())) 
                            : false);

        return "users/libro-view";
    }
    
    

}
