FROM maven:3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip -DskipTests -Dmaven.javadoc.skip=true

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/isod-feed-ios-notifier.jar .
CMD ["java", "-jar", "isod-feed-ios-notifier.jar"]