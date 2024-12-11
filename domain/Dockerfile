FROM openjdk:23-jdk-slim
ARG JAR_FILE=target/domain-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} domain-0.0.1-SNAPSHOT.jar
EXPOSE 8082/tcp
EXPOSE 8082/udp
ENTRYPOINT ["java","-jar","/domain-0.0.1-SNAPSHOT.jar"]
HEALTHCHECK --interval=15s --retries=5 --start-period=10s --timeout=10s CMD curl -o /dev/null domain:8082/actuator/health || true
