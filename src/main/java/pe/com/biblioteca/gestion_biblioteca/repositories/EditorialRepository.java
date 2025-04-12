package pe.com.biblioteca.gestion_biblioteca.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.biblioteca.gestion_biblioteca.models.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Long> {
    // Método para buscar todas las editoriales por su estado
    List<Editorial> findAllByEstado(String estado);
    List<Editorial> findByEstado(String estado);
}



