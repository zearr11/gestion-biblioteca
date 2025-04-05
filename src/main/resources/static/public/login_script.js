document.addEventListener("DOMContentLoaded", function () {
    
    var toastElement = document.getElementById("toastError");
    var toastCuentaCreada = document.getElementById("toastCreacionCuenta");

    if (toastElement) {
        var toast = new bootstrap.Toast(toastElement);
        toast.show();
    }
    else if (toastCuentaCreada) {
        var toast = new bootstrap.Toast(toastCuentaCreada);
        toast.show();
    }

});