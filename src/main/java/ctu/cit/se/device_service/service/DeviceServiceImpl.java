package ctu.cit.se.device_service.service;

import ctu.cit.se.device_service.constant.DeviceType;
import ctu.cit.se.device_service.dto.DeviceCreateRequest;
import ctu.cit.se.device_service.dto.DeviceResponse;
import ctu.cit.se.device_service.dto.DeviceUpdateRequest;
import ctu.cit.se.device_service.entity.Device;
import ctu.cit.se.device_service.exception.NoEntityFoundException;
import ctu.cit.se.device_service.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl extends AbstractDeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public List<DeviceResponse> getAllDevices(DeviceType type, Integer pageNumber, Integer pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        return deviceRepository.findAllByType(type, pageable)
                .parallelStream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DeviceResponse getDevice(String deviceId) throws NoEntityFoundException {
        return mapToResponse(findDevice(deviceId));
    }

    @Override
    public String addDevice(DeviceCreateRequest deviceCreateRequest) {
        var type = deviceCreateRequest.type();
        if (type == null) type = DeviceType.ANY;

        var newDevice = Device.builder()
                .id(deviceCreateRequest.deviceId())
                .type(type)
                .url(deviceCreateRequest.url())
                .username(deviceCreateRequest.username())
                .password(deviceCreateRequest.password())
                .build();
        return deviceRepository.save(newDevice).getId();
    }

    @Override
    public void updateDevice(String deviceId, DeviceUpdateRequest deviceUpdateRequest)
            throws NoEntityFoundException {
        var device = findDevice(deviceId);
        var isUpdated = false;

        var type = deviceUpdateRequest.type();
        if (type != null) {
            device.setType(type);
            isUpdated = true;
        }

        var url = deviceUpdateRequest.url();
        if (url != null) {
            device.setUrl(url);
            isUpdated = true;
        }

        var username = deviceUpdateRequest.username();
        if (username != null) {
            device.setUsername(username);
            isUpdated = true;
        }

        var password = deviceUpdateRequest.password();
        if (password != null) {
            device.setPassword(password);
            isUpdated = true;
        }

        if (isUpdated) deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(String deviceId) throws NoEntityFoundException {
        var device = findDevice(deviceId);
        deviceRepository.delete(device);
    }

    private Device findDevice(String deviceId) throws NoEntityFoundException {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new NoEntityFoundException("No device found with id: " + deviceId));
    }

}
