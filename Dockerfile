# Stage 1: Build the application
# Usamos Java 17 y Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

# 1. Copiar solo el pom.xml para descargar dependencias
COPY pom.xml .

# 2. Descargar todas las dependencias
# El comando RUN intentará descargar el Parent POM 4.0.0 usando los repositorios que definiste en pom.xml
RUN mvn dependency:go-offline

# 3. Copiar el resto del proyecto
COPY . .

# 4. Compilar y empaquetar
# El proceso debe ser exitoso aquí si la descarga de dependencias funcionó.
RUN mvn clean package -DskipTests

# Stage 2: Run the application
# Usamos una imagen ligera de solo Java Runtime Environment (JRE) para producción
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 1. Copiar el JAR desde la etapa de 'build'
# El nombre del archivo JAR debe coincidir con el artifactId y version
COPY --from=build /target/proyecto-software-0.0.1-SNAPSHOT.jar app.jar

# 2. Ejecutar el JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]