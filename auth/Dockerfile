FROM maven:3.8.7-eclipse-temurin-17

WORKDIR /app

# Apenas preparamos o ambiente
COPY pom.xml .
RUN mvn dependency:go-offline

EXPOSE 8080
EXPOSE 5005

# Removemos o COPY dos arquivos fonte, pois virão do volume
# Removemos o ENTRYPOINT/CMD pois será definido no docker-compose