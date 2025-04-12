// // Script para el menú lateral
const wrapper = document.getElementById('wrapper');
const menuToggle = document.getElementById('menu-toggle');

if (menuToggle) {
    menuToggle.addEventListener('click', function (e) {
        e.preventDefault();
        wrapper.classList.toggle('toggled');
    });
};

vistaHabilitadosInhabilitadosAutores();

document.getElementById("view-filter-lst").addEventListener("change", () => {
    vistaHabilitadosInhabilitadosAutores();
    cambiarEstadoAutor(document.getElementById('estadoFiltro').value);
});

function cambiarEstadoAutor(valor) {
    window.location.href = `/autores?estado=${valor}&filtroBusqueda=`;
}

function vistaHabilitadosInhabilitadosAutores() {
    let elemento = document.getElementById("seleccionActInact");
    let activos = document.getElementsByClassName("AutoresActivos");
    let inactivos = document.getElementsByClassName("AutoresInactivos");
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

    if (document.getElementById("view-filter-lst").value == "1") {
        elemento.style.background = "#198754";
        buscador.style.display = (lstActivos > 0) ? 'block' : 'none'; // Muestra u Oculta el buscador

        if (idSinCoincInactivo) {
            idSinCoincInactivo.style.display = 'block'
            buscador.style.display = 'block';
        }

        estadoFiltro.value = 'Activo'
        habilitarVistaLsts(activos);

    } else { // Muestra Autores Inactivos
        elemento.style.background = "#dc3545";
        buscador.style.display = (lstInactivos > 0) ? 'block' : 'none'; // Muestra u Oculta el buscador

        if (idSinCoincActivo) {
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

// Reseto de formulario agregar autor
document.getElementById("addAutorModal").addEventListener("show.bs.modal", function (event) {
    document.getElementById("form-add-autor").reset();
});

// Validación de formulario agregar autor
document.getElementById("form-add-autor").addEventListener("submit", function (event) {
    event.preventDefault();

    // Toast de error (Alerta que aparece en la parte inferior de la pantalla)
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    // Elementos a validar
    let autor = document.getElementById("autor").value;
    let nacionalidad = document.getElementById("nacionalidad").value;

    let msg = "";

    if (nacionalidad == "") {
        msg = ("Seleccione una nacionalidad válida.");
    } else if (validacionAgregarAutorBD(autor, nacionalidad) !== "") {
        msg = validacionAgregarAutorBD(autor, nacionalidad);
    }

    if (msg !== "") {
        toastMessage.textContent = msg;
        toastElement.show();
    } else {
        this.submit();
    }
});

// Carga de datos en formulario editar autor
document.getElementById("editAutorModal").addEventListener("show.bs.modal", function (event) {
    let button = event.relatedTarget;
    if (!button) return;
    document.getElementById("idAutor").value = button.getAttribute("data-idAutor");
    document.getElementById("autorEdit").value = button.getAttribute("data-autor");
    document.getElementById("nacionalidadEdit").value = button.getAttribute("data-nacionalidad");
});

// Validación de formulario editar autor
document.getElementById("form-edit-autor").addEventListener("submit", function (event) {
    event.preventDefault();

    // Toast de error (Alerta que aparece en la parte inferior de la pantalla)
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    // Elementos a validar
    let autor = document.getElementById("autorEdit").value;
    let nacionalidad = document.getElementById("nacionalidadEdit").value;
    let idAutor = document.getElementById("idAutor").value;

    let msg = "";

    if (nacionalidad == "") {
        msg = ("Seleccione una nacionalidad válida.");
    } else if (validacionEdicionAutorBD(autor, nacionalidad, idAutor) !== "") {
        msg = validacionEdicionAutorBD(autor, nacionalidad, idAutor);
    }

    if (msg !== "") {
        toastMessage.textContent = msg;
        toastElement.show();
    } else {
        this.submit();
    }
});

// Carga Modal para deshabilitar autor
document.getElementById("dissableModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    let id = button.getAttribute('data-idAutor');
    let nombresCompletos = button.getAttribute('data-nombresCompletos');
    document.getElementById('autorName').textContent = nombresCompletos;
    document.getElementById("idAutorDissable").value = id;
});

// Carga Modal para habilitar usuario
document.getElementById("habilitarModal").addEventListener("show.bs.modal", function(event) {
     let button = event.relatedTarget;
     if (!button) return;
     let id = button.getAttribute('data-idAutor');
     let nombresCompletos = button.getAttribute('data-nombresCompletos');
     document.getElementById('autorName2').textContent = nombresCompletos;
     document.getElementById("idAutorEnable").value = id;
 });

function validacionAgregarAutorBD(autor, nacionalidad) {
    let msg = "";
    for (let i = 0; i < dataAutoresBD.length; i++) {
        if (dataAutoresBD[i].autor == autor && dataAutoresBD[i].nacionalidad == nacionalidad) {
            msg = "Los datos ingresados coinciden con un autor ya registrado.";
        }
    }
    return msg;
}

function validacionEdicionAutorBD(autor, nacionalidad, id) {
    let msg = "";
    for (let i = 0; i < dataAutoresBD.length; i++) {
        if (dataAutoresBD[i].idAutor != id) {
            if (dataAutoresBD[i].autor == autor && dataAutoresBD[i].nacionalidad == nacionalidad) {
                msg = "Los datos ingresados coinciden con un autor ya registrado.";
            }
        }
    }
    return msg;
}

var dataAutoresBD = [];
fetch('http://localhost:8080/autores/obtener-autores')
    .then(response => response.json())
    .then(autores => {
        dataAutoresBD = autores;
    }).catch(error => console.error('Error:', error));