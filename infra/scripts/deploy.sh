#!/bin/bash

SERVICE_NAME=${SERVICE_NAME-sandboxer}
SERVICE_ROOT=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/../..
DEPLOY_DIR="vagrant@12.12.12.12:/environment/$STAGE"

STAGE=${STAGE-dev}
STAGE=$(echo $STAGE | tr '[A-Z]' '[a-z]')

echo "create dist-$SERVICE_NAME.zip"
cd $SERVICE_ROOT/build/dist/
chmod +x ./scripts/*.sh
zip -r dist-$SERVICE_NAME.zip .

echo "deploy dist-$SERVICE_NAME.zip on stage $STAGE"
scp -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" dist-$SERVICE_NAME.zip $DEPLOY_DIR