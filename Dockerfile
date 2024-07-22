# Crea una imagen de docker con la aplicación
FROM eclipse-temurin:17.0.6_10-jdk
# Crea la carpeta app en la imagen de docker
WORKDIR /app
# Copia el jar generado en la carpeta target a la carpeta app de la imagen
COPY target/backend-citas-medicas-0.0.1-SNAPSHOT.jar app.jar
# Expone el puerto 8080 de la imagen de docker
EXPOSE 8080
# Ejecuta el jar al iniciar el contenedor de docker
ENTRYPOINT ["java", "-jar", "app.jar"]