FROM openjdk:18
VOLUME /tmp
EXPOSE 5500
ADD target/demo-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]