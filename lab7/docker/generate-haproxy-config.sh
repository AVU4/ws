#!/bin/bash

CONFIG_FILE=/usr/local/etc/haproxy/haproxy.cfg

sleep 30

echo "
    defaults
        mode http

    frontend my_frontend
        bind :8080
        default_backend my_servers

    backend my_servers
        server app app:8081 check" > $CONFIG_FILE

JSON=$(curl -s http://consul:8500/v1/catalog/service/my-app)
SERVICES=`jq -n "$JSON" | jq -r 'keys[]'`
for SERVICE in $SERVICES; do
  SERVICE_NAME=`jq -n "$JSON" | jq ".[$SERVICE].ServiceID"`
  SERVICE_ADDRESS=`jq -n "$JSON" | jq ".[$SERVICE].ServiceAddress"`
  SERVICE_PORT=`jq -n "$JSON" | jq ".[$SERVICE].ServicePort"`
  echo "    server $SERVICE_NAME $SERVICE_ADDRESS:$SERVICE_PORT check" >> /usr/local/etc/haproxy/test.txt
  echo "    server $SERVICE_NAME $SERVICE_ADDRESS:$SERVICE_PORT check" >> $CONFIG_FILE
done

exec haproxy -f $CONFIG_FILE
