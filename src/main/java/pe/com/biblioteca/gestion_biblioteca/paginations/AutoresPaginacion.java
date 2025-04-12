package pe.com.biblioteca.gestion_biblioteca.paginations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.models.Autor;
import pe.com.biblioteca.gestion_biblioteca.repositories.AutorRepository;

@Component
public class AutoresPaginacion {

    @Autowired
    private AutorRepository autorRepository;

    private static final int REGISTROS_POR_FILA = 8;

    public int getCantidadFilasAutores(String estado) {
        int totalAutores = (int) autorRepository.countByEstado(estado);
        return (int) Math.ceil((double) totalAutores / REGISTROS_POR_FILA);
    }

    public List<Autor> getAutoresXfila(int page, String estado) {
        Page<Autor> autoresPage = autorRepository.findByEstado(estado, PageRequest.of(page - 1, REGISTROS_POR_FILA));
        return autoresPage.getContent();
    }

    public List<Autor> filtroBusquedaAutor(String estado, String filtroBusqueda, int page) {
        Page<Autor> autoresPage = autorRepository.findByEstadoAndAutorContainingIgnoreCase(estado, filtroBusqueda, PageRequest.of(page - 1, REGISTROS_POR_FILA));
        return autoresPage.getContent();
    }

    public int getCantidadFilasAutoresFiltradas(String estado, String filtroBusqueda) {
        int total = (int) autorRepository.countByEstadoAndAutorContainingIgnoreCase(estado, filtroBusqueda);
        return (int) Math.ceil((double) total / REGISTROS_POR_FILA);
    }
    
}
