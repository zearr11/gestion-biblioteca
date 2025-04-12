package pe.com.biblioteca.gestion_biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pe.com.biblioteca.gestion_biblioteca.models.Persona;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.repositories.PersonaRepository;
import pe.com.biblioteca.gestion_biblioteca.repositories.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Retorna null si no hay usuario autenticado
    public UserDetails auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        return (UserDetails) authentication.getPrincipal();
    }
    
    public String getRol() {
        return this.auth().getAuthorities().iterator().next().getAuthority();
    }

    public String getUsername() {
        return this.auth().getUsername();
    }

    public String getContrasenia() {
        return this.auth().getPassword();
    }

    public Usuario getUsuario() {
        return this.usuarioRepository.findByUsuario(this.getUsername()).orElse(null);
    }

    public Persona getPersona() {
        return this.personaRepository.findByUsuario(this.getUsuario()).orElse(null);
    }
    
}
