package pe.com.biblioteca.gestion_biblioteca.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.biblioteca.gestion_biblioteca.models.Persona;
import pe.com.biblioteca.gestion_biblioteca.repositories.PersonaRepository;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> findAll() { // Busca todos
        return this.personaRepository.findAll();
    }

    public Persona findById(Long id) { // Busca por id
        return this.personaRepository.findById(id).orElse(null);
    }

    public Persona create(Persona entity) { // Crea la persona
        return this.personaRepository.saveAndFlush(entity);
    }

    // No se usa aun
    public Persona update(Persona entity) { // Actualiza todos sus datos

        Persona personaToUpdate = this.findById(entity.getIdPersona());

        if (personaToUpdate == null) return null;

        personaToUpdate.setNombres(entity.getNombres());
        personaToUpdate.setApellidos(entity.getApellidos());
        personaToUpdate.setCorreo(entity.getCorreo());
        personaToUpdate.setGenero(entity.getGenero());
        personaToUpdate.setNumeroDoc(entity.getNumeroDoc());
        personaToUpdate.setTipoDoc(entity.getTipoDoc());
        personaToUpdate.setUsuario(entity.getUsuario());

        return this.personaRepository.saveAndFlush(personaToUpdate);
    }

}
