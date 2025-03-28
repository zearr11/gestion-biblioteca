package pe.com.biblioteca.gestion_biblioteca.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.biblioteca.gestion_biblioteca.models.Autor;
import pe.com.biblioteca.gestion_biblioteca.repositories.AutorRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> findAll() { // Busca todos
        return this.autorRepository.findAll();
    }

    public Autor findById(Long id) { // Busca por id
        return this.autorRepository.findById(id).orElse(null);
    }

    public Autor create(Autor entity) { // Crea el autor
        entity.setEstado("Activo");
        return this.autorRepository.saveAndFlush(entity);
    }

    public Autor update(Autor entity) { // Actualiza sus datos
        Autor autorToUpdate = this.findById(entity.getIdAutor());

        if (autorToUpdate == null) return null;

        autorToUpdate.setAutor(entity.getAutor());
        autorToUpdate.setNacionalidad(entity.getNacionalidad());
        autorToUpdate.setLibros(entity.getLibros());

        return this.autorRepository.saveAndFlush(autorToUpdate);
    }

}
