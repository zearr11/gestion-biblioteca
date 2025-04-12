package pe.com.biblioteca.gestion_biblioteca.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.com.biblioteca.gestion_biblioteca.dtos.AutorDTO;
import pe.com.biblioteca.gestion_biblioteca.models.Autor;
import pe.com.biblioteca.gestion_biblioteca.repositories.AutorRepository;
import pe.com.biblioteca.gestion_biblioteca.services.AutorService;

@Component
public class AutorUtil {
    
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private AutorService autorService;

    public void modificarAutor(AutorDTO autorDTO) {
        Autor autor = autorRepository.findById(autorDTO.getIdAutor()).orElse(null);
        if (autor != null) {
            autor.setAutor(autorDTO.getAutor());
            autor.setNacionalidad(autorDTO.getNacionalidad());
            autorRepository.save(autor);
        }
    }

    public void cambiarEstadoAutor(Long idAutor) {
        Autor autor = autorRepository.findById(idAutor).orElse(null);
        if (autor != null) {
            autor.setEstado(autor.getEstado().equals("Activo") ? "Inactivo" : "Activo");
            autorRepository.save(autor);
        }
    }

    public void agregarAutor(AutorDTO autorDTO) {
        Autor autor = new Autor();
        autor.setAutor(autorDTO.getAutor());
        autor.setNacionalidad(autorDTO.getNacionalidad());
        autor.setEstado("Activo");
        autorRepository.save(autor);
    }

    public int totalAutores() {
        List<Autor> autores = new ArrayList<>();
        for (Autor autor : this.autorRepository.findAll()) {
            if (autor.getEstado().equals("Activo")) {
                autores.add(autor);
            }
        }
        return autores.size();
    }

    public List<AutorDTO> getAllAutoresDTO() {
        return this.autorService.findAllDTO();
    }

    public int tAutores() {
        return this.autorService.findAll().size();
    }

    public List<Autor> getAutoresActivos() {
        return this.autorService.findAll().stream()
            .filter(autor -> "Activo".equalsIgnoreCase(autor.getEstado()))
            .collect(Collectors.toList());
    }

    public List<Autor> getAutoresInactivos() {
        return this.autorService.findAll().stream()
            .filter(autor -> "Inactivo".equalsIgnoreCase(autor.getEstado()))
            .collect(Collectors.toList());
    }

}
