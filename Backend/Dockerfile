FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./
COPY src ./src
RUN mvn --batch-mode -DskipTests package
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd --gid 10001 fooddelivery && useradd --uid 10001 --gid 10001 --no-create-home fooddelivery && mkdir -p /app/data/store-assets && chown -R 10001:10001 /app
COPY --from=build --chown=10001:10001 /build/target/food-delivery-api-*.jar /app/api.jar
USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/api.jar"]
