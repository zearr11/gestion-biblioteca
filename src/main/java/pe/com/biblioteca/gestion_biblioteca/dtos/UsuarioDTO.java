package pe.com.biblioteca.gestion_biblioteca.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    
    private Long idUsuario;
    private String username;
    private String rol;
    private String estado; // No se usa
    private String contrasenia;

    private String nombres;
    private String apellidos;
    private String tipoDoc;
    private String numeroDoc;
    private String genero;
    private String correo;

}
