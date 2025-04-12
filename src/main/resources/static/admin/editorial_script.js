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
    window.location.href = `/editoriales?estado=${valor}&filtroBusqueda=`;
}

function vistaHabilitadosInhabilitados() {
    let elemento = document.getElementById("seleccionActInact");
    let activos = document.getElementsByClassName("editorialesActivos");
    let inactivos = document.getElementsByClassName("editorialesInactivos");
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
    
    if (document.getElementById("view-filter-lst").value == "1") { // Muestra Editoriales Activas
        elemento.style.background = "#198754";
        buscador.style.display = (lstActivos > 0) ? 'block' : 'none'; // Muestra u Oculta el buscador
        
        if (idSinCoincInactivo) {
            idSinCoincInactivo.style.display = 'block'
            buscador.style.display = 'block';
        }

        estadoFiltro.value = 'Activo'
        habilitarVistaLsts(activos);
        
    }
    else { // Muestra Editoriales Inactivas
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

// Reseto de formulario agregar editorial
document.getElementById("addEditorialModal").addEventListener("show.bs.modal", function (event) {
    document.getElementById("formAgregarEditorial").reset();
});

// Validación de formulario agregar editorial
document.getElementById("formAgregarEditorial").addEventListener("submit", function (event) {
    event.preventDefault();

    // Toast de error (Alerta que aparece en la parte inferior de la pantalla)
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    // Elementos a validar
    let editorial = document.getElementById("editorial-add").value;
    let msg = "";

    if (validacionAgregarEditorialBD(editorial) !== "") {
        msg = (validacionAgregarEditorialBD(editorial));
    }

    if (msg !== "") {
        toastMessage.textContent = msg;
        toastElement.show();
    } else {
        this.submit();
    }
});

// Carga de datos en modal editar editorial
document.getElementById("editEditorialModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    document.getElementById('idEditorialEdit').value = button.getAttribute("data-idEditorial");
    document.getElementById('editorial-edit').value = button.getAttribute("data-editorial");
});

// Validación de formulario editar editorial
document.getElementById("formEditarEditorial").addEventListener("submit", function (event) {
    event.preventDefault();

    // Toast de error (Alerta que aparece en la parte inferior de la pantalla)
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    // Elementos a validar
    let id = document.getElementById("idEditorialEdit").value;
    let editorial = document.getElementById("editorial-edit").value;
    let msg = "";

    if (validacionEdicionEditorialBD(editorial, id) !== "") {
        msg = (validacionEdicionEditorialBD(editorial, id));
    }

    if (msg !== "") {
        toastMessage.textContent = msg;
        toastElement.show();
    } else {
        this.submit();
    }
});

// Carga de datos en modal deshabilitar editorial
document.getElementById("dissableModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    document.getElementById('idEditorialDissable').value = button.getAttribute("data-idEditorial");
    document.getElementById('editorialName').textContent = button.getAttribute("data-editorial");
});

// Carga de datos en modal habilitar editorial
document.getElementById("habilitarModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    document.getElementById('idEditorialEnable').value = button.getAttribute("data-idEditorial");
    document.getElementById('editorialName2').textContent = button.getAttribute("data-editorial");
});

function validacionAgregarEditorialBD(editorial) {
    let msg = "";
    for (let i = 0; i < dataEditorialesBD.length; i++) {
        if (dataEditorialesBD[i].editorial == editorial) {
            msg = "Los datos ingresados coinciden con una editorial ya registrada.";
        }
    }
    return msg;
}

function validacionEdicionEditorialBD(editorial, id) {
    let msg = "";
    for (let i = 0; i < dataEditorialesBD.length; i++) {
        if (dataEditorialesBD[i].idEditorial != id) {
            if (dataEditorialesBD[i].editorial == editorial) {
                msg = "Los datos ingresados coinciden con una editorial ya registrada.";
            }
        }
    }
    return msg;
}

var dataEditorialesBD = [];
fetch('http://localhost:8080/editoriales/obtener-editoriales')
    .then(response => response.json())
    .then(editoriales => {
        dataEditorialesBD = editoriales;
    }).catch(error => console.error('Error:', error));