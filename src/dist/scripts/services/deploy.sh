#!/bin/bash

SERVICE_NAME=${SERVICE_NAME-unknown}

echo "copy files for dist.zip"
cp ./src/dist/config/services/$SERVICE_NAME.yml config-$SERVICE_NAME.yml
cp ./dist/*-fat.jar app-$SERVICE_NAME.jar

echo "create dist.zip"
zip dist-$SERVICE_NAME.zip config-$SERVICE_NAME.yml app-$SERVICE_NAME.jar

echo "deploy dist.zip (wherever)"
scp -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" dist-$SERVICE_NAME.zip vagrant@12.12.12.12:/prod