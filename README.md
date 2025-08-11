# 📚 Literalura – Catálogo de Libros con Gutendex API

Proyecto desarrollado como parte del programa Oracle Next Education (ONE) – Alura Latam.  
La aplicación permite buscar y registrar libros desde la API pública Gutendex, almacenarlos en una base de datos PostgreSQL y realizar consultas desde consola usando Java Spring Boot.

## 🚀 Funcionalidades
1. Buscar libro por título (API Gutendex) y guardar en base de datos
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un año determinado
5. Listar libros por idioma
6. Salir de la aplicación

La opción 1 siempre toma el primer resultado encontrado en la API Gutendex.

## 🛠 Tecnologías utilizadas
- Java 17 o superior
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Gutendex API
- Maven

## 📂 Estructura del proyecto
literalura/  
 ├─ .gitignore  
 ├─ pom.xml  
 ├─ README.md  
 └─ src/  
     ├─ main/  
     │   ├─ java/com/example/literalura/  
     │   │   ├─ LiteraluraApplication.java         (Clase principal con menú en consola)  
     │   │   ├─ config/RestTemplateConfig.java     (Configuración de RestTemplate)  
     │   │   ├─ domain/                            (Entidades JPA)  
     │   │   │   ├─ Author.java  
     │   │   │   └─ Book.java  
     │   │   ├─ dto/                               (Clases DTO para Gutendex)  
     │   │   │   ├─ GutendexAuthorDto.java  
     │   │   │   ├─ GutendexBookDto.java  
     │   │   │   └─ GutendexResponse.java  
     │   │   ├─ repository/                        (Interfaces Repository)  
     │   │   │   ├─ AuthorRepository.java  
     │   │   │   └─ BookRepository.java  
     │   │   └─ service/                           (Lógica de negocio)  
     │   │       ├─ GutendexClient.java  
     │   │       └─ LibraryService.java  
     │   └─ resources/  
     │       ├─ application.properties  
     │       └─ banner.txt (opcional)  
     └─ test/ (opcional para pruebas unitarias)  

## ⚙️ Configuración
1. Variables de entorno  
   Antes de ejecutar, define en tu sistema las variables necesarias:  
   DB_HOST=localhost:5432  
   DB_USER_POST=postgres  
   DB_PASS_POST=postgres  

2. application.properties  
   El proyecto ya está configurado para leer las variables de entorno:  
   spring.application.name=literalura  
   spring.datasource.url=jdbc:postgresql://${DB_HOST}/literalura  
   spring.datasource.username=${DB_USER_POST}  
   spring.datasource.password=${DB_PASS_POST}  
   spring.datasource.driver-class-name=org.postgresql.Driver  
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect  
   spring.jpa.hibernate.ddl-auto=update  
   spring.jpa.show-sql=true  
   spring.jpa.properties.hibernate.format_sql=true  

## 🗄 Creación de la base de datos
En PostgreSQL, crear la base de datos vacía:  
CREATE DATABASE literalura;  
No es necesario crear tablas manualmente, Spring JPA generará la estructura a partir de las entidades.

## ▶️ Ejecución
1. Clonar el repositorio:  
   git clone https://github.com/robertytocerva/ConsultorDeLibrosGutendex.git  
   cd literalura  

2. Compilar y ejecutar:  
   mvn spring-boot:run  

3. Usar el menú en consola:  
   ===== LITERALURA =====  
   1 - Buscar libro por título (Gutendex) y guardar  
   2 - Listar libros registrados  
   3 - Listar autores registrados  
   4 - Listar autores vivos en un año  
   5 - Listar libros por idioma  
   0 - Salir  

## 📌 Ejemplo de uso
Buscar libro por título:  
Título a buscar: Don Quijote  
Guardado: Don Quijote (GutendexId=500)  
Idiomas: es  
Autores: Miguel de Cervantes Saavedra  

Listar libros registrados:  
- Don Quijote | Idiomas: [es] | Autores: Miguel de Cervantes Saavedra | Descargas: 1500  


