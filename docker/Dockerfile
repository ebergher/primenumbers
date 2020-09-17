# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=primesapp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} primes.jar
ENTRYPOINT ["java","-jar","/primes.jar"]
