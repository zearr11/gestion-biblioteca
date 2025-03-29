package pe.com.biblioteca.gestion_biblioteca.paginations;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.biblioteca.gestion_biblioteca.models.Usuario;
import pe.com.biblioteca.gestion_biblioteca.utils.UsuarioUtil;

@Component
public class UsuariosPaginacion {

    @Autowired
    private UsuarioUtil usuarioUtil;
    
    private static final int REGISTROS_POR_FILA = 8;
    
    public List<Usuario> getUsuariosXfila(int numeroFila, String estado) {

        List<Usuario> usuarios = (estado.equals("Activo")) ? 
                        this.usuarioUtil.getAllUsuariosActivos() : this.usuarioUtil.getAllUsuariosInactivos();
    
        if (usuarios.isEmpty()) {
            return new ArrayList<>();
        }

        int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
        int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, usuarios.size());
    
        return new ArrayList<>(usuarios.subList(desde, hasta));
    }

    public int getCantidadFilasUsuarios(String estado) {
        int totalUsuarios = (estado.equals("Activo")) ? 
                    this.usuarioUtil.getAllUsuariosActivos().size() : this.usuarioUtil.getAllUsuariosInactivos().size();
        
        return (int) Math.ceil((double) totalUsuarios / REGISTROS_POR_FILA);
    }

    public List<Usuario> filtroBusquedaAllUsuarios(String estado, String filtro) {

        List<Usuario> usuarios = (estado.equals("Activo")) ? 
            this.usuarioUtil.getAllUsuariosActivos() : this.usuarioUtil.getAllUsuariosInactivos();

        String filtroLower = filtro.toLowerCase();

        List<Usuario> filtrados = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            boolean coincide = 
                (usuario.getUsuario() != null && usuario.getUsuario().toLowerCase().contains(filtroLower)) ||
                (usuario.getEstado() != null && usuario.getEstado().toLowerCase().contains(filtroLower)) ||
                (usuario.getRol() != null && usuario.getRol().toLowerCase().contains(filtroLower)) ||
                (usuario.getPersona().getNombres() != null && usuario.getPersona().getNombres().toLowerCase().contains(filtroLower)) ||
                (usuario.getPersona().getApellidos() != null && usuario.getPersona().getApellidos().toLowerCase().contains(filtroLower)) ||
                (usuario.getPersona().getCorreo() != null && usuario.getPersona().getCorreo().toLowerCase().contains(filtroLower)) ||
                (usuario.getPersona().getGenero() != null && usuario.getPersona().getGenero().toLowerCase().contains(filtroLower)) ||
                (usuario.getPersona().getNumeroDoc() != null && usuario.getPersona().getNumeroDoc().toLowerCase().contains(filtroLower)) ||
                (usuario.getPersona().getTipoDoc() != null && usuario.getPersona().getTipoDoc().toLowerCase().contains(filtroLower)) ||
                (usuario.getPersona().getFechaRegistro() != null && 
                    usuario.getPersona().getFechaRegistro().toString().toLowerCase().contains(filtroLower)) ||
                (usuario.getPersona().getIdPersona() != null && 
                    usuario.getPersona().getIdPersona().toString().toLowerCase().contains(filtroLower));

            if (coincide) {
                filtrados.add(usuario);
            }
        }
        return filtrados;
    }

    public List<Usuario> filtroBusquedaUsuario(String estado, String filtro, int numeroFila) {

        List<Usuario> lstFiltrada = filtroBusquedaAllUsuarios(estado, filtro);

        if (lstFiltrada.size() > 0) {
            int desde = (numeroFila - 1) * REGISTROS_POR_FILA;
            int hasta = Math.min(numeroFila * REGISTROS_POR_FILA, lstFiltrada.size());

            return lstFiltrada.subList(desde, hasta);
        }

        return lstFiltrada;
    }

    public int getCantidadFilasUsuariosFiltradas(String estado, String filtro) {

        List<Usuario> lstFiltrada = filtroBusquedaAllUsuarios(estado, filtro);

        return (int) Math.ceil((double) lstFiltrada.size() / REGISTROS_POR_FILA);
    }

}
