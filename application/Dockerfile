FROM openjdk:23-jdk-slim
ARG JAR_FILE=target/application-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application-0.0.1-SNAPSHOT.jar
EXPOSE 8081/tcp
EXPOSE 8081/udp
ENTRYPOINT ["java","-jar","/application-0.0.1-SNAPSHOT.jar"]
HEALTHCHECK --interval=15s --retries=5 --start-period=10s --timeout=10s CMD curl -o /dev/null domain:8081/actuator/health || true
