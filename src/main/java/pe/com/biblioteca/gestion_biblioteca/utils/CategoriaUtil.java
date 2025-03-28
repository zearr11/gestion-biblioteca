package pe.com.biblioteca.gestion_biblioteca.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.services.CategoriaService;

@Component
public class CategoriaUtil {

    @Autowired
    private CategoriaService categoriaService;

    public int totalCategorias() {
        return this.categoriaService.findAll().size();
    }
    
}
