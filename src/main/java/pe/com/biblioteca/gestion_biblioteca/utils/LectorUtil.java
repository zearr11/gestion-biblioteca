package pe.com.biblioteca.gestion_biblioteca.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.services.UsuarioService;

@Component
public class LectorUtil {

    @Autowired
    private UsuarioService usuarioService;

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
    
}
