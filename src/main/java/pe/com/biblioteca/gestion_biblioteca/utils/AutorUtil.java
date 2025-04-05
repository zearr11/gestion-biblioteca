package pe.com.biblioteca.gestion_biblioteca.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.models.Autor;
import pe.com.biblioteca.gestion_biblioteca.services.AutorService;

@Component
public class AutorUtil {
    
    @Autowired
    private AutorService autorService;

    public int totalAutores() {
        return this.autorService.findAll().size();
    }

    public List<Autor> getAutoresActivos() {
        return this.autorService.findAll().stream()
            .filter(autor -> "Activo".equalsIgnoreCase(autor.getEstado()))
            .collect(Collectors.toList());
    }

    public List<Autor> getAutoresInactivos() {
        return this.autorService.findAll().stream()
            .filter(autor -> "Inactivo".equalsIgnoreCase(autor.getEstado()))
            .collect(Collectors.toList());
    }

}
