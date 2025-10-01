package com.techlab.moto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;

    public List<MotoDTO> findAll() {
        return motoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MotoDTO findById(Long id) {
        return motoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
    }

    public MotoDTO save(MotoDTO dto) {
        Moto entity = toEntity(dto);
        // Se for criação, validar placa única
        if (entity.getId() == null && motoRepository.existsByPlaca(entity.getPlaca())) {
            throw new RuntimeException("Já existe uma moto com a placa informada");
        }
        // Se for edição, validar duplicidade de placa em outro registro
        if (entity.getId() != null) {
            motoRepository.findByPlaca(entity.getPlaca())
                    .filter(existing -> !existing.getId().equals(entity.getId()))
                    .ifPresent(m -> { throw new RuntimeException("Já existe outra moto com essa placa"); });
        }
        Moto saved = motoRepository.save(entity);
        return toDTO(saved);
    }

    public void delete(Long id) {
        motoRepository.deleteById(id);
    }

    /* ---------- Mapping ---------- */

    private MotoDTO toDTO(Moto m) {
        return MotoDTO.builder()
                .id(m.getId())
                .modelo(m.getModelo())
                .placa(m.getPlaca())
                .dataEntrada(m.getDataEntrada())
                .dataSaida(m.getDataSaida())
                .build();
    }

    private Moto toEntity(MotoDTO dto) {
        return Moto.builder()
                .id(dto.getId())
                .modelo(dto.getModelo())
                .placa(dto.getPlaca())
                .dataEntrada(dto.getDataEntrada())
                .dataSaida(dto.getDataSaida())
                .build();
    }
}
