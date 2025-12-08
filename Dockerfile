# Stage 1: Build the application
# Usamos una imagen de Java/Maven más estable (focal) para la fase de construcción
FROM eclipse-temurin:17-jdk-focal AS build

# Copiamos todo el proyecto al directorio de trabajo del contenedor
COPY . .

# Compilar y empaquetar
# Esta es la línea que está fallando. Si pom.xml tiene el repositorio, ahora debería pasar.
RUN mvn clean package -DskipTests

# Stage 2: Run the application
# Usamos una imagen de solo Java Runtime Environment (JRE) más ligera para producción
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR compilado (revisa que el nombre sea correcto)
COPY --from=build /target/proyecto-software-0.0.1-SNAPSHOT.jar app.jar

# Ejecutar el JAR
ENTRYPOINT ["java","-jar","/app/app.jar"]