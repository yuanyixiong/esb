#!/bin/bash

TRIFECTA_CONFIG="$1"

# Setup trifecta configuration
if [ -z "${ZK_HOST}" ]; then
  echo "Please set ZK_HOST env variable"
  exit 1
fi

echo "Using Zookeeper host: '$ZK_HOST'";
sed -i -e s#{{ZK_HOST}}#${ZK_HOST}# "$TRIFECTA_CONFIG"

# Start ui
/home/trifecta/trifecta_ui/bin/trifecta_ui -Dpidfile.path=/dev/null
