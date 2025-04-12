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
    window.location.href = `/categorias?estado=${valor}&filtroBusqueda=`;
}

function vistaHabilitadosInhabilitados() {
    let elemento = document.getElementById("seleccionActInact");
    let activos = document.getElementsByClassName("categoriasActivos");
    let inactivos = document.getElementsByClassName("categoriasInactivos");
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
    
    if (document.getElementById("view-filter-lst").value == "1") { // Muestra Categorias Activas
        elemento.style.background = "#198754";
        buscador.style.display = (lstActivos > 0) ? 'block' : 'none'; // Muestra u Oculta el buscador
        
        if (idSinCoincInactivo) {
            idSinCoincInactivo.style.display = 'block'
            buscador.style.display = 'block';
        }

        estadoFiltro.value = 'Activo'
        habilitarVistaLsts(activos);
        
    }
    else { // Muestra Categorias Inactivas
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

// Reseto de formulario agregar categoria
document.getElementById("addCategoriaModal").addEventListener("show.bs.modal", function (event) {
    document.getElementById("formAgregar").reset();
});

// Validación de formulario agregar categoria
document.getElementById("formAgregar").addEventListener("submit", function (event) {
    event.preventDefault();

    // Toast de error (Alerta que aparece en la parte inferior de la pantalla)
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    // Elementos a validar
    let categoria = document.getElementById("categoria-add").value;
    let msg = "";

    if (validacionAgregarCategoriaBD(categoria) !== "") {
        msg = (validacionAgregarCategoriaBD(categoria));
    }

    if (msg !== "") {
        toastMessage.textContent = msg;
        toastElement.show();
    } else {
        this.submit();
    }
});

// Carga de datos en formulario editar categoria
document.getElementById("editCategoriaModal").addEventListener("show.bs.modal", function (event) {
    let button = event.relatedTarget;
    if (!button) return;
    document.getElementById("idCategoriaEdit").value = button.getAttribute("data-idCategoria");
    document.getElementById("categoria-edit").value = button.getAttribute("data-categoria");
});

// Validación de formulario editar categoria
document.getElementById("formEditar").addEventListener("submit", function (event) {
    event.preventDefault();

    // Toast de error (Alerta que aparece en la parte inferior de la pantalla)
    let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
    let toastMessage = document.getElementById("toastMessage");

    // Elementos a validar
    let id = document.getElementById("idCategoriaEdit").value;
    let categoria = document.getElementById("categoria-edit").value;
    let msg = "";

    if (validacionEdicionCategoriaBD(categoria, id) !== "") {
        msg = (validacionEdicionCategoriaBD(categoria, id));
    }

    if (msg !== "") {
        toastMessage.textContent = msg;
        toastElement.show();
    } else {
        this.submit();
    }
});

// Carga de datos en modal deshabilitar categoria
document.getElementById("dissableModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    document.getElementById('idCategoriaDissable').value = button.getAttribute("data-idCategoria");
    document.getElementById('categoriaName').textContent = button.getAttribute("data-categoria");
});

// Carga de datos en modal habilitar categoria
document.getElementById("habilitarModal").addEventListener("show.bs.modal", function(event) {
    let button = event.relatedTarget;
    if (!button) return;
    document.getElementById('idCategoriaEnable').value = button.getAttribute("data-idCategoria");
    document.getElementById('categoriaName2').textContent = button.getAttribute("data-categoria");
});

function validacionAgregarCategoriaBD(categoria) {
    let msg = "";
    for (let i = 0; i < dataCategoriasBD.length; i++) {
        if (dataCategoriasBD[i].categoria == categoria) {
            msg = "Los datos ingresados coinciden con una categoria ya registrada.";
        }
    }
    return msg;
}

function validacionEdicionCategoriaBD(categoria, id) {
    let msg = "";
    for (let i = 0; i < dataCategoriasBD.length; i++) {
        if (dataCategoriasBD[i].idCategoria != id) {
            if (dataCategoriasBD[i].categoria == categoria) {
                msg = "Los datos ingresados coinciden con una categoria ya registrada.";
            }
        }
    }
    return msg;
}

var dataCategoriasBD = [];
fetch('http://localhost:8080/categorias/obtener-categorias')
    .then(response => response.json())
    .then(categorias => {
        dataCategoriasBD = categorias;
    }).catch(error => console.error('Error:', error));