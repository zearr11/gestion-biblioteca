package pe.com.biblioteca.gestion_biblioteca.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.com.biblioteca.gestion_biblioteca.dtos.UsuarioDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() { // Busca todos
        return this.usuarioRepository.findAll();
    }

    public Usuario findById(Long id) { // Busca por id
        return this.usuarioRepository.findById(id).orElse(null);
    }

    public Usuario create(Usuario entity) { // Crea el usuario
        entity.setContrasenia(passwordEncoder.encode(entity.getContrasenia()));
        return this.usuarioRepository.saveAndFlush(entity);
    }

    public Usuario updateWhitoutPassword(Usuario entity) { // Actualiza los datos menos la contraseña
        return this.usuarioRepository.saveAndFlush(entity);
    }

    public Usuario enableDissable(Long id) { // Solo actualiza su estado (Simula la eliminacion)
        Usuario usuarioToDelete = this.findById(id);
        if (usuarioToDelete == null) return null;
        String state = (usuarioToDelete.getEstado().equals("Activo")) ? "Inactivo" : "Activo";
        usuarioToDelete.setEstado(state);
        return this.usuarioRepository.saveAndFlush(usuarioToDelete);
    }

    public List<UsuarioDTO> findAllDTO() {
        List<UsuarioDTO> usuariosMapped = new ArrayList<>();

        for (Usuario usuario : this.findAll()) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();

            usuarioDTO.setIdUsuario(usuario.getIdUsuario());
            usuarioDTO.setUsername(usuario.getUsuario());
            usuarioDTO.setRol(usuario.getRol());
            usuarioDTO.setEstado(usuario.getEstado());
            usuarioDTO.setNombres(usuario.getPersona().getNombres());
            usuarioDTO.setApellidos(usuario.getPersona().getApellidos());
            usuarioDTO.setTipoDoc(usuario.getPersona().getTipoDoc());
            usuarioDTO.setNumeroDoc(usuario.getPersona().getNumeroDoc());
            usuarioDTO.setGenero(usuario.getPersona().getGenero());
            usuarioDTO.setCorreo(usuario.getPersona().getCorreo());

            usuariosMapped.add(usuarioDTO);
        }
        return usuariosMapped;
    }

}
