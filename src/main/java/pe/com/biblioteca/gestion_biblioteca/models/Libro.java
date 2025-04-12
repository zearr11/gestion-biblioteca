package pe.com.biblioteca.gestion_biblioteca.models;

import java.util.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "libro")
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_editorial")
    private Editorial editorial;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "anio_publicacion", nullable = false)
    private int anioPublicacion;

    @Column(name = "archivo_foto", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] archivoFoto;
    
    @Column(name = "archivo_libro", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] archivoLibro;

    @Column(name = "nombre_archivo_libro")
    private String nombreArchivoLibro;

    @Column(name = "tipo_archivo_libro")
    private String tipoArchivoLibro;

    @Column(name = "estado", nullable = false)
    private String estado;

    public String getFotoBase64() {
        return Base64.getEncoder().encodeToString(this.archivoFoto);
    }

}
