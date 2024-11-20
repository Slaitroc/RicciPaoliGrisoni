# Usa l'immagine leggera di OpenJDK 21
FROM openjdk:21-jdk-slim

# Imposta la directory di lavoro
WORKDIR /sc_server

# Copia il file JAR compilato nel container
COPY target/sc_server.jar sc_server.jar

# Comando per eseguire il server Spring Boot
ENTRYPOINT ["java", "-jar", "sc_server.jar"]
