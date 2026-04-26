package com.example.webseclab.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mitigations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Mitigation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finding_id")
    private VulnerabilityFinding finding;

    private String controlType;
    private String priorityLevel;
    private String status;

    @Column(length = 1800)
    private String actionPlan;
}
