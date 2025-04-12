# BooksFree: Sistema de Gestión de Biblioteca Virtual

BooksFree es una aplicación web diseñada para la administración integral de una biblioteca virtual. Con una arquitectura robusta y escalable, BooksFree permite gestionar libros, categorías, autores, editoriales y usuarios a través de roles diferenciados, ofreciendo una experiencia intuitiva y segura tanto para administradores como para usuarios lectores.

---

## Tabla de Contenidos
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Funcionalidades del Sistema](#funcionalidades-del-sistema)
- [Roles y Permisos](#roles-y-permisos)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Pre-Requisitos](#pre-requisitos)
- [Instalación](#instalación)
- [Cuentas de Acceso](#cuentas-de-acceso)

---

## Descripción del Proyecto

BooksFree es una solución web que facilita la gestión y administración de una biblioteca virtual. La plataforma permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en libros, categorías, autores, editoriales y usuarios. El sistema está diseñado para adaptarse a las necesidades de diferentes perfiles de usuario, asegurando un acceso controlado y personalizado según el rol asignado.

---

## Funcionalidades del Sistema

- **Gestión de Usuarios del Sistema (Admin, Usuario, Lector):**  
  Permite listar, registrar, modificar y eliminar usuarios, asegurando que cada perfil tenga acceso a las funcionalidades acordes a su rol.

- **Gestión de Libros:**  
  Facilita el manejo del catálogo de libros, permitiendo su registro, actualización, listado y eliminación. Además, se integra un buscador avanzado y filtros por categorías, autores y editoriales.

- **Gestión de Categorías:**  
  Permite crear, modificar, listar y eliminar categorías, organizando el catálogo de libros de forma eficiente.

- **Gestión de Autores:**  
  Incluye funcionalidades para registrar, actualizar, listar y eliminar autores, garantizando que cada obra esté debidamente asociada a su creador.

- **Gestión de Editoriales:**  
  Posibilita el manejo completo de las editoriales, desde su registro hasta su eliminación, optimizando la organización y búsqueda en el catálogo.

Cada función ha sido desarrollada pensando en la usabilidad y la seguridad, permitiendo a los usuarios interactuar con el sistema de forma intuitiva y eficiente.

---

## Roles y Permisos

El sistema está basado en tres roles principales, cada uno con diferentes niveles de acceso y funcionalidades:

- **ADMIN:**  
  - **Acceso:** Visualización del catálogo como lector y acceso completo al módulo de administración.
  - **Permisos:** Gestiona usuarios del sistema (Admin, Usuario), usuarios lectores, libros, categorías, autores y editoriales.

- **USUARIO:**  
  - **Acceso:** Visualización del catálogo y acceso al módulo de administración.
  - **Permisos:** Puede gestionar usuarios lectores, libros, categorías, autores y editoriales, pero **no** tiene permiso para gestionar los usuarios del sistema (Admin, Usuario).

- **LECTOR:**  
  - **Acceso:** Exclusivamente visualización del catálogo.
  - **Permisos:** Permite filtrar libros por categoría, autor, editorial y mediante una barra de búsqueda, sin acceso al módulo de administración.

---

## Tecnologías Utilizadas

- **Java:**  
  Lenguaje de programación robusto y escalable, ideal para aplicaciones empresariales.

- **Spring Framework:**  
  - **Spring Boot:** Permite un arranque rápido y una configuración mínima para el despliegue de la aplicación.
  - **Spring Data JPA:** Facilita el manejo de la persistencia y el acceso a datos de manera eficiente.
  - **Spring Security:** Asegura la autenticación y autorización de los usuarios, protegiendo los recursos críticos del sistema.

- **Thymeleaf:**  
  Motor de plantillas moderno y natural que facilita la integración de vistas dinámicas en la aplicación web.

- **MySQL:**  
  Sistema gestor de bases de datos relacional, robusto y ampliamente utilizado para aplicaciones de alto rendimiento.

- **Bootstrap:**  
  Framework CSS que permite el desarrollo de interfaces responsivas y modernas. Ofrece componentes predefinidos y utilidades que aceleran la creación de diseños elegantes y consistentes.

- **HTML, CSS y JavaScript:**  
  Tecnologías fundamentales para el desarrollo web:
  - **HTML:** Estructura y semántica de las páginas.
  - **CSS:** Estilos y diseño visual, asegurando una presentación atractiva y coherente.
  - **JavaScript:** Interactividad y dinamismo en el frontend, mejorando la experiencia del usuario.

---

## Pre-Requisitos

Antes de comenzar, asegúrate de contar con lo siguiente:

- **Visual Studio Code:**  
  Recomendado con los plugins:
  - Extension Pack For Java (Microsoft)
  - Spring Boot Extension Pack (VMware)

- **MySQL Workbench**

- **Apache Maven**

- **JDK 21**

---

## Instalación

### Carga de la Base de Datos

1. Ubica el archivo `bd_biblioteca.sql` en la carpeta `documentation`.
2. Ejecuta MySQL e ingresa a la instancia local.
3. En el menú del servidor, selecciona la opción **Server > Data Import**.
4. Elige **Import from Self-Contained File** y carga el archivo `bd_biblioteca.sql`.
5. Haz clic en **Start Import** en la parte inferior derecha.

### Configuración de la Aplicación

1. Abre el proyecto completo en Visual Studio Code.
2. Dirígete a la carpeta `src/main/resources` y abre el archivo `application.properties`.
3. Modifica los siguientes campos según tu configuración de MySQL:
   - `spring.datasource.username`: Ingresa tu usuario de MySQL.
   - `spring.datasource.password`: Ingresa tu contraseña de MySQL.

---

## Cuentas de Acceso

Utiliza las siguientes cuentas para ingresar al sistema:

- **ADMIN:**
  - Usuario: `eduardo11`
  - Contraseña: `123456`

- **USUARIO:**
  - Usuario: `maria22`
  - Contraseña: `123456`

- **LECTOR:**
  - Usuario: `carlos33`
  - Contraseña: `123456`
