#!/bin/bash

HEALTHCHECK_HOST=${HEALTHCHECK_HOST-localhost:8091}

status="fail"
max_checks=10

set +e
for i in `seq 1 $max_checks`; do
  echo "${i}. try"

  out=`curl -fsS --max-time 5 "http://$HOST/healthcheck"`

  if [ $? -eq 0 ]; then
    status="success"
    echo $status
    break
  else
    sleep 1
  fi
done
set -e

if [ "${status}" != "success" ]; then
  echo "healthcheck failed"
  exit 1
fi