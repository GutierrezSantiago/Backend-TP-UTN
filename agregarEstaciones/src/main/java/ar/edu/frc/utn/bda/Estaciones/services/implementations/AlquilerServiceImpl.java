package ar.edu.frc.utn.bda.Estaciones.services.implementations;

import ar.edu.frc.utn.bda.Estaciones.entities.Alquiler;
import ar.edu.frc.utn.bda.Estaciones.repositories.AlquilerRepository;
import ar.edu.frc.utn.bda.Estaciones.services.interfaces.AlquilerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlquilerServiceImpl implements AlquilerService {
    private final AlquilerRepository alquilerRepository;
    public AlquilerServiceImpl(AlquilerRepository alquilerRepository){
        this.alquilerRepository = alquilerRepository;
    }
    @Override
    public Alquiler add(Alquiler entity) {
        return null;
    }

    @Override
    public Alquiler update(Alquiler entity) {
        return null;
    }

    @Override
    public Alquiler delete(Integer id) {
        Alquiler alquiler = this.alquilerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encontro el alquiler"));
        this.alquilerRepository.delete(alquiler);
        return alquiler;
    }

    @Override
    public Alquiler findById(Integer id) { return this.alquilerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encontro el alquiler")); }

    @Override
    public List<Alquiler> findAll() { return this.alquilerRepository.findAll(); }
}
