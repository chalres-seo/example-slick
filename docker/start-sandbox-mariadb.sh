#!/bin/bash

docker run -d \
--name sandbox-mariadb \
--hostname sandbox-mariadb \
--net test-net \
--ip 172.18.0.32 \
-e MYSQL_ROOT_PASSWORD=mariadb \
-e MYSQL_DATABASE=DB_FOR_TEST \
mariadb:latest \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci