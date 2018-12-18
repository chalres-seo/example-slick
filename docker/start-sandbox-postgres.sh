#!/bin/bash

docker run -d \
--name sandbox-postgres \
--hostname sandbox-postgres \
--net test-net \
--ip 172.18.0.31 \
-e POSTGRES_PASSWORD=postgres \
postgres:latest