#!/bin/bash

docker run -d \
--name sandbox-mysql \
--hostname sandbox-mysql \
--net test-net \
--ip 172.18.0.33 \
-e MYSQL_ROOT_PASSWORD=mysql \
-e MYSQL_DATABASE=DB_FOR_TEST \
mysql:latest \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci