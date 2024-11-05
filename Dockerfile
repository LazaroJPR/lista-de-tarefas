# Use uma imagem base do Maven para construir o projeto
FROM maven:3.8.5-openjdk-17 AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie o pom.xml e baixe as dependências
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use uma imagem base do OpenJDK para rodar o aplicativo
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o JAR gerado da etapa de construção
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta que o aplicativo irá usar
EXPOSE 8080

# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
