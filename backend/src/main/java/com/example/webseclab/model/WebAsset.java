package com.example.webseclab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "web_assets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WebAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    @NotBlank
    private String url;

    private String technology;

    @Column(length = 1200)
    private String description;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VulnerabilityFinding> findings;
}
