package ctu.cit.se.device_service.controller;

import ctu.cit.se.device_service.constant.DeviceType;
import ctu.cit.se.device_service.dto.DeviceCreateRequest;
import ctu.cit.se.device_service.dto.DeviceResponse;
import ctu.cit.se.device_service.dto.DeviceUpdateRequest;
import ctu.cit.se.device_service.service.IDeviceService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device")
@RequiredArgsConstructor
@Tag(
        name = "Device Controller",
        description = "All endpoints about devices."
)
public class DeviceController {

    private final IDeviceService deviceService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(
            responseCode = "400",
            description = "Invalid page number or page size.",
            content = @Content
    )
    public List<DeviceResponse> getAllDevices(
            @RequestParam(required = false, defaultValue = "CAMERA") DeviceType type,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "6") Integer pageSize
    ) {
        return deviceService.getAllDevices(type, pageNumber, pageSize);
    }

    @GetMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(
            responseCode = "404",
            description = "Device not found.",
            content = @Content
    )
    public DeviceResponse getDevice(@PathVariable String deviceId) {
        return deviceService.getDevice(deviceId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "400",
            description = "Device ID is null/blank.",
            content = @Content
    )
    public String addExistedDevice(@RequestBody DeviceCreateRequest deviceCreateRequest) {
        return deviceService.addDevice(deviceCreateRequest);
    }

    @PatchMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid device type.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Device not found.",
                    content = @Content
            )
    })
    public void updateDevice(@PathVariable String deviceId,
                             @RequestBody DeviceUpdateRequest deviceUpdateRequest) {
        deviceService.updateDevice(deviceId, deviceUpdateRequest);
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "404", description = "Device not found.", content = @Content)
    public void deleteDevice(@PathVariable String deviceId) {
        deviceService.deleteDevice(deviceId);
    }

}
