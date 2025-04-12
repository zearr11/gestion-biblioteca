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
import pe.com.biblioteca.gestion_biblioteca.dtos.EditorialDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Editorial;
import pe.com.biblioteca.gestion_biblioteca.paginations.EditorialPaginacion;
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.services.EditorialService;
import pe.com.biblioteca.gestion_biblioteca.utils.EditorialUtil;

@Controller
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private AuthService authService;
    @Autowired
    private EditorialService editorialService; // Servicio inyectado
    @Autowired
    private EditorialUtil editorialUtil;
    @Autowired
    private EditorialPaginacion editorialPaginacion;

    @GetMapping()
    public String vistaEditoriales(@RequestParam(name="page", defaultValue = "1") int page,
                                   @RequestParam(name="estado", defaultValue = "Activo") String estado, 
                                   @RequestParam(name="filtroBusqueda", defaultValue = "") String filtroBusqueda,
                                   Model model) {
        
        List<Editorial> editorialesActivos = editorialService.findActivos() != null ? editorialService.findActivos() : new ArrayList<>();
        List<Editorial> editorialesInactivos = editorialService.findInactivos() != null ? editorialService.findInactivos() : new ArrayList<>();
        
        int filasActivos = editorialPaginacion.getCantidadFilasEditoriales("Activo");
        int filasInactivos = editorialPaginacion.getCantidadFilasEditoriales("Inactivo");
    
        // Lógica para búsqueda y filtrado
        if (filtroBusqueda.equals("")) {
            if (estado.equals("Activo")) {
                editorialesActivos = editorialPaginacion.getEditorialesXfila(page, "Activo");
                editorialesInactivos = editorialPaginacion.getEditorialesXfila(1, "Inactivo");
            } else {
                editorialesInactivos = editorialPaginacion.getEditorialesXfila(page, "Inactivo");
                editorialesActivos = editorialPaginacion.getEditorialesXfila(1, "Activo");
            }
        } else {
            if (estado.equals("Activo")) {
                editorialesActivos = editorialPaginacion.filtroBusquedaEditorial("Activo", filtroBusqueda, page);
                filasActivos = editorialPaginacion.getCantidadFilasEditorialesFiltradas("Activo", filtroBusqueda);
                editorialesInactivos = editorialPaginacion.getEditorialesXfila(1, "Inactivo");
                if (filasActivos == 0) {
                    model.addAttribute("noDatosFiltroActivo", "No se encontraron coincidencias.");
                }
            } else {
                editorialesInactivos = editorialPaginacion.filtroBusquedaEditorial("Inactivo", filtroBusqueda, page);
                filasInactivos = editorialPaginacion.getCantidadFilasEditorialesFiltradas("Inactivo", filtroBusqueda);
                editorialesActivos = editorialPaginacion.getEditorialesXfila(1, "Activo");
                if (filasInactivos == 0) {
                    model.addAttribute("noDatosFiltroInactivo", "No se encontraron coincidencias.");
                }
            }
        }
    
        model.addAttribute("EditorialesActivos", editorialesActivos);
        model.addAttribute("EditorialesInactivos", editorialesInactivos);
        model.addAttribute("filasActivos", filasActivos);
        model.addAttribute("filasInactivos", filasInactivos);
        model.addAttribute("estado", estado);
        model.addAttribute("filtro", filtroBusqueda);
        model.addAttribute("esAdmin", ("ROLE_ADMIN".equals(authService.getRol())));

        return "admin/editorial";
    }

    // Método para mostrar el formulario de agregar editorial
    // @GetMapping("/agregar-editorial")
    // public String agregarEditorialForm(Model model) {
    //     EditorialDTO editorialDTO = new EditorialDTO(); // Crear un objeto vacío para el formulario
    //     model.addAttribute("editorialDTO", editorialDTO);  // Pasar el objeto al modelo
    //     return "admin/agregar-editorial"; // El nombre de la vista donde estará el formulario
    // }

    // Método para manejar el envío del formulario de agregar editorial
    @PostMapping("/agregar-editorial")
    public String agregarEditorial(@ModelAttribute EditorialDTO editorialDTO) {
        Editorial nuevaEditorial = new Editorial(null, editorialDTO.getEditorial(), null, null);
        this.editorialService.create(nuevaEditorial);
        return "redirect:/editoriales"; // Redirigir a la lista de editoriales después de agregar
    }

    @PostMapping("/editar-editorial")
    public String actualizarLibro(@ModelAttribute EditorialDTO editorialDTO) {
        editorialUtil.editarEditorial(editorialDTO);
        return "redirect:/editoriales";
    }

    @PostMapping("/cambiar-estado")
    public String inhabilitarHabilitar(@RequestParam("idEditorial") Long idEditorial) {
        editorialUtil.cambiarEstadoEditorial(idEditorial);
        return "redirect:/editoriales";
    }

    @GetMapping("/obtener-editoriales")
    @ResponseBody
    public List<EditorialDTO> getEditoriales() {
        return this.editorialService.findAllDTO();
    }
    
}
