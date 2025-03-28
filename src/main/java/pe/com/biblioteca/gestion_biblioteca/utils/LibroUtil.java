package pe.com.biblioteca.gestion_biblioteca.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.biblioteca.gestion_biblioteca.models.Libro;
import pe.com.biblioteca.gestion_biblioteca.services.LibroService;

@Component
public class LibroUtil {

    @Autowired
    private LibroService libroService;
    
    public int totalLibros() {
        return this.libroService.findAll().size();
    }

    public List<Libro> ultimos4Libros() {
        List<Libro> allLibros = this.libroService.findAll();
        if (allLibros.isEmpty()) {
            return new ArrayList<>();
        }
        int totalLibros = allLibros.size();
        int inicio = Math.max(totalLibros - 4, 0); 
        return allLibros.subList(inicio, totalLibros);
    }

}
