FROM eclipse-temurin:21-jdk-jammy AS build
COPY . /dms-device
WORKDIR /dms-device
RUN ./mvnw clean install -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-jammy
RUN addgroup --system spring && adduser --system spring && adduser spring spring
USER spring:spring
COPY --from=build /dms-device/target/device-service.jar /dms-device/app.jar
WORKDIR /dms-device
CMD ["java", "-jar", "app.jar"]