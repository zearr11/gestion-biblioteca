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
import org.springframework.web.bind.annotation.ResponseBody;
import pe.com.biblioteca.gestion_biblioteca.dtos.AutorDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Autor;
import pe.com.biblioteca.gestion_biblioteca.paginations.AutoresPaginacion;
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.utils.AutorUtil;

@Controller
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AutoresPaginacion autoresPaginacion;
    @Autowired
    private AutorUtil autorUtil;

    @GetMapping()
    public String verAutores(@RequestParam(name = "page", defaultValue = "1") int page,
                             @RequestParam(name = "estado", defaultValue = "Activo") String estado,
                             @RequestParam(name = "filtroBusqueda", defaultValue = "") String filtroBusqueda,
                             Model model) {

        List<Autor> autoresActivos = new ArrayList<>();
        List<Autor> autoresInactivos = new ArrayList<>();
        int cantidadFilasActivos = autoresPaginacion.getCantidadFilasAutores("Activo");
        int cantidadFilasInactivos = autoresPaginacion.getCantidadFilasAutores("Inactivo");

        // Si no hay filtro de búsqueda
        if (filtroBusqueda.equals("")) {
            if (estado.equals("Activo")) {
                autoresActivos = autoresPaginacion.getAutoresXfila(page, "Activo");
                autoresInactivos = autoresPaginacion.getAutoresXfila(1, "Inactivo");
            } else {
                autoresInactivos = autoresPaginacion.getAutoresXfila(page, "Inactivo");
                autoresActivos = autoresPaginacion.getAutoresXfila(1, "Activo");
            }
        } else { // Si hay filtro de búsqueda
            if (estado.equals("Activo")) {
                autoresActivos = autoresPaginacion.filtroBusquedaAutor("Activo", filtroBusqueda, page);
                cantidadFilasActivos = autoresPaginacion.getCantidadFilasAutoresFiltradas("Activo", filtroBusqueda);
                autoresInactivos = autoresPaginacion.getAutoresXfila(1, "Inactivo");
                if (cantidadFilasActivos == 0) {
                    model.addAttribute("noDatosFiltroActivo", "No se encontraron coincidencias.");
                }
            } else {
                autoresInactivos = autoresPaginacion.filtroBusquedaAutor("Inactivo", filtroBusqueda, page);
                cantidadFilasInactivos = autoresPaginacion.getCantidadFilasAutoresFiltradas("Inactivo", filtroBusqueda);
                autoresActivos = autoresPaginacion.getAutoresXfila(1, "Activo");
                if (cantidadFilasInactivos == 0) {
                    model.addAttribute("noDatosFiltroInactivo", "No se encontraron coincidencias.");
                }
            }
        }

        model.addAttribute("autorModel", new AutorDTO());
        model.addAttribute("autoresActivos", autoresActivos);
        model.addAttribute("autoresInactivos", autoresInactivos);
        model.addAttribute("filasActivos", cantidadFilasActivos);
        model.addAttribute("filasInactivos", cantidadFilasInactivos);
        model.addAttribute("estado", estado);
        model.addAttribute("filtro", filtroBusqueda);
        model.addAttribute("esAdmin", ("ROLE_ADMIN".equals(authService.getRol())));

        return "admin/autores";
    }

    @PostMapping("/modificar-autor")
    public String modificarAutor(@ModelAttribute AutorDTO autorModel) {
        autorUtil.modificarAutor(autorModel);
        return "redirect:/autores";
    }

    @PostMapping("/cambiar-estado")
    public String cambiarEstado(@RequestParam("idAutor") Long idAutor) {
        autorUtil.cambiarEstadoAutor(idAutor);
        return "redirect:/autores";
    }

    @PostMapping("/agregar-autor")
    public String agregarAutor(@ModelAttribute AutorDTO autorModel) {
        autorUtil.agregarAutor(autorModel);
        return "redirect:/autores";
    }

    @GetMapping("/obtener-autores")
    @ResponseBody
    public List<AutorDTO> getAutores() {
        return autorUtil.getAllAutoresDTO();
    }
    
}