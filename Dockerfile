FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
MAINTAINER Amila Harshana
COPY target/fileUploader-0.0.1-SNAPSHOT.jar fileUploader.jar
ENTRYPOINT ["java","-jar","/fileUploader.jar"]