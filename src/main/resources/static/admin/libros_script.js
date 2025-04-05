// CONTROL DE PAGINA MAESTRA
const wrapper = document.getElementById('wrapper');
const menuToggle = document.getElementById('menu-toggle');

if(menuToggle) {
    menuToggle.addEventListener('click', function(e) {
        e.preventDefault();
        wrapper.classList.toggle('toggled');
    });
}

vistaHabilitadosInhabilitados();

document.getElementById("view-filter-lst").addEventListener("change", () => {
    vistaHabilitadosInhabilitados();
    cambiarEstado(document.getElementById('estadoFiltro').value);
});

function cambiarEstado(valor) {
    window.location.href = `/libros?estado=${valor}&filtroBusqueda=`;
}

function vistaHabilitadosInhabilitados() {
    let elemento = document.getElementById("seleccionActInact");
    let activos = document.getElementsByClassName("LibrosActivos");
    let inactivos = document.getElementsByClassName("LibrosInactivos");
    deshabilitarVistaLsts(activos, inactivos);

    const buscador = document.getElementById('buscador');
    const lstActivos = parseInt(buscador.getAttribute('data-activos'));
    const lstInactivos = parseInt(buscador.getAttribute('data-inactivos'));
    const estadoFiltro = document.getElementById('estadoFiltro');

    const idSinCoincActivo = document.getElementById("sinCoincidenciasActivos");
    const idSinCoincInactivo = document.getElementById("sinCoincidenciasInactivos");
    
    if (idSinCoincActivo) {
        idSinCoincActivo.style.display = 'none'
    }
    if (idSinCoincInactivo) {
        idSinCoincInactivo.style.display = 'none'
    }
    
    if (document.getElementById("view-filter-lst").value == "1") { // Muestra Libros Activos
        elemento.style.background = "#198754";
        buscador.style.display = (lstActivos > 0) ? 'block' : 'none'; // Muestra u Oculta el buscador
        
        if (idSinCoincInactivo) {
            idSinCoincInactivo.style.display = 'block'
            buscador.style.display = 'block';
        }

        estadoFiltro.value = 'Activo'
        habilitarVistaLsts(activos);
        
    }
    else { // Muestra Libros Inactivos
        elemento.style.background = "#dc3545";
        buscador.style.display = (lstInactivos > 0) ? 'block' : 'none'; // Muestra u Oculta el buscador
        
        if(idSinCoincActivo) {
            idSinCoincActivo.style.display = 'block'
            buscador.style.display = 'block';
        }

        estadoFiltro.value = 'Inactivo'
        habilitarVistaLsts(inactivos);
    }
}

function deshabilitarVistaLsts(activos, inactivos) {
    for (let i = 0; i < activos.length; i++) {
        activos[i].style.display = "none";
    }

    for (let i = 0; i < inactivos.length; i++) {
        inactivos[i].style.display = "none";
    }
}

function habilitarVistaLsts(classList) {
    for (let i = 0; i < classList.length; i++) {
        classList[i].style.display = "block";
    }
}

document.getElementById('verDatosLibro').addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    cargarModalView(button);
    mostrarPaginaView(1);
});

document.getElementById("btnVerMasView").addEventListener("click", () => {
    mostrarPaginaView(2);
});

document.getElementById("btnVolverView").addEventListener("click", () => {
    mostrarPaginaView(1);
});

function ocultarPaginasViews() {
    document.getElementById("pagina1").style.display = 'none';
    document.getElementById("pagina2").style.display = 'none';
};

function ocultarFooterViews() {
    document.getElementById("divVolverView").hidden = true;
    document.getElementById("divVerMasView").hidden = true;
}

function mostrarPaginaView(numero) {

    ocultarPaginasViews();
    ocultarFooterViews();

    if (numero == 1) {
        document.getElementById("pagina1").style.display = 'flex';
        document.getElementById("divVerMasView").hidden = false;
    }
    else {
        document.getElementById("pagina2").style.display = 'flex';  
        document.getElementById("divVolverView").hidden = false;
    }

}

// Cargar datos en el modal de vista de libro
function cargarModalView(button) {
    const foto = button.getAttribute('data-foto'); 
    const titulo = button.getAttribute('data-titulo');
    const descripcion = button.getAttribute('data-descripcion');

    const autor = button.getAttribute('data-nombreAutor');
    const editorial = button.getAttribute('data-nombreEditorial');
    const categoria = button.getAttribute('data-nombreCategoria');
    const anioPublic = button.getAttribute('data-anioPublic');
    const estado = button.getAttribute('data-estado');
    const idLibro = button.getAttribute('data-idLibro');

    document.getElementById('imgLibro').src = foto;
    document.getElementById('modal-titulo').textContent = titulo;
    document.getElementById('modal-descripcion').textContent = descripcion;

    document.getElementById("modal-autor").textContent = autor
    document.getElementById("modal-editorial").textContent = editorial;
    document.getElementById("modal-categoria").textContent = categoria;
    document.getElementById("modal-anioPublic").textContent = anioPublic;
    document.getElementById("modal-estado").textContent = estado;
    document.getElementById("descargaLibro").href = "/libros/descargar-libro/" + idLibro;
}

// Cargar archivo foto en elemento img en agregar libro
document.getElementById('imagenLibro').addEventListener('change', function(event) {
    let imagenElement = document.getElementById('imgLibroAdd');
    let opcionalRuta = 'img/portada-libro.jpg';
    cargarImagenLibro(event, imagenElement, opcionalRuta);
});

function cargarImagenLibro(event, imagenElement, opcionalRuta) {
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    const fileInput = event.target;
    const file = fileInput.files[0];

    const minWidth = 400;  // ancho mínimo requerido
    const minHeight = 600; // alto mínimo requerido

    if (file) {
      const reader = new FileReader();
      reader.onload = function(e) {
        const img = new Image();
        img.onload = function() {
          if (img.width >= minWidth && img.height >= minHeight) {
            imagenElement.src = e.target.result;
          } else {
            fileInput.value = ""; // Limpia el campo
            toastMessage.textContent = `La imagen debe tener al menos ${minWidth}px de ancho y ${minHeight}px de alto.`;
            imagenElement.src = opcionalRuta;
            toastElement.show();
          }
        };
        // Asigna el resultado de FileReader a la imagen para disparar onload
        img.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
}

// Validacion archivo de libro cuando excede los 10 MB
document.getElementById('archivoLibro').addEventListener('change', function(event) {
    validarTamanoArchivo(event);
});

function validarTamanoArchivo(event) {
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    const fileInput = event.target;
    const file = fileInput.files[0];
    const maxSize = 10 * 1024 * 1024; // 10 MB en bytes

    if (file && file.size > maxSize) {
        fileInput.value = ""; // Limpia el campo de entrada
        toastMessage.textContent = 'El archivo excede el tamaño máximo permitido de 10 MB.';
        toastElement.show();
    }
}

// Reseteo de formulario agregar libro
document.getElementById("btn-add-libro").addEventListener("click", () => {
    let form = document.getElementById("formAgregarLibro");
    document.getElementById('imgLibroAdd').src = 'img/portada-libro.jpg';
    form.reset();
})

// Validacion de campos en agregar libro
document.getElementById('formAgregarLibro').addEventListener('submit', function(event) {
    event.preventDefault();
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    const fotoLibro = document.getElementById('imagenLibro').value; // "" -> No se cargo la imagen
    const titulo = document.getElementById('modal-titulo-add').value;
    const descripcion = document.getElementById('modal-descripcion-add').value;
    const autor = document.getElementById('modal-autor-add').value;
    const editorial = document.getElementById('modal-editorial-add').value;
    const categoria = document.getElementById('modal-categoria-add').value;
    const anioPublic = document.getElementById('modal-anioPublic-add').value;
    const archivoLibro = document.getElementById('archivoLibro').value; // "" -> No se cargo el archivo

    let msg = "";

    if (fotoLibro == "") {
        msg = "La imagen del libro es obligatoria.";
    }
    else if (titulo.length == 0) {
        msg = "El título del libro es obligatorio.";
    } 
    else if (descripcion.length == 0) {
        msg = "La descripción del libro es obligatoria.";
    }
    else if (autor == "") {
        msg = "Seleccione un autor válido para el libro.";
    }
    else if (editorial == "") {
        msg = "Seleccione una editorial válida para el libro.";
    }
    else if (categoria == "") {
        msg = "Seleccione una categoría válida para el libro.";
    }
    else if (anioPublic.length == 0) {
        msg = "El año de publicación del libro es obligatorio.";
    }
    else if (archivoLibro == "") {
        msg = "El archivo del libro es obligatorio.";
    }

    if (msg !== "") {
        toastMessage.textContent = msg;
        toastElement.show();
    }
    else{
        this.submit();
    }
});

// Carga de datos en formulario editar libro
document.getElementById("editLibroModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;

    imagenLibroEditGlobal = button.getAttribute('data-foto');

    document.getElementById('modal-idLibro-edit').value = button.getAttribute("data-idLibro");
    document.getElementById('imgLibroEdit').src = imagenLibroEditGlobal;
    document.getElementById('modal-titulo-edit').value = button.getAttribute("data-titulo");
    document.getElementById('modal-descripcion-edit').value = button.getAttribute("data-descripcion");
    document.getElementById('modal-autor-edit').value = button.getAttribute("data-idAutor");
    document.getElementById('modal-editorial-edit').value = button.getAttribute("data-idEditorial");
    document.getElementById('modal-categoria-edit').value = button.getAttribute("data-idCategoria");
    document.getElementById('modal-anioPublic-edit').value = button.getAttribute("data-anioPublic");
    
    ocultarVistaCargaArchivosEdicion();
    document.getElementById("archivoLibroSelector").value = "1";
    document.getElementById("imagenLibroSelector").value = "1";

});

function ocultarVistaCargaArchivosEdicion() {
    document.getElementById('imagenLibroEdit').value = "";
    document.getElementById('imagenLibroEdit').style.display = 'none';

    document.getElementById("archivoLibroEdit").value = "";
    document.getElementById('archivoLibroEdit').style.display = 'none';
};

document.getElementById("archivoLibroSelector").addEventListener("change", (event) => {
    if (event.target.value == "1") {
        document.getElementById("archivoLibroEdit").value = ""; // Limpia el campo de archivo
        document.getElementById('archivoLibroEdit').style.display = 'none';
    }
    else {
        document.getElementById('archivoLibroEdit').style.display = 'block';
    }
});

document.getElementById("imagenLibroSelector").addEventListener("change", (event) => {
    if (event.target.value == "1") {
        document.getElementById('imgLibroEdit').src = imagenLibroEditGlobal;
        document.getElementById('imagenLibroEdit').value = ""; // Limpia el campo de imagen
        document.getElementById('imagenLibroEdit').style.display = 'none';
    }
    else {
        document.getElementById('imagenLibroEdit').style.display = 'block';
    }
});

// Validacion de campos en editar libro
document.getElementById("formEditarLibro").addEventListener("submit", function(event) {
    event.preventDefault();
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    const titulo = document.getElementById('modal-titulo-edit').value;
    const descripcion = document.getElementById('modal-descripcion-edit').value;
    const autor = document.getElementById('modal-autor-edit').value;
    const editorial = document.getElementById('modal-editorial-edit').value;
    const categoria = document.getElementById('modal-categoria-edit').value;
    const anioPublic = document.getElementById('modal-anioPublic-edit').value;

    const imagenLibroSelector = document.getElementById('imagenLibroSelector').value;
    const fotoLibroFile = document.getElementById('imagenLibroEdit').value;

    const archivoLibroSelector = document.getElementById('archivoLibroSelector').value;
    const archivoLibroFile = document.getElementById('archivoLibroEdit').value;

    let msg = "";

    if (titulo.length == 0) {
        msg = "El título del libro es obligatorio.";
    } 
    else if (descripcion.length == 0) {
        msg = "La descripción del libro es obligatoria.";
    }
    else if (autor == "") {
        msg = "Seleccione un autor válido para el libro.";
    }
    else if (editorial == "") {
        msg = "Seleccione una editorial válida para el libro.";
    }
    else if (categoria == "") {
        msg = "Seleccione una categoría válida para el libro.";
    }
    else if (anioPublic.length == 0) {
        msg = "El año de publicación del libro es obligatorio.";
    }
    else if (imagenLibroSelector == "2" && fotoLibroFile == "") {
        msg = "La imagen del libro es obligatoria.";
    }
    else if (archivoLibroSelector == "2" && archivoLibroFile == "") {
        msg = "El archivo del libro es obligatorio.";
    }

    if (msg !== "") {
        toastMessage.textContent = msg;
        toastElement.show();
    }
    else{
        this.submit();
    }
});

var imagenLibroEditGlobal = "";

document.getElementById("imagenLibroEdit").addEventListener("change", function(event) {
    let imagenElement = document.getElementById('imgLibroEdit');
    cargarImagenLibro(event, imagenElement, imagenLibroEditGlobal);
});

document.getElementById("archivoLibroEdit").addEventListener("change", function(event) {
    validarTamanoArchivo(event);
});

// Carga Modal para deshabilitar libro
document.getElementById("dissableModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    let id = button.getAttribute('data-idLibro');
    let titulo = button.getAttribute('data-titulo');
    document.getElementById('libroName').textContent = titulo;
    document.getElementById("idLibroDissable").value = id;
});

// Carga Modal para habilitar libro
document.getElementById("habilitarModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    let id = button.getAttribute('data-idLibro');
    let titulo = button.getAttribute('data-titulo');
    document.getElementById('libroName2').textContent = titulo;
    document.getElementById("idLibroEnable").value = id;
});