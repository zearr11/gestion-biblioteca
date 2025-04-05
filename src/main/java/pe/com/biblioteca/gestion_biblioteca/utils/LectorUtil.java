package pe.com.biblioteca.gestion_biblioteca.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.biblioteca.gestion_biblioteca.dtos.UsuarioDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Persona;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.services.PersonaService;
import pe.com.biblioteca.gestion_biblioteca.services.UsuarioService;

@Component
public class LectorUtil {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PersonaService personaService;

    // Registrar Nuevo Lector
    public void agregarNuevoLector(UsuarioDTO entity) {
        Persona personaToSave = new Persona(null, entity.getNombres(), entity.getApellidos(), entity.getTipoDoc(),
                                            entity.getNumeroDoc(), entity.getGenero(), entity.getCorreo(),
                                            LocalDate.now(), null);
        Usuario usuarioToSave = new Usuario(null, entity.getUsername(), entity.getContrasenia(),
                                            "LECTOR", "Activo", null);
        usuarioToSave.setPersona(this.personaService.create(personaToSave));
        this.usuarioService.create(usuarioToSave);
    }

    // Modificar lector sin contraseña
    public void modificarLector(UsuarioDTO entity) {
        Usuario lectorToUpdate = this.usuarioService.findById(entity.getIdUsuario());
        lectorToUpdate.setUsuario(entity.getUsername());
        lectorToUpdate.getPersona().setApellidos(entity.getApellidos());
        lectorToUpdate.getPersona().setCorreo(entity.getCorreo());
        lectorToUpdate.getPersona().setGenero(entity.getGenero());
        lectorToUpdate.getPersona().setNombres(entity.getNombres());
        lectorToUpdate.getPersona().setNumeroDoc(entity.getNumeroDoc());
        lectorToUpdate.getPersona().setTipoDoc(entity.getTipoDoc());
        this.usuarioService.updateWhitoutPassword(lectorToUpdate);
    }

    // Cambia el estado del lector (Simula la eliminacion)
    public void cambiarEstadoLector(Long id) {
        this.usuarioService.enableDissable(id);
    }

    public int totalLectores() {
        List<Usuario> lectores = new ArrayList<>();
        
        for (Usuario lector : this.usuarioService.findAll()) {
            if (lector.getRol().equals("LECTOR")) {
                lectores.add(lector);
            }
        }
        return lectores.size();
    }

    public List<Usuario> getAllLectores() {
        List<Usuario> lectores = new ArrayList<>();
        
        for (Usuario lector : this.usuarioService.findAll()) {
            if (lector.getRol().equals("LECTOR")) {
                lectores.add(lector);
            }
        }
        return lectores;
    }

    public List<Usuario> getAllLectoresActivos() {
        List<Usuario> lectoresActivos = new ArrayList<>();
        
        for (Usuario lector : this.getAllLectores()) {
            if (lector.getEstado().equals("Activo")) {
                lectoresActivos.add(lector);
            }
        }
        return lectoresActivos;
    }

    public List<Usuario> getAllLectoresInactivos() {
        List<Usuario> lectoresInactivos = new ArrayList<>();

        for (Usuario lector : this.getAllLectores()) {
            if (!lector.getEstado().equals("Activo")) {
                lectoresInactivos.add(lector);
            }
        }
        return lectoresInactivos;
    }
    
}
