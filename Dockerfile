FROM openjdk:11-jre-slim
VOLUME /app
COPY target/app.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-Duser.timezone=America/Sao_Paulo", "-jar","./app.jar"]