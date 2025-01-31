FROM maven:3-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip -DskipTests -Dmaven.javadoc.skip=true

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/isod-feed-ios-notifier.jar .
CMD ["java", "-jar", "isod-feed-ios-notifier.jar"]