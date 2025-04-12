package pe.com.biblioteca.gestion_biblioteca.paginations;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.biblioteca.gestion_biblioteca.models.Editorial;
import pe.com.biblioteca.gestion_biblioteca.repositories.EditorialRepository;

@Component
public class EditorialPaginacion {

    @Autowired
    private EditorialRepository editorialRepository;

    private static final int REGISTROS_POR_FILA = 8;

    // Obtener editoriales por fila según su estado
    public List<Editorial> getEditorialesXfila(int numeroFila, String estado) {

        List<Editorial> editoriales = (estado.equals("Activo")) ?
                        editorialRepository.findAllByEstado("Activo") : editorialRepository.findAllByEstado("Inactivo");

        if (editoriales.isEmpty()) {
            return new ArrayList<>();
        }

        int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
        int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, editoriales.size());

        return new ArrayList<>(editoriales.subList(desde, hasta));
    }

    // Obtener la cantidad de filas para editoriales según su estado
    public int getCantidadFilasEditoriales(String estado) {
        int totalEditoriales = (estado.equals("Activo")) ?
                    editorialRepository.findAllByEstado("Activo").size() : editorialRepository.findAllByEstado("Inactivo").size();

        return (int) Math.ceil((double) totalEditoriales / REGISTROS_POR_FILA);
    }

    // Filtro de búsqueda para editoriales
    public List<Editorial> filtroBusquedaAllEditoriales(String estado, String filtro) {

        List<Editorial> editoriales = (estado.equals("Activo")) ?
            editorialRepository.findAllByEstado("Activo") : editorialRepository.findAllByEstado("Inactivo");

        String filtroLower = filtro.toLowerCase();

        List<Editorial> filtrados = new ArrayList<>();

        for (Editorial editorial : editoriales) {
            boolean coincide = 
                (editorial.getEditorial() != null && editorial.getEditorial().toLowerCase().contains(filtroLower)) ||
                (editorial.getIdEditorial() != null && (editorial.getIdEditorial()+"").toLowerCase().contains(filtroLower));

            if (coincide) {
                filtrados.add(editorial);
            }
        }

        return filtrados;
    }

    // Filtro de búsqueda para editoriales con paginación
    public List<Editorial> filtroBusquedaEditorial(String estado, String filtro, int numeroFila) {

        List<Editorial> lstFiltrada = filtroBusquedaAllEditoriales(estado, filtro);

        if (lstFiltrada.size() > 0) {
            int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
            int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, lstFiltrada.size());

            return lstFiltrada.subList(desde, hasta);
        }

        return lstFiltrada;
    }

    // Obtener la cantidad de filas para editoriales filtradas
    public int getCantidadFilasEditorialesFiltradas(String estado, String filtro) {

        List<Editorial> lstFiltrada = filtroBusquedaAllEditoriales(estado, filtro);

        return (int) Math.ceil((double) lstFiltrada.size() / REGISTROS_POR_FILA);
    }

}
