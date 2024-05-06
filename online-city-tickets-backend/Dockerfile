FROM openjdk:17-alpine AS TEMP_BUILD_IMAGE
COPY build.gradle settings.gradle gradlew /
COPY gradle /gradle
COPY src /src
RUN ./gradlew build
RUN ./gradlew bootJar

# Production
FROM openjdk:17-alpine
COPY --from=TEMP_BUILD_IMAGE /build/libs/online-city-tickets-backend-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","online-city-tickets-backend-0.0.1-SNAPSHOT.jar"]
