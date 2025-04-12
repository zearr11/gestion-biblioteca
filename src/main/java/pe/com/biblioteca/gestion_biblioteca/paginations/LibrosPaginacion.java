package pe.com.biblioteca.gestion_biblioteca.paginations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.models.Libro;
import pe.com.biblioteca.gestion_biblioteca.utils.LibroUtil;

@Component
public class LibrosPaginacion {
    
    @Autowired
    private LibroUtil libroUtil;

    private static final int REGISTROS_POR_FILA = 8;

    public List<Libro> getLibrosXFila(int numeroFila, String estado) {

        List<Libro> libros = (estado.equals("Activo")) ? 
                        this.libroUtil.librosActivos() : this.libroUtil.librosInactivos();
    
        if (libros.isEmpty()) {
            return new ArrayList<>();
        }

        int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
        int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, libros.size());
    
        return new ArrayList<>(libros.subList(desde, hasta));

    }

    public int getCantidadFilasLibros(String estado) {
        int totalLibros = (estado.equals("Activo")) ? 
                    this.libroUtil.librosActivos().size() : this.libroUtil.librosInactivos().size();
        
        return (int) Math.ceil((double) totalLibros / REGISTROS_POR_FILA);
    }

    public List<Libro> filtroBusquedaAllLibros(String estado, String filtro) {

        List<Libro> libros = (estado.equals("Activo")) ? 
            this.libroUtil.librosActivos() : this.libroUtil.librosInactivos();

        String filtroLower = filtro.toLowerCase();

        List<Libro> filtrados = new ArrayList<>();

        for (Libro libro : libros) {
            boolean coincide = 
                (libro.getIdLibro() != null && libro.getIdLibro().toString().toLowerCase().contains(filtroLower)) ||
                (libro.getTitulo() != null && libro.getTitulo().toLowerCase().contains(filtroLower)) ||
                ((libro.getAnioPublicacion() + "").toLowerCase().contains(filtroLower)) ||
                (libro.getAutor().getAutor() != null && libro.getAutor().getAutor().toLowerCase().contains(filtroLower)) ||
                (libro.getCategoria().getCategoria() != null && libro.getCategoria().getCategoria().toLowerCase().contains(filtroLower)) ||
                (libro.getDescripcion() != null && libro.getDescripcion().toLowerCase().contains(filtroLower)) ||
                (libro.getEditorial().getEditorial() != null && libro.getEditorial().getEditorial().toLowerCase().contains(filtroLower)) ||
                (libro.getEstado() != null && libro.getEstado().toLowerCase().contains(filtroLower));

            if (coincide) {
                filtrados.add(libro);
            }
        }

        return filtrados;
    }

    public List<Libro> filtroBusquedaLibro(String estado, String filtro, int numeroFila) {

        List<Libro> lstFiltrada = filtroBusquedaAllLibros(estado, filtro);

        if (lstFiltrada.size() > 0) {
            int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
            int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, lstFiltrada.size());

            return lstFiltrada.subList(desde, hasta);
        }

        return lstFiltrada;
    }

    public int getCantidadFilasLibrosFiltradas(String estado, String filtro) {

        List<Libro> lstFiltrada = filtroBusquedaAllLibros(estado, filtro);

        return (int) Math.ceil((double) lstFiltrada.size() / REGISTROS_POR_FILA);
    }

    public List<Libro> getLibrosXFiltros(String palabraClave, String selectorAutor, 
                                         String selectorCategoria, String selectorEditorial,
                                         String selectorAnio, int numeroFila) {

        List<Libro> libros = getLibrosAllCatalago(palabraClave, selectorAutor, selectorCategoria, selectorEditorial, selectorAnio);

        int desde = (numeroFila - 1) * 16;
        int hasta = Math.min(numeroFila * 16, libros.size());
        
        return libros.subList(desde, hasta);
    }

    public List<Libro> getLibrosAllCatalago(String palabraClave, String selectorAutor, String selectorCategoria,
                                            String selectorEditorial, String selectorAnio) {

        List<Libro> libros = this.libroUtil.librosActivos();
        
        if (palabraClave.length() > 0) {
            libros = this.catalogoFiltroPalabraClave(libros, palabraClave);
        }
        if (!selectorAutor.equals("todos")) {
            libros = this.catalogoFiltroAutor(libros, Long.parseLong(selectorAutor));
        }
        if (!selectorCategoria.equals("todos")) {
            libros = this.catalogoFiltroCategoria(libros, Long.parseLong(selectorCategoria));
        }
        if (!selectorEditorial.equals("todos")) {
            libros = this.catalogoFiltroEditorial(libros, Long.parseLong(selectorEditorial));
        }
        if (!selectorAnio.equals("todos")) {
            libros = this.catalogoFiltroAnio(libros, Integer.parseInt(selectorAnio));
        }

        return libros;
    }

    public List<Libro> catalogoFiltroPalabraClave(List<Libro> libros, String palabraClave) {
        String filtroLower = palabraClave.toLowerCase();
        List<Libro> filtrados = new ArrayList<>();
        for (Libro libro : libros) {
            boolean coincide = 
                ((libro.getAnioPublicacion() + "").toLowerCase().contains(filtroLower)) ||
                (libro.getTitulo() != null && libro.getTitulo().toLowerCase().contains(filtroLower)) ||
                (libro.getAutor().getAutor() != null && libro.getAutor().getAutor().toLowerCase().contains(filtroLower)) ||
                (libro.getCategoria().getCategoria() != null && libro.getCategoria().getCategoria().toLowerCase().contains(filtroLower)) ||
                (libro.getDescripcion() != null && libro.getDescripcion().toLowerCase().contains(filtroLower)) ||
                (libro.getEditorial().getEditorial() != null && libro.getEditorial().getEditorial().toLowerCase().contains(filtroLower));

            if (coincide) {
                filtrados.add(libro);
            }
        }
        return filtrados;
    }

    public List<Libro> catalogoFiltroAutor(List<Libro> libros, Long idAutor) {
        return libros.stream()
                .filter(libro -> libro.getAutor().getIdAutor().equals(idAutor))
                .collect(Collectors.toList());
    }

    public List<Libro> catalogoFiltroCategoria(List<Libro> libros, Long idCategoria) {
        return libros.stream()
                .filter(libro -> libro.getCategoria().getIdCategoria().equals(idCategoria))
                .collect(Collectors.toList());
    }

    public List<Libro> catalogoFiltroEditorial(List<Libro> libros, Long idEditorial) {
        return libros.stream()
                .filter(libro -> libro.getEditorial().getIdEditorial().equals(idEditorial))
                .collect(Collectors.toList());
    }

    public List<Libro> catalogoFiltroAnio(List<Libro> libros, int anio) {
        return libros.stream()
                .filter(libro -> libro.getAnioPublicacion() == anio)
                .collect(Collectors.toList());
    }

    public int catalogoFilasLibros(List<Libro> libros) {
        return (int) Math.ceil((double) libros.size() / 16);
    }

}
