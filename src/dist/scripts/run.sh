#!/bin/bash

SERVICE_NAME="sandboxer"

echo "run $SERVICE_NAME service"
ssh -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" vagrant@12.12.12.12:/prod 'unzip dist-$SERVICE_NAME.zip -d $SERVICE_NAME'
ssh -i /root/.ssh/id_rsa -o "StrictHostKeyChecking no" vagrant@12.12.12.12:/prod 'java -jar app-$SERVICE_NAME.jar server config-$SERVICE_NAME.yml'