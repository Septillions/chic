#!/bin/bash
app_name="chic-admin"
docker_registry="registry.cn-chengdu.aliyuncs.com/septillions"
echo "1.停止容器"
docker stop ${app_name}
echo "2.删除容器"
docker rm ${app_name}
echo "3.删除镜像"
docker rmi ${docker_registry}/${app_name}
echo "4.拉取镜像"
docker pull ${docker_registry}/${app_name}
echo "5.运行容器"
docker run -d \
-p 8082:8080 \
-v /docker/${app_name}/logs:/logs \
-v /docker/skywalking:/skywalking \
-v /etc/localtime:/etc/localtime:ro \
-e SPRING_PROFILES_ACTIVE=test \
-e TZ=Asia/Shanghai \
--name=${app_name} \
--network=project-network \
${docker_registry}/${app_name}
