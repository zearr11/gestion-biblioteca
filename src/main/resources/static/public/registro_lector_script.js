// Validación de contraseña de formulario agregar usuario
document.getElementById("contrasenia-form").addEventListener("input", function () {
  let password = this.value;

  document.getElementById("lengthCriteria").classList.toggle("text-success", password.length >= 8);
  document.getElementById("lengthCriteria").classList.toggle("text-danger", password.length < 8);

  document.getElementById("uppercaseCriteria").classList.toggle("text-success", /[A-Z]/.test(password));
  document.getElementById("uppercaseCriteria").classList.toggle("text-danger", !/[A-Z]/.test(password));

  document.getElementById("lowercaseCriteria").classList.toggle("text-success", /[a-z]/.test(password));
  document.getElementById("lowercaseCriteria").classList.toggle("text-danger", !/[a-z]/.test(password));

  document.getElementById("numberCriteria").classList.toggle("text-success", /[0-9]/.test(password));
  document.getElementById("numberCriteria").classList.toggle("text-danger", !/[0-9]/.test(password));

  document.getElementById("specialCharCriteria").classList.toggle("text-success", /[!@#$%^&*(),.?":{}|<>]/.test(password));
  document.getElementById("specialCharCriteria").classList.toggle("text-danger", !/[!@#$%^&*(),.?":{}|<>]/.test(password));
});

// Validación de formulario 
document.getElementById("form-registro").addEventListener("submit", function (event) {
  event.preventDefault();

  // Toast de error (Alerta que aparece en la parte inferior de la pantalla)
  let toastElement = new bootstrap.Toast(document.getElementById("toastError"));
  let toastMessage = document.getElementById("toastMessage");

  // Elementos a validar
  let correo = document.getElementById("correo-form").value;
  let genero = document.getElementById("genero-form").value;
  let tipoDoc = document.getElementById("tipoDocumento-form").value;
  let numeroDoc = document.getElementById("numeroDocumento-form").value;
  let contrasenia = document.getElementById("contrasenia-form").value;
  let usuario = document.getElementById("nombreUsuario-form").value;
  
  let regexCorreo = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  let msg = "";
  
  if (!regexCorreo.test(correo)) {
    msg = "El correo ingresado no es válido.";
  }
  else if (genero == "") {
    msg = "Debe seleccionar un género.";
  }
  else if (tipoDoc == "") {
    msg = "Debe seleccionar un tipo de documento.";
  }
  else if (tipoDoc == "DNI" && numeroDoc.length != 8) {
    msg = "El DNI debe tener 8 dígitos.";
  }
  else if (tipoDoc == "CE" && numeroDoc.length < 8) {
    msg = "El Carné de Extranjería no puede tener menos de 8 caracteres.";
  }
  else if (validacionNuevoUsuarioBD(correo, numeroDoc, usuario) !== "") {
    msg = validacionNuevoUsuarioBD(correo, numeroDoc, usuario);
  }
  else {
    if (contrasenia.length < 8)
      msg = "La contraseña debe tener al menos 8 caracteres.";
    else if (!/[A-Z]/.test(contrasenia))
      msg = "La contraseña debe incluir al menos una letra mayúscula.";
    else if (!/[a-z]/.test(contrasenia))
      msg = "La contraseña debe incluir al menos una letra minúscula.";
    else if (!/[0-9]/.test(contrasenia))
      msg = "La contraseña debe incluir al menos un número.";
    else if (!/[!@#$%^&*(),.?":{}|<>]/.test(contrasenia))
      msg = "La contraseña debe incluir al menos un carácter especial.";
  }

  if (msg !== "") {
    toastMessage.textContent = msg;
    toastElement.show();
  }
  else {
    this.submit();
  }
});

// Validación de usuarios repetidos
function validacionNuevoUsuarioBD(correo, numeroDoc, usuario) {
  let msg = "";
  for (let i = 0; i < dataUsuariosBD.length; i++) {
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
  return msg;
}

// Carga de datos de los usuarios ya registrados
var dataUsuariosBD = [];
fetch('http://localhost:8080/usuarios/obtener-usuarios')
  .then(response => response.json())
  .then(usuarios => {
    dataUsuariosBD = usuarios;
  })
  .catch(error => console.error('Error:', error));
