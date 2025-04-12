package pe.com.biblioteca.gestion_biblioteca.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.biblioteca.gestion_biblioteca.dtos.CategoriaDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Categoria;
import pe.com.biblioteca.gestion_biblioteca.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() { // Busca todos
        return this.categoriaRepository.findAll();
    }

    public Categoria findById(Long id) { // Busca por id
        return this.categoriaRepository.findById(id).orElse(null);
    }

    public Categoria create(Categoria entity) { // Crea la categoria
        entity.setEstado("Activo");
        return this.categoriaRepository.saveAndFlush(entity);
    }

    public Categoria update(Categoria entity) { // Actualiza sus datos
        Categoria categoriaToUpdate = this.findById(entity.getIdCategoria());

        if (categoriaToUpdate == null) return null;
        categoriaToUpdate.setCategoria(entity.getCategoria());

        return this.categoriaRepository.saveAndFlush(categoriaToUpdate);
    }

    public Categoria enableDissable(Long id) { // Solo actualiza su estado (Simula la eliminacion)
        Categoria categoriaToDelete = this.findById(id);
        if (categoriaToDelete == null) return null;
        String state = (categoriaToDelete.getEstado().equals("Activo")) ? "Inactivo" : "Activo";
        categoriaToDelete.setEstado(state);
        return this.categoriaRepository.saveAndFlush(categoriaToDelete);
    }

    public List<CategoriaDTO> findAllDTO() {
        List<CategoriaDTO> categorias = new ArrayList<>();

        for (Categoria categoria : this.findAll()) {
            CategoriaDTO categoriaDTO = new CategoriaDTO();

            categoriaDTO.setIdCategoria(categoria.getIdCategoria());
            categoriaDTO.setCategoria(categoria.getCategoria());
            categoriaDTO.setEstado(categoria.getEstado());
            categorias.add(categoriaDTO);
        }
        return categorias;
    }

}
