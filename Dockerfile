# Dockerfile для Java-приложения
FROM openjdk:17-jdk
EXPOSE 1337
# Копируем jar файл
COPY target/fastCGI-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar
# Указываем точку входа
ENTRYPOINT ["java", "-DFCGI_PORT=1337", "-jar", "app.jar"]

# Открываем порт для FastCGI приложения