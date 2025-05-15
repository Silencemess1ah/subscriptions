#!/bin/bash
./gradlew clean
./gradlew bootJar
docker-compose build
docker-compose up -d
echo "Приложение успешно запущено!"