package pe.com.biblioteca.gestion_biblioteca.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.services.AutorService;

@Component
public class AutorUtil {
    
    @Autowired
    private AutorService autorService;

    public int totalAutores() {
        return this.autorService.findAll().size();
    }

}
