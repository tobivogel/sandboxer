#!/bin/bash

LIB_DIR=`pwd`
source $LIB_DIR/common.sh

echo "run $SERVICE_NAME service"
cd $DEPLOY_DIR
java -jar $SERVICE_NAME-service.jar server $SERVICE_NAME-config.yml