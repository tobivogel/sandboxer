#!/bin/bash

SERVICE_NAME=${SERVICE_NAME-sandboxer}

STAGE=${STAGE-dev}
STAGE=$(echo $STAGE | tr '[A-Z]' '[a-z]')
DEPLOY_DIR="vagrant@12.12.12.12:/environment/$STAGE"

echo "deploy dist-$SERVICE_NAME.zip on stage $STAGE"
scp -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" dist-$SERVICE_NAME.zip $DEPLOY_DIR