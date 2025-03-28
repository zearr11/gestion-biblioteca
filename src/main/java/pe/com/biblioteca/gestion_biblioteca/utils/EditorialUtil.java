package pe.com.biblioteca.gestion_biblioteca.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.services.EditorialService;

@Component
public class EditorialUtil {

    @Autowired
    private EditorialService editorialService;

    public int totalEditoriales() {
        return this.editorialService.findAll().size();
    }

}
