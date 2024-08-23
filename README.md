# Device Service

## Tech

Using a number of open source projects to work properly:

- Spring Boot
- PostgreSQL

## Installation

Required:
- JDK 21+
- Docker

Configuration files:
1. Environment file (.env)
```sh
SERVICE_PORT=<host_port>
DB_PORT=<db_port>
DB_USER=<db_username>
DB_PASSWORD=<db_password>
DB_NAME=<db_name>
```
2. Dockerfile
```sh
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
```
3. docker-compose.yml - Example | It can be customed all values.
```sh
services:
  device-postgres-db:
    image: postgres:15.8
    container_name: dms-device-postgres-db
    ports:
      - "${DB_PORT}:5432"
    environment:
      POSTGRES_USER: ${DB_USER}
      POSTGRES_PASSWORD: ${DB_PASSWORD}
      POSTGRES_DB: ${DB_NAME}
    env_file:
      - .env
    healthcheck:
      test: [ "CMD-SHELL", "sh -c 'pg_isready -U ${DB_USER} -d ${DB_NAME}'" ]
      interval: 10s
      timeout: 10s
      retries: 3
    restart: always
    #    PostgreSQL data in /var/lib/postgresql/data
    volumes:
      - ./device-postgres-db:/var/lib/postgresql/data
    networks:
      - dms-device-network
  device-service:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: dms-device-service
    ports:
      - "${SERVICE_PORT}:8080"
    environment:
      ACTIVE_PROFILE: dev
      DB_HOST: device-postgres-db:5432
      ROOT_LOG_LEVEL: error
      WEB_LOG_LEVEL: debug
    env_file:
      - .env
    #    All service logs will be written in directory /dms-device/logs
    volumes:
      - ./logs:/dms-device/logs
    depends_on:
      device-postgres-db:
        condition: service_healthy
        restart: true
    networks:
      - dms-device-network
networks:
  dms-device-network:
```
