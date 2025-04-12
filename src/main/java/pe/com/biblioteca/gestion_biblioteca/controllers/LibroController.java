package pe.com.biblioteca.gestion_biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.com.biblioteca.gestion_biblioteca.dtos.LibroDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Libro;
import pe.com.biblioteca.gestion_biblioteca.paginations.LibrosPaginacion;
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.services.LibroService;
import pe.com.biblioteca.gestion_biblioteca.utils.AutorUtil;
import pe.com.biblioteca.gestion_biblioteca.utils.CategoriaUtil;
import pe.com.biblioteca.gestion_biblioteca.utils.EditorialUtil;
import pe.com.biblioteca.gestion_biblioteca.utils.LibroUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private AuthService authService;
    @Autowired
    private LibrosPaginacion librosPaginacion;
    @Autowired
    private LibroService libroService;
    @Autowired
    private LibroUtil libroUtil;
    @Autowired
    private AutorUtil autorUtil;
    @Autowired
    private EditorialUtil editorialUtil;
    @Autowired
    private CategoriaUtil categoriaUtil;
    
    @GetMapping()
    public String vistaLibros(@RequestParam(name="page", defaultValue = "1") int page,
                              @RequestParam(name="estado", defaultValue = "Activo") String estado, 
                              @RequestParam(name="filtroBusqueda", defaultValue = "") String filtroBusqueda,
                              Model model) {

        List<Libro> librosActivos = new ArrayList<>();
        List<Libro> librosInactivos = new ArrayList<>();
        int filasActivos = librosPaginacion.getCantidadFilasLibros("Activo");
        int filasInactivos = librosPaginacion.getCantidadFilasLibros("Inactivo");

        // Si no hay filtro de busqueda
        if (filtroBusqueda.equals("")) {
            if (estado.equals("Activo")) {
                librosActivos = librosPaginacion.getLibrosXFila(page, "Activo");
                librosInactivos = librosPaginacion.getLibrosXFila(1, "Inactivo");
            }
            else {
                librosInactivos = librosPaginacion.getLibrosXFila(page, "Inactivo");
                librosActivos = librosPaginacion.getLibrosXFila(1, "Activo");
            }
        }
        else { // Si hay filtro de busqueda
            if (estado.equals("Activo")) {
                librosActivos = librosPaginacion.filtroBusquedaLibro("Activo", filtroBusqueda, page);
                filasActivos = librosPaginacion.getCantidadFilasLibrosFiltradas("Activo", filtroBusqueda);
                librosInactivos = librosPaginacion.getLibrosXFila(1, "Inactivo");
                if (filasActivos == 0) {
                    model.addAttribute("noDatosFiltroActivo", "No se encontraron coincidencias.");
                }
            }
            else {
                librosInactivos = librosPaginacion.filtroBusquedaLibro("Inactivo", filtroBusqueda, page);
                filasInactivos = librosPaginacion.getCantidadFilasLibrosFiltradas("Inactivo", filtroBusqueda);
                librosActivos = librosPaginacion.getLibrosXFila(1, "Activo");
                if (filasInactivos == 0) {
                    model.addAttribute("noDatosFiltroInactivo", "No se encontraron coincidencias.");
                }
            }
        }

        //model.addAttribute("libroModel", new LibroDTO());
        model.addAttribute("librosActivos", librosActivos);
        model.addAttribute("librosInactivos", librosInactivos);
        model.addAttribute("filasActivos", filasActivos);
        model.addAttribute("filasInactivos", filasInactivos);
        model.addAttribute("estado", estado);
        model.addAttribute("filtro", filtroBusqueda);

        model.addAttribute("autores", autorUtil.getAutoresActivos());
        model.addAttribute("editoriales", editorialUtil.editorialesActivas());
        model.addAttribute("categorias", categoriaUtil.getCategoriaActivos());

        model.addAttribute("esAdmin", ("ROLE_ADMIN".equals(authService.getRol())));

        return "admin/libros";
    }

    @PostMapping("/agregar-libro")
    public String agregarNuevoLibro(@ModelAttribute LibroDTO libroModel) {
        libroUtil.agregarNuevoLibro(libroModel);
        return "redirect:/libros";
    }

    @PostMapping("/editar-libro")
    public String actualizarLibro(@ModelAttribute LibroDTO libroModel, 
                                  @RequestParam("imagenLibroSelector") String imagenLibroSelector,
                                  @RequestParam("archivoLibroSelector") String archivoLibroSelector) {

        boolean editarImagen = (imagenLibroSelector.equals("2"));
        boolean editarArchivo = (archivoLibroSelector.equals("2"));
        libroUtil.editarLibro(libroModel, editarImagen, editarArchivo);

        return "redirect:/libros";
    }

    @PostMapping("/cambiar-estado")
    public String inhabilitarHabilitar(@RequestParam("idLibro") Long idLibro) {
        libroUtil.cambiarEstadoLibro(idLibro);
        return "redirect:/libros";
    }

    @GetMapping("/descargar-libro/{id}")
    public ResponseEntity<byte[]> descargarLibro(@PathVariable Long id) {
        
        Libro libro = this.libroService.findById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(libro.getTipoArchivoLibro()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + libro.getNombreArchivoLibro() + "\"")
                .body(libro.getArchivoLibro());
    }
    
}
