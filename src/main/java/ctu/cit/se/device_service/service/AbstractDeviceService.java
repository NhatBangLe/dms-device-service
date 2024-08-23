package ctu.cit.se.device_service.service;

import ctu.cit.se.device_service.dto.DeviceResponse;
import ctu.cit.se.device_service.entity.Device;

public abstract class AbstractDeviceService implements IDeviceService {

    protected DeviceResponse mapToResponse(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getType(),
                device.getUrl(),
                device.getUsername(),
                device.getPassword()
        );
    }

}
