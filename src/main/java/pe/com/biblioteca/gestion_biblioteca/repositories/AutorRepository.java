package pe.com.biblioteca.gestion_biblioteca.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.biblioteca.gestion_biblioteca.models.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Page<Autor> findByEstado(String estado, Pageable pageable);
    long countByEstado(String estado);
    Page<Autor> findByEstadoAndAutorContainingIgnoreCase(String estado, String autor, Pageable pageable);
    long countByEstadoAndAutorContainingIgnoreCase(String estado, String autor);
}
