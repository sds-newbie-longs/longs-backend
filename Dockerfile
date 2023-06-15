# Base image
FROM adoptopenjdk:11-jdk-hotspot

# 애플리케이션의 JAR 파일을 /app 디렉토리로 복사합니다
COPY build/libs/act-longs-0.0.1-SNAPSHOT.jar /app/act-longs-0.0.1-SNAPSHOT.jar

RUN apt-get update && apt-get install -y ffmpeg

# 애플리케이션의 포트를 노출합니다 
EXPOSE 8080

# 애플리케이션 실행 명령어를 지정합니다
CMD ["sh", "-c", "java -jar -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE \
    -DAWS_ACCESS_KEY=$AWS_ACCESS_KEY \
    -DAWS_SECRET_KEY=$AWS_SECRET_KEY /app/act-longs-0.0.1-SNAPSHOT.jar"]
