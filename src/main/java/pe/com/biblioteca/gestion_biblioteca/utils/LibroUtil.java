package pe.com.biblioteca.gestion_biblioteca.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.dtos.LibroDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Autor;
import pe.com.biblioteca.gestion_biblioteca.models.Categoria;
import pe.com.biblioteca.gestion_biblioteca.models.Editorial;
import pe.com.biblioteca.gestion_biblioteca.models.Libro;
import pe.com.biblioteca.gestion_biblioteca.services.AutorService;
import pe.com.biblioteca.gestion_biblioteca.services.CategoriaService;
import pe.com.biblioteca.gestion_biblioteca.services.EditorialService;
import pe.com.biblioteca.gestion_biblioteca.services.LibroService;

@Component
public class LibroUtil {

    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private EditorialService editorialService;
    @Autowired
    private CategoriaService categoriaService;
    
    public int totalLibros() {
        return this.libroService.findAll().size();
    }

    public List<Libro> ultimos4Libros() {
        List<Libro> allLibros = this.libroService.findAll().stream()
                                .filter(libro -> libro.getEstado().equals("Activo"))
                                .collect(Collectors.toList());
        if (allLibros.isEmpty()) {
            return new ArrayList<>();
        }
        int totalLibros = allLibros.size();
        int inicio = Math.max(totalLibros - 4, 0); 
        return allLibros.subList(inicio, totalLibros);
    }

    public List<Libro> librosActivos() {
        List<Libro> libros = new ArrayList<>();

        for (Libro libro : this.libroService.findAll()) {
            if (libro.getEstado().equals("Activo")) {
                libros.add(libro);
            }
        }

        return libros;
    }

    public List<Libro> librosInactivos() {
        List<Libro> libros = new ArrayList<>();

        for (Libro libro : this.libroService.findAll()) {
            if (!libro.getEstado().equals("Activo")) {
                libros.add(libro);
            }
        }

        return libros;
    }

    public Libro agregarNuevoLibro(LibroDTO libroModel) {
        Editorial editorial = this.editorialService.findById(libroModel.getIdEditorial());
        Autor autor = this.autorService.findById(libroModel.getIdAutor());
        Categoria categoria = this.categoriaService.findById(libroModel.getIdCategoria());
        Libro libroToSave;

        try {
            libroToSave = new Libro
            (null, categoria,
            editorial, autor, 
            libroModel.getTitulo(), libroModel.getDescripcion(), 
            libroModel.getAnioPublicacion(), libroModel.getArchivoImagenLibro().getBytes(), 
            libroModel.getArchivoLibro().getBytes(), libroModel.getArchivoLibro().getOriginalFilename(), 
            libroModel.getArchivoLibro().getContentType(), null);

        } catch (IOException e) {
            return null;
        }

        return this.libroService.create(libroToSave);
    }

    public Libro editarLibro(LibroDTO libroModel, 
                             boolean editarImagen, boolean editarArchivo) {

        Editorial editorial = this.editorialService.findById(libroModel.getIdEditorial());
        Autor autor = this.autorService.findById(libroModel.getIdAutor());
        Categoria categoria = this.categoriaService.findById(libroModel.getIdCategoria());
        Libro libroToSave = this.libroService.findById(libroModel.getIdLibro());

        libroToSave.setTitulo(libroModel.getTitulo());
        libroToSave.setDescripcion(libroModel.getDescripcion());
        libroToSave.setAutor(autor);
        libroToSave.setEditorial(editorial);
        libroToSave.setCategoria(categoria);
        libroToSave.setAnioPublicacion(libroModel.getAnioPublicacion());

        try {

            libroToSave.setArchivoFoto((editarImagen) ? 
                        libroModel.getArchivoImagenLibro().getBytes() : libroToSave.getArchivoFoto());
            libroToSave.setArchivoLibro((editarArchivo) ? 
                        libroModel.getArchivoLibro().getBytes() : libroToSave.getArchivoLibro());
            libroToSave.setNombreArchivoLibro((editarArchivo) ? 
                        libroModel.getArchivoLibro().getOriginalFilename() : libroToSave.getNombreArchivoLibro());
            libroToSave.setTipoArchivoLibro((editarArchivo) ?
                        libroModel.getArchivoLibro().getContentType() : libroToSave.getTipoArchivoLibro());

        } catch (IOException e) {
            return null;
        }

        return this.libroService.update(libroToSave);
    }

    public void cambiarEstadoLibro(Long id) {
        this.libroService.enableDissable(id);
    }

    public List<Integer> aniosPublicacion() {
        return this.libroService.findAll().stream()
                .map(Libro::getAnioPublicacion)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

}
