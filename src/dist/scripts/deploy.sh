#!/bin/bash

SERVICE_NAME="sandboxer"
DEPLOY_DIR="vagrant@12.12.12.12:/prod"

echo "copy files for dist-$SERVICE_NAME.zip"
cp ./src/dist/config/dev.yml config-$SERVICE_NAME.yml
cp ./build/libs/*-fat.jar app-$SERVICE_NAME.jar

echo "create dist-$SERVICE_NAME.zip"
zip dist-$SERVICE_NAME.zip config-$SERVICE_NAME.yml app-$SERVICE_NAME.jar

echo "deploy dist-$SERVICE_NAME.zip on $DEPLOY_DIR"
scp -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" dist-$SERVICE_NAME.zip $DEPLOY_DIR