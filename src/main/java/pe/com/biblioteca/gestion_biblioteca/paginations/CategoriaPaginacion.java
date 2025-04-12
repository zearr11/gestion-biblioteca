package pe.com.biblioteca.gestion_biblioteca.paginations;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.models.Categoria;
import pe.com.biblioteca.gestion_biblioteca.utils.CategoriaUtil;

@Component
public class CategoriaPaginacion {

    @Autowired
    private CategoriaUtil categoriaUtil;

    private static final int REGISTROS_POR_FILA = 8;

    public List<Categoria> getCategoriasXfila(int numeroFila, String estado) {

        List<Categoria> categorias = (estado.equals("Activo")) ?
            categoriaUtil.getCategoriaActivos() : categoriaUtil.getCategoriaInactivos();

        if (categorias.isEmpty()) {
            return new ArrayList<>();
        }

        int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
        int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, categorias.size());

        return new ArrayList<>(categorias.subList(desde, hasta));
    }

    public int getCantidadFilasCategorias(String estado) {
        int totalCategorias = (estado.equals("Activo")) ?
                categoriaUtil.getCategoriaActivos().size() : categoriaUtil.getCategoriaInactivos().size();

        return (int) Math.ceil((double) totalCategorias / REGISTROS_POR_FILA);
    }

    public List<Categoria> filtroBusquedaAllCategorias(String estado, String filtro) {

        List<Categoria> categorias = (estado.equals("Activo")) ?
                categoriaUtil.getCategoriaActivos() : categoriaUtil.getCategoriaInactivos();

        String filtroLower = filtro.toLowerCase();

        List<Categoria> filtrados = new ArrayList<>();

        for (Categoria categoria : categorias) {
            boolean coincide = 
                (categoria.getIdCategoria() != null && (categoria.getIdCategoria()+"").toLowerCase().contains(filtroLower)) ||
                (categoria.getCategoria() != null && categoria.getCategoria().toLowerCase().contains(filtroLower));

            if (coincide) {
                filtrados.add(categoria);
            }
        }

        return filtrados;
    }

    public List<Categoria> filtroBusquedaCategoria(String estado, String filtro, int numeroFila) {

        List<Categoria> lstFiltrada = filtroBusquedaAllCategorias(estado, filtro);

        if (lstFiltrada.size() > 0) {
            int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
            int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, lstFiltrada.size());

            return lstFiltrada.subList(desde, hasta);
        }

        return lstFiltrada;
    }

    public int getCantidadFilasCategoriasFiltradas(String estado, String filtro) {

        List<Categoria> lstFiltrada = filtroBusquedaAllCategorias(estado, filtro);

        return (int) Math.ceil((double) lstFiltrada.size() / REGISTROS_POR_FILA);
    }

}
