#!/bin/bash

LIB_DIR=`pwd`
SERVICE_ROOT=$LIB_DIR/../../../
source $LIB_DIR/common.sh

echo "create $SERVICE_NAME service fatJar"
cd SERVICE_ROOT
./gradlew clean fatJar

echo "copy jar and config file to deploy folder"
cd $SERVICE_ROOT
cp ./src/dist/config/dev.yml $DEPLOY_DIR/$SERVICE_NAME-config.yml
cp ./build/libs/sandboxerService-fat.jar $DEPLOY_DIR/$SERVICE_NAME-service.jar