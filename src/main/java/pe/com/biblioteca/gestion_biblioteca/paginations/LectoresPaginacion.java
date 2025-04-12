package pe.com.biblioteca.gestion_biblioteca.paginations;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.utils.LectorUtil;

@Component
public class LectoresPaginacion {
    
    @Autowired
    private LectorUtil lectorUtil;
    
    private static final int REGISTROS_POR_FILA = 8;

    public List<Usuario> getLectoresXfila(int numeroFila, String estado) {

        List<Usuario> lectores = (estado.equals("Activo")) ? 
                        this.lectorUtil.getAllLectoresActivos() : this.lectorUtil.getAllLectoresInactivos();
    
        if (lectores.isEmpty()) {
            return new ArrayList<>();
        }

        int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
        int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, lectores.size());
    
        return new ArrayList<>(lectores.subList(desde, hasta));                 
    }

    public int getCantidadFilasLectores(String estado) {
        int totalLectores = (estado.equals("Activo")) ? 
                    this.lectorUtil.getAllLectoresActivos().size() : this.lectorUtil.getAllLectoresInactivos().size();
        
        return (int) Math.ceil((double) totalLectores / REGISTROS_POR_FILA);
    }

    public List<Usuario> filtroBusquedaAllLectores(String estado, String filtro) {

        List<Usuario> lectores = (estado.equals("Activo")) ? 
            this.lectorUtil.getAllLectoresActivos() : this.lectorUtil.getAllLectoresInactivos();

        String filtroLower = filtro.toLowerCase();

        List<Usuario> filtrados = new ArrayList<>();

        for (Usuario lector : lectores) {
            boolean coincide = 
                (lector.getUsuario() != null && lector.getUsuario().toLowerCase().contains(filtroLower)) ||
                (lector.getEstado() != null && lector.getEstado().toLowerCase().contains(filtroLower)) ||
                (lector.getRol() != null && lector.getRol().toLowerCase().contains(filtroLower)) ||
                (lector.getPersona().getNombres() != null && lector.getPersona().getNombres().toLowerCase().contains(filtroLower)) ||
                (lector.getPersona().getApellidos() != null && lector.getPersona().getApellidos().toLowerCase().contains(filtroLower)) ||
                (lector.getPersona().getCorreo() != null && lector.getPersona().getCorreo().toLowerCase().contains(filtroLower)) ||
                (lector.getPersona().getGenero() != null && lector.getPersona().getGenero().toLowerCase().contains(filtroLower)) ||
                (lector.getPersona().getNumeroDoc() != null && lector.getPersona().getNumeroDoc().toLowerCase().contains(filtroLower)) ||
                (lector.getPersona().getTipoDoc() != null && lector.getPersona().getTipoDoc().toLowerCase().contains(filtroLower)) ||
                (lector.getPersona().getFechaRegistro() != null && 
                    lector.getPersona().getFechaRegistro().toString().toLowerCase().contains(filtroLower)) ||
                (lector.getPersona().getIdPersona() != null && 
                    lector.getPersona().getIdPersona().toString().toLowerCase().contains(filtroLower));

            if (coincide) {
                filtrados.add(lector);
            }
        }
        return filtrados;
    }

    public List<Usuario> filtroBusquedaLector(String estado, String filtro, int numeroFila) {

        List<Usuario> lstFiltrada = filtroBusquedaAllLectores(estado, filtro);

        if (lstFiltrada.size() > 0) {
            int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
            int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, lstFiltrada.size());

            return lstFiltrada.subList(desde, hasta);
        }

        return lstFiltrada;
    }

    public int getCantidadFilasLectoresFiltradas(String estado, String filtro) {

        List<Usuario> lstFiltrada = filtroBusquedaAllLectores(estado, filtro);

        return (int) Math.ceil((double) lstFiltrada.size() / REGISTROS_POR_FILA);
    }

}
