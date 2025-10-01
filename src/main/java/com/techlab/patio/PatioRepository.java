package com.techlab.patio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatioRepository extends JpaRepository<Patio, Long> {
    Optional<Patio> findByNome(String nome);
    boolean existsByNome(String nome);
}
