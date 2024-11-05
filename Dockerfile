# Etapa 1: Construir a imagem
FROM maven:3.8.6-openjdk-17 AS build

# Definir o diretório de trabalho
WORKDIR /app

# Copiar os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Executar o Maven para compilar o projeto
RUN mvn clean package -DskipTests

# Etapa 2: Criar a imagem de execução
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR gerado pela etapa de construção
COPY --from=build /app/target/*.jar app.jar

# Expor a porta em que o aplicativo será executado
EXPOSE 8080

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
