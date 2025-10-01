package com.techlab.iot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IotRepository extends JpaRepository<Iot, Long> {
}
