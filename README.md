# TopTierFlix v1.0 release
#Proyecto TFG 2º DAM Promoción 2023-2025

TTF es una plataforma para descubrir, gestionar y comentar tus películas, series, música y videojuegos favoritos.

**Estado del Proyecto:** Finalizada versión básica para realizar defensa y entrega. 

**Características Actuales:**

* Listado y visualización de películas, series, música y videjuegos.
* Detalles (título, portada, sinopsis, géneros, tráiler de YouTube).
* Funcionalidad de añadir y eliminar medios audiovisuales de favoritos (requiere autenticación).
* Sistema de comentarios (requiere autenticación).
* Panel de administración para la gestión de medios (crear, editar, eliminar - solo para administradores).
* Moderación de comentarios (eliminar comentarios - solo para administradores).
* Autenticación de usuarios y gestión de roles (usuarios normales y administradores).
* Sistema de busqeda de medios y usuarios.
* Administración de usuarios (detalles y eliminación)

**Próximas Funcionalidades:**

* El admin pueda resetear la contraseña de un user
* Resetear la contraseña del admin la primera vez que se lanza la aplicación
* Opción en el login para recuperar contraseña
* Verificar si el mail ya existe al registrar
* Implementar expresión regular para email al registrar
* Enviar mail de confirmación de registro
* Refactorizar código, sobre todo herencia
* Implementar uso de APIS externas para al obtención de datos

**Tecnologías Utilizadas:**

* Java, Spring Boot, Thymeleaf, HTML, CSS, MySQL.

**Cómo Ejecutar el Proyecto (para desarrolladores):**

1.  Clonar el repositorio: `git clone https://github.com/dolthub/dolt`
2.  Configurar la base de datos (detalles en `src/main/resources/application.properties` o similar).
3.  Ejecutar la aplicación Spring Boot (normalmente desde tu IDE o con `./mvnw spring-boot:run`).
4.  Lanzar Script DML en caso de querer disponer de datos demo.

**Contribuciones:**

Las contribuciones son bienvenidas. Por favor, revisa la guía de contribuciones (si la tienes) antes de crear un pull request.
