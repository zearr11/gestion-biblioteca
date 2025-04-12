package pe.com.biblioteca.gestion_biblioteca.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibroDTO {
    
    private Long idLibro;
    private MultipartFile archivoImagenLibro;
    private String titulo;
    private String descripcion;
    private Long idAutor;
    private Long idEditorial;
    private Long idCategoria;
    private int anioPublicacion;
    private MultipartFile archivoLibro;

}
