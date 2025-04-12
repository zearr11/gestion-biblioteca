package pe.com.biblioteca.gestion_biblioteca.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.biblioteca.gestion_biblioteca.dtos.EditorialDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Editorial;
import pe.com.biblioteca.gestion_biblioteca.services.EditorialService;

@Component
public class EditorialUtil {

    @Autowired
    private EditorialService editorialService;

    public int totalEditoriales() {
        return this.editorialService.findAll().size();
    }

    // Método para obtener solo editoriales activas
    public List<Editorial> editorialesActivas() {
        return this.editorialService.findAll().stream()
                .filter(editorial -> "Activo".equals(editorial.getEstado()))
                .collect(Collectors.toList());
    }

    public List<Editorial> editorialesInactivas() {
        return this.editorialService.findAll().stream()
                .filter(editorial -> "Inactivo".equals(editorial.getEstado()))
                .collect(Collectors.toList());
    }

    public Editorial agregarNuevaEditorial(Editorial editorial) {
        editorial.setEstado("Activo");
        return this.editorialService.create(editorial);
    }

    public Editorial editarEditorial(EditorialDTO editorial) {
        Editorial editorialToUpdate = this.editorialService.findById(editorial.getIdEditorial());
        if (editorialToUpdate == null) return null;
        
        editorialToUpdate.setEditorial(editorial.getEditorial());
        return this.editorialService.update(editorialToUpdate);
    }

    public void cambiarEstadoEditorial(Long id) {
        this.editorialService.enableDissable(id);
    }
}

