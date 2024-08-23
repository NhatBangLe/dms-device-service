package ctu.cit.se.device_service.repository;

import ctu.cit.se.device_service.constant.DeviceType;
import ctu.cit.se.device_service.entity.Device;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, String> {

    List<Device> findAllByType(@NonNull DeviceType type, Pageable pageable);

}