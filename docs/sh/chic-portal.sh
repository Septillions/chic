#!/bin/bash
app_name="chic-portal"
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
-p 8081:8080 \
-v /docker/chic-portal/logs:/logs \
-v /etc/localtime:/etc/localtime:ro \
-e TZ=Asia/Shanghai \
-e SPRING_PROFILES_ACTIVE=test \
--name=${app_name} \
--network=project-network \
${docker_registry}/${app_name}
