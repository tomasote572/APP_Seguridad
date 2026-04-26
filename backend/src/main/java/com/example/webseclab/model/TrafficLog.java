package com.example.webseclab.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "traffic_logs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TrafficLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String protocolName;
    private String sourceIp;
    private String destinationIp;

    @Column(length = 1800)
    private String observation;

    private boolean sensitiveDataExposed;
}
