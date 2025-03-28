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
        // Falta implementar
        entity.setEstado("Activo");
        return this.libroRepository.saveAndFlush(entity);
    }

    public Libro update(Libro entity) { // Actualiza todos sus datos
        // Falta implementar
        Libro libroToUpdate = this.findById(entity.getIdLibro());

        if (libroToUpdate == null) return null;

        libroToUpdate.setAnioPublicacion(entity.getAnioPublicacion());
        libroToUpdate.setArchivoFoto(entity.getArchivoFoto());
        libroToUpdate.setArchivoLibro(entity.getArchivoLibro());
        libroToUpdate.setAutor(entity.getAutor());
        libroToUpdate.setCategoria(entity.getCategoria());
        libroToUpdate.setDescripcion(entity.getDescripcion());
        libroToUpdate.setEditorial(entity.getEditorial());
        libroToUpdate.setTitulo(entity.getTitulo());

        return this.libroRepository.saveAndFlush(libroToUpdate);
    }

}
