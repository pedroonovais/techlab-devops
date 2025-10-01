package com.techlab.moto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotoDTO {

    private Long id;

    @NotBlank(message = "O modelo da moto é obrigatório.")
    private String modelo;

    @NotBlank(message = "A placa da moto é obrigatória.")
    private String placa;

    @NotNull(message = "A data de entrada é obrigatória.")
    private LocalDateTime dataEntrada;

    private LocalDateTime dataSaida;
}
