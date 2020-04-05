FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/moxpoxy.web.service.jar
COPY ${JAR_FILE} moxpoxy.web.service.jar
ENTRYPOINT ["java","-jar","/moxpoxy.web.service.jar"]