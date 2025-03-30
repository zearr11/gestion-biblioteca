package pe.com.biblioteca.gestion_biblioteca.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.dtos.UsuarioDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Persona;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.services.AuthService;
import pe.com.biblioteca.gestion_biblioteca.services.PersonaService;
import pe.com.biblioteca.gestion_biblioteca.services.UsuarioService;

@Component
public class UsuarioUtil {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private AuthService authService;

    public int totalUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        for (Usuario usuario : this.usuarioService.findAll()) {
            if (usuario.getRol().equals("ADMIN") || usuario.getRol().equals("USUARIO")) {
                usuarios.add(usuario);
            }
        }
        return usuarios.size();
    }

    // Registrar Nuevo Usuario
    public void agregarNuevoUsuario(UsuarioDTO entity) {
        Persona personaToSave = new Persona(null, entity.getNombres(), entity.getApellidos(), entity.getTipoDoc(),
                                            entity.getNumeroDoc(), entity.getGenero(), entity.getCorreo(),
                                            LocalDate.now(), null);
        Usuario usuarioToSave = new Usuario(null, entity.getUsername(), entity.getContrasenia(),
                                            entity.getRol(), "Activo", null);
        usuarioToSave.setPersona(this.personaService.create(personaToSave));
        this.usuarioService.create(usuarioToSave);
    }

    // Modificar usuario sin contraseña
    public void modificarUsuario(UsuarioDTO entity) {
        Usuario usuarioToUpdate = this.usuarioService.findById(entity.getIdUsuario());
        usuarioToUpdate.setUsuario(entity.getUsername());
        usuarioToUpdate.setRol(entity.getRol());
        usuarioToUpdate.getPersona().setApellidos(entity.getApellidos());
        usuarioToUpdate.getPersona().setCorreo(entity.getCorreo());
        usuarioToUpdate.getPersona().setGenero(entity.getGenero());
        usuarioToUpdate.getPersona().setNombres(entity.getNombres());
        usuarioToUpdate.getPersona().setNumeroDoc(entity.getNumeroDoc());
        usuarioToUpdate.getPersona().setTipoDoc(entity.getTipoDoc());
        this.usuarioService.updateWhitoutPassword(usuarioToUpdate);
    }

    // Cambia el estado del usuario (Simula la eliminacion)
    public void cambiarEstadoUsuario(Long id) {
        this.usuarioService.enableDissable(id);
    }

    public List<UsuarioDTO> getAllUsuariosDTO() {
        return this.usuarioService.findAllDTO();
    }

    // Sin uso
    public UsuarioDTO getUsuarioDTOByID(Long id) {
        Usuario usuario = this.usuarioService.findById(id);
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getIdUsuario(), usuario.getUsuario(),
                            usuario.getRol(), usuario.getEstado(),"", usuario.getPersona().getNombres(),
                            usuario.getPersona().getApellidos(), usuario.getPersona().getTipoDoc(),
                            usuario.getPersona().getNumeroDoc(), usuario.getPersona().getGenero(),
                            usuario.getPersona().getCorreo());
        return usuarioDTO;
    }

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        for (Usuario usuario : this.usuarioService.findAll()) {
            if (usuario.getRol().equals("ADMIN") || usuario.getRol().equals("USUARIO")) {
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public List<Usuario> getAllUsuariosActivos() {
        List<Usuario> usuariosActivos = new ArrayList<>();
        
        for (Usuario usuario : this.getAllUsuarios()) {
            if (usuario.getEstado().equals("Activo") && 
                authService.getUsuario().getIdUsuario() != usuario.getIdUsuario()) {
                usuariosActivos.add(usuario);
            }
        }
        return usuariosActivos;
    }

    public List<Usuario> getAllUsuariosInactivos() {
        List<Usuario> usuariosInactivos = new ArrayList<>();

        for (Usuario usuario : this.getAllUsuarios()) {
            if (!usuario.getEstado().equals("Activo")) {
                usuariosInactivos.add(usuario);
            }
        }
        return usuariosInactivos;
    }
    
}
