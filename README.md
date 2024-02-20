# Setup
## NginX
The following nginx config is used, where unnecessary fluff has been pulled out.

```
map $http_upgrade $connection_upgrade {
    default upgrade;
    '' close;
}

server {
    listen 0.0.0.0:80;
    listen [::]:80;

    server_name notquitehuman.local notquitehuman.co.uk www.notquitehuman.co.uk;

    location /.well-known/acme-challenge/ {
        root /var/www/certbot;
    }
}

server {
    #ipv4
    listen 443 default_server ssl http2;
    #ipv6
    listen [::]:443 ssl http2;

    server_name notquitehuman.co.uk www.notquitehuman.co.uk;

    error_log /var/log/nginx/error.log debug;
    access_log /var/log/nginx/access.log main;

    root /var/www/notquitehuman/public;

    ssl_certificate /etc/letsencrypt/live/notquitehuman.co.uk/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/notquitehuman.co.uk/privkey.pem;

    location = /.git/ {
        return 404;
    }

    location = /test/ {
        proxy_set_header X-Forwarded-Host $host;
	      proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass http://tomcat:8080/test/;
    }

    location /canvas-filler/ {
        proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass http://tomcat:8080/canvas-filler/;
    }

    location /chat-websocket/ {
#	return 418;
        proxy_pass http://tomcat:8080/chat-example/;
        proxy_http_version 1.1;
        proxy_set_header HOST $host;
        proxy_set_header X_Forwarded_For $remote_addr;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection $connection_upgrade;
    }

    location /ws/ {
#	return 418;
        proxy_pass http://tomcat:8080/chat-example/;
        proxy_http_version 1.1;
        proxy_set_header HOST $host;
        proxy_set_header X_Forwarded_For $remote_addr;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection $connection_upgrade;
    }

    ###############################################################
    # exclude /favicon.ico from logs
    location = /favicon.ico {
#<snip>
    }

    ##############################################################
    # Disable logging for robots.txt
    location = /robots.txt {
#<snip>
    }


    location / {
        # try to serve file directly, fallback to index.php
        try_files $uri /index.php$is_args$args;
    }

    location ~ ^/index\.php(/|$) {
#<snip>
   }

   # return 404 for all other php files not matching the front controller
   # this prevents access to other php files you don't want to be accessible.
   location ~ \.php$ {
#<snip>
   }

}
```
