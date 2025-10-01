package com.techlab.patio;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatioDTO {

    private Long id;

    @NotBlank(message = "O nome do pátio é obrigatório.")
    private String nome;
}
