package pe.com.biblioteca.gestion_biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.biblioteca.gestion_biblioteca.models.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Long> {

}
