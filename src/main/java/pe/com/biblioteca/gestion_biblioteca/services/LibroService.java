package pe.com.biblioteca.gestion_biblioteca.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.biblioteca.gestion_biblioteca.models.Libro;
import pe.com.biblioteca.gestion_biblioteca.repositories.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> findAll() { // Busca todos
        return this.libroRepository.findAll();
    }

    public Libro findById(Long id) { // Busca por id
        return this.libroRepository.findById(id).orElse(null);
    }

    public Libro create(Libro entity) { // Crea el libro
        entity.setEstado("Activo");
        return this.libroRepository.saveAndFlush(entity);
    }

    public Libro update(Libro entity) { // Actualiza todos sus datos
        return this.libroRepository.saveAndFlush(entity);
    }

    public Libro enableDissable(Long id) { // Solo actualiza su estado (Simula la eliminacion)
        Libro libroToDelete = this.findById(id);
        if (libroToDelete == null) return null;
        String state = (libroToDelete.getEstado().equals("Activo")) ? "Inactivo" : "Activo";
        libroToDelete.setEstado(state);
        return this.libroRepository.saveAndFlush(libroToDelete);
    }

}
