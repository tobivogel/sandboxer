#! /bin/bash

SERVICE_NAME=${SERVICE_NAME-sandboxer}
SERVICE_ROOT=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

echo "build and test service artifact"
$SERVICE_ROOT/gradlew clean test buildDist

echo "package dist-$SERVICE_NAME.zip"
cd $SERVICE_ROOT/build/dist/
chmod +x ./scripts/*.sh
zip -r dist-$SERVICE_NAME.zip .