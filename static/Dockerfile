FROM nginx:stable-alpine

COPY nginx.conf /etc/nginx/nginx.conf

COPY . /usr/share/nginx/html

WORKDIR /usr/share/nginx/html

EXPOSE 24598
CMD ["nginx", "-g", "daemon off;"]