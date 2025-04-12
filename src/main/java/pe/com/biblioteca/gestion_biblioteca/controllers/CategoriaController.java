package pe.com.biblioteca.gestion_biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.com.biblioteca.gestion_biblioteca.dtos.CategoriaDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Categoria;
import pe.com.biblioteca.gestion_biblioteca.paginations.CategoriaPaginacion;
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.services.CategoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
    
    @Autowired
    private AuthService authService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaPaginacion categoriaPaginacion;

    @GetMapping()
    public String vistaCategorias(@RequestParam(name="page", defaultValue = "1") int page,
                                  @RequestParam(name="estado", defaultValue = "Activo") String estado, 
                                  @RequestParam(name="filtroBusqueda", defaultValue = "") String filtroBusqueda,
                                  Model model) {

        List<Categoria> categoriasActivos = new ArrayList<>();
        List<Categoria> categoriasInactivos = new ArrayList<>();
        int filasActivos = categoriaPaginacion.getCantidadFilasCategorias("Activo");
        int filasInactivos = categoriaPaginacion.getCantidadFilasCategorias("Inactivo");

        if (filtroBusqueda.equals("")) {
            if (estado.equals("Activo")) {
                categoriasActivos = categoriaPaginacion.getCategoriasXfila(page, "Activo");
                categoriasInactivos = categoriaPaginacion.getCategoriasXfila(1, "Inactivo");
            }
            else {
                categoriasInactivos = categoriaPaginacion.getCategoriasXfila(page, "Inactivo");
                categoriasActivos = categoriaPaginacion.getCategoriasXfila(1, "Activo");
            }
        }
        else {
            if (estado.equals("Activo")) {
                categoriasActivos = categoriaPaginacion.filtroBusquedaCategoria("Activo", filtroBusqueda, page);
                filasActivos = categoriaPaginacion.getCantidadFilasCategoriasFiltradas("Activo", filtroBusqueda);
                categoriasInactivos = categoriaPaginacion.getCategoriasXfila(1, "Inactivo");
                if (filasActivos == 0) {
                    model.addAttribute("noDatosFiltroActivo", "No se encontraron coincidencias.");
                }
            } 
            else {
                categoriasInactivos = categoriaPaginacion.filtroBusquedaCategoria("Inactivo", filtroBusqueda, page);
                filasInactivos = categoriaPaginacion.getCantidadFilasCategoriasFiltradas("Inactivo", filtroBusqueda);
                categoriasActivos = categoriaPaginacion.getCategoriasXfila(1, "Activo");
                if (filasInactivos == 0) {
                    model.addAttribute("noDatosFiltroInactivo", "No se encontraron coincidencias.");
                }
            }
        }

        model.addAttribute("CategoriasActivos", categoriasActivos);
        model.addAttribute("CategoriasInactivos", categoriasInactivos);
        model.addAttribute("filasActivos", filasActivos);
        model.addAttribute("filasInactivos", filasInactivos);
        model.addAttribute("estado", estado);
        model.addAttribute("filtro", filtroBusqueda);
        model.addAttribute("esAdmin", ("ROLE_ADMIN".equals(authService.getRol())));

        return "admin/categoria";
    }

    @PostMapping("/agregar-categoria")
    public String agregarCategoria(@ModelAttribute CategoriaDTO categoriaDTO) {
        Categoria nuevaCategoria = new Categoria(null, categoriaDTO.getCategoria(), null, null);
        this.categoriaService.create(nuevaCategoria);
        return "redirect:/categorias";
    }

    @PostMapping("/editar-categoria")
    public String actualizarCategoria(@ModelAttribute CategoriaDTO categoriaDTO) {
        Categoria updateCategoria = new Categoria(categoriaDTO.getIdCategoria(), categoriaDTO.getCategoria(), null, null);
        this.categoriaService.update(updateCategoria);
        return "redirect:/categorias";
    }

    @PostMapping("/cambiar-estado")
    public String inhabilitarHabilitar(@RequestParam Long idCategoria) {
        this.categoriaService.enableDissable(idCategoria);
        return "redirect:/categorias";
    }

    @GetMapping("/obtener-categorias")
    @ResponseBody
    public List<CategoriaDTO> getCategorias() {
        return this.categoriaService.findAllDTO();
    }
    
}
