package com.techlab.iot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IotService {

    private final IotRepository iotRepository;

    public List<Iot> findAll() {
        return iotRepository.findAll();
    }

    public Iot findById(Long id) {
        return iotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("IoT não encontrado"));
    }

    public Iot save(Iot iot) {
        return iotRepository.save(iot);
    }

    public void delete(Long id) {
        iotRepository.deleteById(id);
    }
}
