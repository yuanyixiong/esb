#!/usr/bin/env bash
set -e

CREDENTIALS="admin:admin"
if [ ! -z "$1" ]; then
  CREDENTIALS=$1
fi

echo 'Configuring Grafana...'
add_prometheus() {
  curl "http://${CREDENTIALS}@localhost:3000/api/datasources" \
    -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    --data-binary \
    '{"name":"Prometheus","type":"prometheus","url":"http://prom:9090","access":"proxy","isDefault":true}'
}

add_dashboard() {
  echo
  echo "Adding $1 dashboard"
  curl "http://${CREDENTIALS}@localhost:3000/api/dashboards/db" \
    -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    --data-binary \
    @$1
}

until add_prometheus; do
  sleep 1
done
for dashboard in etc/grafana/dashboards/api/*; do
  until add_dashboard $dashboard; do
    sleep 1
  done
done
echo
echo 'Done!'
wait
