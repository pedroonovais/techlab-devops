package com.techlab.iot;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotDTO {
    private Long id;
    private Boolean ativo;
    private Integer bateria;

    private Long motoId;
    private String motoPlaca; // opcional, só pra exibir

    private Long patioId;     // útil para filtrar o select de Motos por pátio
    private String patioNome; // opcional, exibição

    private String coordenadaX;
    private String coordenadaY;
}
