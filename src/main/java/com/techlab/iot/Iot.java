package com.techlab.iot;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "iot")
public class Iot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O status (ativo) é obrigatório.")
    private Boolean ativo;

    @Min(0) @Max(100)
    private Integer bateria;

    @NotBlank(message = "A coordenada X é obrigatória.")
    @Column(name = "coordenada_x", nullable = false) 
    private String coordenadaX;

    @NotBlank(message = "A coordenada Y é obrigatória.")
    @Column(name = "coordenada_y", nullable = false) 
    private String coordenadaY;
}
