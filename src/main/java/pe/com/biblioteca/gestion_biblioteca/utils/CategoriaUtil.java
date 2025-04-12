package pe.com.biblioteca.gestion_biblioteca.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.models.Categoria;
import pe.com.biblioteca.gestion_biblioteca.services.CategoriaService;

@Component
public class CategoriaUtil {

    @Autowired
    private CategoriaService categoriaService;

    public int totalCategorias() {
        return this.categoriaService.findAll().size();
    }

    public List<Categoria> getCategoriaActivos() {
        return this.categoriaService.findAll().stream()
            .filter(categoria -> "Activo".equalsIgnoreCase(categoria.getEstado()))
            .collect(Collectors.toList());
    }

    public List<Categoria> getCategoriaInactivos() {
        return this.categoriaService.findAll().stream()
            .filter(categoria -> "Inactivo".equalsIgnoreCase(categoria.getEstado()))
            .collect(Collectors.toList());
    }
    
}
