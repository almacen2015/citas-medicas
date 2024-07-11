# Crea una imagen de docker con la aplicación
FROM eclipse-temurin:17.0.6_10-jdk
# Crea la carpeta app
WORKDIR /app
# Copia el jar generado en la carpeta target
COPY target/backend-citas-medicas-0.0.1-SNAPSHOT.jar app.jar
# Expone el puerto 8080
EXPOSE 8080
# Ejecuta el jar
ENTRYPOINT ["java", "-jar", "app.jar"]