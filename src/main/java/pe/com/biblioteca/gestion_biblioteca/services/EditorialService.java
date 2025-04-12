package pe.com.biblioteca.gestion_biblioteca.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.biblioteca.gestion_biblioteca.dtos.EditorialDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Editorial;
import pe.com.biblioteca.gestion_biblioteca.repositories.EditorialRepository;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    public List<Editorial> findAll() { 
        return this.editorialRepository.findAll();
    }

    public List<Editorial> findActivos() { 
        return this.editorialRepository.findByEstado("Activo");
    }

    public List<Editorial> findInactivos() { 
        return this.editorialRepository.findByEstado("Inactivo");
    }

    public Editorial findById(Long id) { 
        return this.editorialRepository.findById(id).orElse(null);
    }

    public Editorial create(Editorial entity) { 
        entity.setEstado("Activo");
        return this.editorialRepository.saveAndFlush(entity);
    }

    public Editorial update(Editorial entity) { 
        return this.editorialRepository.saveAndFlush(entity);
    }

    public Editorial saveEditorial(Editorial editorial) { 
        return this.editorialRepository.save(editorial);
    }

    public Editorial enableDissable(Long id) { 
        Editorial editorialToUpdate = this.findById(id);
        if (editorialToUpdate == null) return null;
        String state = (editorialToUpdate.getEstado().equals("Activo")) ? "Inactivo" : "Activo";
        editorialToUpdate.setEstado(state);
        return this.editorialRepository.saveAndFlush(editorialToUpdate);
    }

    public List<EditorialDTO> findAllDTO() {
        List<EditorialDTO> editoriales = new ArrayList<>();

        for (Editorial editorial : this.findAll()) {
            EditorialDTO editorialDTO = new EditorialDTO();

            editorialDTO.setIdEditorial(editorial.getIdEditorial());
            editorialDTO.setEditorial(editorial.getEditorial());
            editorialDTO.setEstado(editorial.getEstado());
            editoriales.add(editorialDTO);
        }
        return editoriales;
    }
}
