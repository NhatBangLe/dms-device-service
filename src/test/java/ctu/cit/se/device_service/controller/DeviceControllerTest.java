package ctu.cit.se.device_service.controller;

import ctu.cit.se.device_service.DeviceServiceApplicationTests;
import ctu.cit.se.device_service.constant.DeviceType;
import ctu.cit.se.device_service.entity.Device;
import ctu.cit.se.device_service.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static io.restassured.RestAssured.given;

class DeviceControllerTest extends DeviceServiceApplicationTests {

    @Autowired
    private DeviceRepository deviceRepository;

    private String randomUUID;

    @BeforeEach
    void init() {
        this.randomUUID = UUID.randomUUID().toString();
    }

    @Test
    void getAllDevicesWithInvalidDeviceType_shouldReturnBadRequest() {
        given(requestSpecification)
                .param("type", "invalid")
                .when()
                .get("/device")
                .then()
                .statusCode(400);
    }

    @Test
    void getAllDevicesWithValidDeviceType_shouldReturnOK() {
        given(requestSpecification)
                .param("type", "CAMERA")
                .when()
                .get("/device")
                .then()
                .statusCode(200);
    }

    @Test
    void getDeviceWithUnavailableDeviceId_shouldReturnNotFound() {
        given(requestSpecification)
                .pathParam("deviceId", "not-found")
                .when()
                .get("/device/{deviceId}")
                .then()
                .statusCode(404);
    }

    @Test
    void getDeviceWithAvailableDeviceId_shouldReturnOK() {
        var deviceId = deviceRepository.save(
                Device.builder()
                        .id(randomUUID)
                        .type(DeviceType.ANY)
                        .username("test")
                        .password("test")
                        .build()
        ).getId();
        given(requestSpecification)
                .pathParam("deviceId", deviceId)
                .when()
                .get("/device/{deviceId}")
                .then()
                .statusCode(200);
    }

    @Test
    void addExistedDeviceWithBlankDeviceId_shouldReturnBadRequest() {
        given(requestSpecification)
                .body("""
                        {
                            "deviceId": "        ",
                            "type": "CAMERA",
                            "username": "test",
                            "password": "test",
                            "url": "http://localhost:8080"
                        }""")
                .when()
                .post("/device")
                .then()
                .statusCode(400);
    }

    @Test
    void addExistedDeviceWithNullDeviceType_shouldReturnCreated() {
        given(requestSpecification)
                .body("""
                        {
                            "deviceId": "%s",
                            "username": "test",
                            "password": "test",
                            "url": "http://localhost:8080"
                        }""".formatted(randomUUID))
                .when()
                .post("/device")
                .then()
                .statusCode(201);
    }

    @Test
    void addExistedDevice_shouldReturnCreated() {
        given(requestSpecification)
                .body("""
                        {
                            "deviceId": "%s",
                            "type": "CAMERA",
                            "username": "test",
                            "password": "test",
                            "url": "http://localhost:8080"
                        }""".formatted(randomUUID))
                .when()
                .post("/device")
                .then()
                .statusCode(201);
    }

    @Test
    void updateDeviceWithUnavailableDeviceId_shouldReturnNotFound() {
        given(requestSpecification)
                .pathParam("deviceId", "not-found")
                .body("""
                        {
                            "type": "ANY",
                            "username": "test2",
                            "password": "test",
                            "url": "http://localhost:8081"
                        }""")
                .when()
                .patch("/device/{deviceId}")
                .then()
                .statusCode(404);
    }

    @Test
    void updateDeviceWithInvalidDeviceType_shouldReturnBadRequest() {
        var deviceId = deviceRepository.save(
                Device.builder()
                        .id(randomUUID)
                        .type(DeviceType.ANY)
                        .username("test")
                        .password("test")
                        .build()
        ).getId();
        given(requestSpecification)
                .pathParam("deviceId", deviceId)
                .body("""
                        {
                            "type": "UNKNOWN",
                            "username": "test2",
                            "password": "test",
                            "url": "http://localhost:8081"
                        }""")
                .when()
                .patch("/device/{deviceId}")
                .then()
                .statusCode(400);
    }

    @Test
    void updateDevice_shouldReturnNoContent() {
        var deviceId = deviceRepository.save(
                Device.builder()
                        .id(randomUUID)
                        .type(DeviceType.ANY)
                        .username("test")
                        .password("test")
                        .build()
        ).getId();
        given(requestSpecification)
                .pathParam("deviceId", deviceId)
                .body("""
                        {
                            "type": "ANY",
                            "username": "test2",
                            "password": "test",
                            "url": "http://localhost:8081"
                        }""")
                .when()
                .patch("/device/{deviceId}")
                .then()
                .statusCode(204);
    }

    @Test
    void deleteDeviceWithUnavailableDeviceId_shouldReturnNotFound() {
        given(requestSpecification)
                .pathParam("deviceId", "not-found")
                .when()
                .delete("/device/{deviceId}")
                .then()
                .statusCode(404);
    }

    @Test
    void deleteDevice_shouldReturnNoContent() {
        var deviceId = deviceRepository.save(
                Device.builder()
                        .id(randomUUID)
                        .type(DeviceType.ANY)
                        .username("test")
                        .password("test")
                        .build()
        ).getId();
        given(requestSpecification)
                .pathParam("deviceId", deviceId)
                .when()
                .delete("/device/{deviceId}")
                .then()
                .statusCode(204);
    }

}