package pe.com.biblioteca.gestion_biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.biblioteca.gestion_biblioteca.models.Editorial;
import pe.com.biblioteca.gestion_biblioteca.repositories.EditorialRepository;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    public List<Editorial> findAll() { // Busca todos
        return this.editorialRepository.findAll();
    }

    public Editorial findById(Long id) { // Busca por id
        return this.editorialRepository.findById(id).orElse(null);
    }

    public Editorial create(Editorial entity) { // Crea editorial
        entity.setEstado("Activo");
        return this.editorialRepository.saveAndFlush(entity);
    }

    public Editorial update(Editorial entity) { // Actualiza sus datos
        Editorial editorialToUpdate = this.findById(entity.getIdEditorial());

        if (editorialToUpdate == null) return null;

        editorialToUpdate.setEditorial(entity.getEditorial());
        editorialToUpdate.setLibros(entity.getLibros());

        return this.editorialRepository.saveAndFlush(editorialToUpdate);
    }

}
