#!/bin/bash

SERVICE_NAME="sandboxer"
GO_PIPELINE_NAME=${GO_PIPELINE_NAME-local}

echo "copy files for dist.zip"
cp ./src/dist/config/dev.yml config-$SERVICE_NAME.yml
cp ./build/libs/$SERVICE_NAME-fat.jar app-$SERVICE_NAME.jar

echo "create dist.zip"
zip dist-$SERVICE_NAME.zip config-$SERVICE_NAME.yml app-$SERVICE_NAME.jar

echo "deploy dist.zip (wherever)"
# need an ssh key
scp -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" dist-$SERVICE_NAME.zip vagrant@12.12.12.12:/prod