FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} target/company-test-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/company-test-0.0.1-SNAPSHOT.jar"]