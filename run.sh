#!/bin/bash

nohup java -jar `find . -name *-all.jar` -Xmx1024m -Xms1024m -Xmn512m >/dev/null &

echo "Running"
exit

