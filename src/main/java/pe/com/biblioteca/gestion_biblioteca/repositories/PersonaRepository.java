package pe.com.biblioteca.gestion_biblioteca.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.biblioteca.gestion_biblioteca.models.Persona;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;


@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByUsuario(Usuario usuario);
}
