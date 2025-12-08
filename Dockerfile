# 1. Construcción: Usa la imagen de Maven para compilar el código.
FROM maven:3.9.6-eclipse-temurin-17 AS build
# Copia todo el contenido del contexto de la carpeta al contenedor.
COPY . .
# Ejecuta la compilación de Maven.
RUN mvn clean package -DskipTests

# 2. Ejecución: Usa una imagen más ligera (solo JRE) para ejecutar la app.
FROM eclipse-temurin:17-jdk-alpine
# Copia el archivo JAR compilado desde la etapa 'build' a la etapa 'run'.
COPY --from=build /target/proyecto-software-0.0.1-SNAPSHOT.jar app.jar
# Define el comando de inicio.
ENTRYPOINT ["java","-jar","/app.jar"]