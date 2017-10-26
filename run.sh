#!/bin/bash

# 使用这种方式将输出台的信息全部发送到黑洞，但是项目正常的log文件输出还是正常的
	# 也说明了java的日志输出是当前jar运行路径下新建 log 目录然后输出log文件
# 可以配置 alias redis_client='sh yourpath/run.sh' 方便运行
cd `dirname $0`
nohup java -jar `find . -name *-all.jar` -Xmx1024m -Xms1024m -Xmn512m >/dev/null 2>&1
