package com.techlab.patio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatioService {

    private final PatioRepository patioRepository;

    public List<PatioDTO> findAll() {
        return patioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PatioDTO findById(Long id) {
        return patioRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
    }

    public PatioDTO save(PatioDTO dto) {
        Patio entity = toEntity(dto);

        // Criar: validar nome único
        if (entity.getId() == null && patioRepository.existsByNome(entity.getNome())) {
            throw new RuntimeException("Já existe um pátio com esse nome");
        }
        // Editar: validar duplicidade de nome em outro registro
        if (entity.getId() != null) {
            patioRepository.findByNome(entity.getNome())
                    .filter(existing -> !existing.getId().equals(entity.getId()))
                    .ifPresent(p -> { throw new RuntimeException("Já existe outro pátio com esse nome"); });
        }

        Patio saved = patioRepository.save(entity);
        return toDTO(saved);
    }

    public void delete(Long id) {
        patioRepository.deleteById(id);
    }

    /* ---------- Mapping ---------- */

    private PatioDTO toDTO(Patio p) {
        return PatioDTO.builder()
                .id(p.getId())
                .nome(p.getNome())
                .build();
    }

    private Patio toEntity(PatioDTO dto) {
        return Patio.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .build();
    }
}
