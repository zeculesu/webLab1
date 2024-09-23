FROM alpine:3.20.3
RUN apk update && apk upgrade
RUN apk add nginx
RUN apk add openjdk17
COPY nginx.conf /etc/nginx/nginx.conf
COPY static /root/static
COPY target/fastCGI-1.0-SNAPSHOT-jar-with-dependencies.jar /root/web_server.jar
ENTRYPOINT nginx; java -DFCGI_PORT=24344 -jar /root/web_server.jar
EXPOSE 80