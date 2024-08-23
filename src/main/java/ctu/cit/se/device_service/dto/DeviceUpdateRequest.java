package ctu.cit.se.device_service.dto;

import ctu.cit.se.device_service.constant.DeviceType;
import ctu.cit.se.device_service.entity.Device;

import java.io.Serializable;

/**
 * DTO for {@link Device}
 */
public record DeviceUpdateRequest(
        DeviceType type,
        String url,
        String username,
        String password
) implements Serializable {
}