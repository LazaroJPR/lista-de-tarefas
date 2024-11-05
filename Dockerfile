# Usar uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim as builder

# Instalar o Maven
RUN apt-get update && apt-get install -y maven

# Configurar diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml e a pasta src para a imagem
COPY pom.xml .
COPY src ./src

# Executar o Maven para compilar o projeto
RUN mvn clean package -DskipTests

# Usar uma imagem base do OpenJDK para rodar o aplicativo
FROM openjdk:17-jdk-slim

# Copiar o jar do build anterior
COPY --from=builder /app/target/lista-de-tarefas-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta que o aplicativo irá rodar
EXPOSE 8080

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "/app.jar"]
