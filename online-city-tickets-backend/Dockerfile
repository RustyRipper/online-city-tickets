FROM openjdk:21-jdk AS TEMP_BUILD_IMAGE
COPY build.gradle settings.gradle gradlew /
COPY gradle /gradle
COPY src /src
RUN microdnf install findutils
RUN ./gradlew assemble

# Production
FROM openjdk:21-jdk
COPY --from=TEMP_BUILD_IMAGE /build/libs/online-city-tickets-backend-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","online-city-tickets-backend-0.0.1-SNAPSHOT.jar"]

HEALTHCHECK --interval=30s --timeout=5s --retries=3 CMD wget --spider http://localhost:8080/api/v1/actuator/health || exit 1
