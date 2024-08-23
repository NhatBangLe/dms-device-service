package ctu.cit.se.device_service.dto;

import ctu.cit.se.device_service.constant.DeviceType;
import ctu.cit.se.device_service.entity.Device;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link Device}
 */
public record DeviceCreateRequest(
        @NotBlank(message = "Device ID cannot be null/blank.")
        String deviceId,
        DeviceType type,
        String url,
        String username,
        String password
) implements Serializable {
}