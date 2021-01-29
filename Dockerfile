# Docker for java  sei-manager

# 基础镜像
FROM registry.cn-hangzhou.aliyuncs.com/brianchou/server-jre:8u281-alpine

# 维护者
LABEL maintainer="hua.feng@changhong.com"

# 环境变量
ENV JAVA_OPTS=""  APP_NAME="sei-manager"

# 拷贝包
ADD $APP_NAME-service/build/libs/$APP_NAME.jar $APP_NAME.jar

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["sh","-c","java -server -XX:+UseG1GC -XX:InitialRAMPercentage=75.0  -XX:MaxRAMPercentage=75.0 $JAVA_OPTS  -jar $APP_NAME.jar --server.servlet.context-path=/$APP_NAME --server.port=8080"]