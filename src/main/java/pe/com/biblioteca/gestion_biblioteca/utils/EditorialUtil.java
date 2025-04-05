package pe.com.biblioteca.gestion_biblioteca.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.models.Editorial;
import pe.com.biblioteca.gestion_biblioteca.services.EditorialService;

@Component
public class EditorialUtil {

    @Autowired
    private EditorialService editorialService;

    public int totalEditoriales() {
        return this.editorialService.findAll().size();
    }

    public List<Editorial> getEditorialActivos() {
        return this.editorialService.findAll().stream()
            .filter(editorial -> "Activo".equalsIgnoreCase(editorial.getEstado()))
            .collect(Collectors.toList());
    }

    public List<Editorial> getEditorialInactivos() {
        return this.editorialService.findAll().stream()
            .filter(editorial -> "Inactivo".equalsIgnoreCase(editorial.getEstado()))
            .collect(Collectors.toList());
    }

}
