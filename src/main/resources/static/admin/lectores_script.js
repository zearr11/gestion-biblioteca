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
    window.location.href = `/lectores?estado=${valor}&filtroBusqueda=`;
}

function vistaHabilitadosInhabilitados() { 
    let elemento = document.getElementById("seleccionActInact");
    let activos = document.getElementsByClassName("LectoresActivos");
    let inactivos = document.getElementsByClassName("LectoresInactivos");
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
    
    if (document.getElementById("view-filter-lst").value == "1") { // Muestra Usuarios Activos
        elemento.style.background = "#198754";
        buscador.style.display = (lstActivos > 0) ? 'block' : 'none'; // Muestra u Oculta el buscador
        
        if (idSinCoincInactivo) {
            idSinCoincInactivo.style.display = 'block'
            buscador.style.display = 'block';
        }

        estadoFiltro.value = 'Activo'
        habilitarVistaLsts(activos);
        
    }
    else { // Muestra Usuarios Inactivos
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

// Modal para ver datos de lector
document.addEventListener("DOMContentLoaded", function () {
    let modal = document.getElementById("verDatosLector");
    
    modal.addEventListener("show.bs.modal", function (event) {
        let button = event.relatedTarget;

        // Obtener datos del botón
        document.getElementById("modal-username").textContent = button.getAttribute("data-username");
        document.getElementById("modal-nombres").textContent = button.getAttribute("data-nombres");
        document.getElementById("modal-apellidos").textContent = button.getAttribute("data-apellidos");
        document.getElementById("modal-correo").textContent = button.getAttribute("data-correo");
        document.getElementById("modal-genero").textContent = button.getAttribute("data-genero");
        document.getElementById("modal-tipoDoc").textContent = button.getAttribute("data-tipoDoc");
        document.getElementById("modal-numeroDoc").textContent = button.getAttribute("data-numeroDoc");
        document.getElementById("modal-fechaReg").textContent = button.getAttribute("data-fechaReg");
        document.getElementById("modal-rol").textContent = button.getAttribute("data-rol");
        document.getElementById("modal-estado").textContent = button.getAttribute("data-estado");
    });
});

// Carga de datos en formulario editar lector
document.getElementById("editLectorModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    document.getElementById("idUsuario").value = button.getAttribute("data-idUsuario");
    document.getElementById("nombresEdit").value = button.getAttribute("data-nombres");
    document.getElementById("apellidosEdit").value = button.getAttribute("data-apellidos");
    document.getElementById("correoEdit").value = button.getAttribute("data-correo");
    document.getElementById("generoEdit").value = button.getAttribute("data-genero");
    document.getElementById("tipoDocEdit").value = button.getAttribute("data-tipoDoc");
    document.getElementById("numeroDocEdit").value = button.getAttribute("data-numeroDoc");
    document.getElementById("usuarioEdit").value = button.getAttribute("data-username");
});

// Validación de formulario editar lector
document.getElementById("form-edit-lector").addEventListener("submit", function (event) {
    event.preventDefault();

    // Toast de error (Alerta que aparece en la parte inferior de la pantalla)
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    // Elementos a validar
    let correo = document.getElementById("correoEdit").value;
    let genero = document.getElementById("generoEdit").value;
    let tipoDoc = document.getElementById("tipoDocEdit").value;
    let numeroDoc = document.getElementById("numeroDocEdit").value;
    let usuario = document.getElementById("usuarioEdit").value;
    let idUsuario = document.getElementById("idUsuario").value;
    
    let regexCorreo = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    let msg = "";
    
    if (!regexCorreo.test(correo)) {
        msg = ("El correo ingresado no es válido.");
    }
    else if (genero == "") {
        msg = ("Debe seleccionar un género.");
    }
    else if (tipoDoc == "") {
        msg = ("Debe seleccionar un tipo de documento.");
    }
    else if (tipoDoc == "DNI" && numeroDoc.length != 8) {
        msg = ("El DNI debe tener 8 dígitos.");
    }
    else if (tipoDoc == "CE" && numeroDoc.length < 8) {
        msg = ("El Carné de Extranjería no puede tener menos de 8 caracteres.");
    }
    else if (validacionEdicionUsuarioBD(correo, numeroDoc, usuario, idUsuario) !== "") {
        msg = validacionEdicionUsuarioBD(correo, numeroDoc, usuario, idUsuario);
    }

    if (msg !== "") {
        toastMessage.textContent = msg;
        toastElement.show();
    }
    else{
        this.submit();
    }
});

// Carga Modal para deshabilitar lector
document.getElementById("dissableModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    let id = button.getAttribute('data-idUsuario');
    let nombresCompletos = button.getAttribute('data-nombresCompletos');
    document.getElementById('usuarioName').textContent = nombresCompletos;
    document.getElementById("idUsuarioDissable").value = id;
});

// Carga Modal para habilitar lector
document.getElementById("habilitarModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    let id = button.getAttribute('data-idUsuario');
    let nombresCompletos = button.getAttribute('data-nombresCompletos');
    document.getElementById('usuarioName2').textContent = nombresCompletos;
    document.getElementById("idUsuarioEnable").value = id;
});

function validacionEdicionUsuarioBD(correo, numeroDoc, usuario, id) {
    let msg = "";
    for (let i = 0; i < dataUsuariosBD.length; i++) {
        if (dataUsuariosBD[i].idUsuario != id) {
            if (dataUsuariosBD[i].correo == correo) {
                msg = "El correo ingresado ya está registrado.";
            }
            else if (dataUsuariosBD[i].numeroDoc == numeroDoc) {
                msg = "El documento ingresado ya está registrado.";
            }
            else if (dataUsuariosBD[i].username == usuario) {
                msg = "El usuario ingresado ya está registrado.";
            }
        }
    }
    return msg;
}

var dataUsuariosBD = [];
fetch('http://localhost:8080/usuarios/obtener-usuarios')
    .then(response => response.json())
    .then(usuarios => {
        dataUsuariosBD = usuarios;
    }).catch(error => console.error('Error:', error));

