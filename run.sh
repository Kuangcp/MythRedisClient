#!/bin/bash
nohup java -jar ./redis_client/build/libs/redis_client-1.0-all.jar -Xmx1024m -Xms1024m -Xmn512m >/dev/null &

echo "Running"
exit

