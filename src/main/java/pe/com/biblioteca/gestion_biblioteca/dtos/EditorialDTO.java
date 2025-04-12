package pe.com.biblioteca.gestion_biblioteca.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditorialDTO {

    private Long idEditorial;
    private String editorial;
    private String estado;  

}
