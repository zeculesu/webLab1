FROM openjdk:17-jdk

EXPOSE 1337

COPY target/fastCGI-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar

ENTRYPOINT ["java", "-DFCGI_PORT=1337", "-jar", "app.jar"]