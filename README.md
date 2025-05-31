# UniTutor



## Proyecto de Java
Tecnologias
* Java +17
* JDBC (no olvidar agregar el driver de mysql) y autenticar la base de datos (usuario y contraseña)


## Recien se inicio el proyecto, por lo que no hay mucho que mostrar.

### Menu

Que hace el proyecto autentica al usuario y segun el rol que tenga lo redirige a un menu diferente.

## 🛢️ Base de datos

## 🛢️ 1‑2‑3: Base de datos en tres pasos

1.  Crea una base de datos llamada **to_do_list** en tu instancia MySQL  
    (puedes hacerlo con MySQL Workbench o con `CREATE DATABASE to_do_list`).
2.  Abre `src/main/org.example/database/DbConnection` y pon tus credenciales:

    ```
    private static final String URL = "jdbc:mysql://localhost:3306/-";
    private static final String USER = "-";
    private static final String PASSWORD = "-";
    ```

Listo: al iniciar la aplicación se conectará a esa BD.
---

### Models
Se crearon las clases de los modelos, pero no se han implementado los metodos de la base de datos.
Por el momento solo del modelo de usuario.

### Services
Se crearon las interfaces de los servicios de autenticacion, pero no se han implementado los metodos de la base de datos.

### Menus
Se crearon  los menus de la aplicacion para estudiantes y maestro (falta implementar los metodos).



