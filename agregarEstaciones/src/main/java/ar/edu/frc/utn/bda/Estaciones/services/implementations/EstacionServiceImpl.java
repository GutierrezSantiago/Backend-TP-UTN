package ar.edu.frc.utn.bda.Estaciones.services.implementations;

import ar.edu.frc.utn.bda.Estaciones.entities.Estacion;
import ar.edu.frc.utn.bda.Estaciones.repositories.EstacionRepository;
import ar.edu.frc.utn.bda.Estaciones.services.interfaces.EstacionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacionServiceImpl implements EstacionService {
    private final EstacionRepository estacionRepository;
    public EstacionServiceImpl(EstacionRepository estacionRepository){
        this.estacionRepository = estacionRepository;
    }

    @Override
    public Estacion add(Estacion entity) { return this.estacionRepository.save(entity); }

    @Override
    public Estacion update(Estacion entity) { return this.estacionRepository.save(entity); }

    @Override
    public Estacion delete(Integer id) {
        Estacion estacion = this.estacionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encontro la estacion"));
        this.estacionRepository.delete(estacion);
        return estacion;
    }

    @Override
    public Estacion findById(Integer id) {
        return this.estacionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encontro la estacion"));
    }

    @Override
    public List<Estacion> findAll() { return this.estacionRepository.findAll(); }

    @Override
    public Estacion findByClosestTo(double latitud, double longitud) {
        Estacion estacion = this.estacionRepository.findByClosestTo(latitud, longitud);
        if(estacion == null){
            throw new IllegalArgumentException("No se encontro la estacion");
        }
        return estacion;
    }
}
