package ctu.cit.se.device_service.service;

import ctu.cit.se.device_service.constant.DeviceType;
import ctu.cit.se.device_service.dto.DeviceCreateRequest;
import ctu.cit.se.device_service.dto.DeviceResponse;
import ctu.cit.se.device_service.dto.DeviceUpdateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface IDeviceService {

    List<DeviceResponse> getAllDevices(
            @NotNull(message = "Device type cannot be null.")
            DeviceType type,
            @Min(value = 0, message = "Invalid page number (must positive).")
            @NotNull(message = "Page number cannot be null.")
            Integer pageNumber,
            @Min(value = 1, message = "Invalid page size (must greater than 0).")
            @NotNull(message = "Page size cannot be null.")
            Integer pageSize
    );

    DeviceResponse getDevice(
            @NotBlank(message = "Device ID cannot be null/blank.")
            String deviceId
    );

    String addDevice(
            @Valid
            @NotNull(message = "Creating data cannot be null.")
            DeviceCreateRequest deviceCreateRequest
    );

    void updateDevice(
            @NotBlank(message = "Device ID cannot be null/blank.")
            String deviceId,
            @NotNull(message = "Updating data cannot be null.")
            DeviceUpdateRequest deviceUpdateRequest
    );

    void deleteDevice(
            @NotBlank(message = "Device ID cannot be null/blank.")
            String deviceId
    );

}
