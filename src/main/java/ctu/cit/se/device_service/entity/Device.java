package ctu.cit.se.device_service.entity;

import ctu.cit.se.device_service.constant.DeviceType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "device")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @Column(length = 100)
    private String id;

    @Column
    private String url;

    @Column(length = 100)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(nullable = false)
    private DeviceType type;
}