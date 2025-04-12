package pe.com.biblioteca.gestion_biblioteca.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {

    private Long idAutor;
    private String autor;
    private String nacionalidad;
    private String estado;
    
}