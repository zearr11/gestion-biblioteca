package pe.com.biblioteca.gestion_biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        
        Usuario usuarioLogin = this.usuarioRepository.findByUsuario(usuario)
                            .orElseThrow(() -> new UsernameNotFoundException(""));

        if (!"Activo".equalsIgnoreCase(usuarioLogin.getEstado())) {
            throw new UsernameNotFoundException("");
        }

        return User.withUsername(usuarioLogin.getUsuario())
            .password(usuarioLogin.getContrasenia())
            .roles(usuarioLogin.getRol())
            .build();

    }
    
}
