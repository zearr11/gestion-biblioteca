document.addEventListener("DOMContentLoaded", function () {
    var toastElement = document.getElementById("toastError");
    if (toastElement) {
        var toast = new bootstrap.Toast(toastElement);
        toast.show();
    }
});