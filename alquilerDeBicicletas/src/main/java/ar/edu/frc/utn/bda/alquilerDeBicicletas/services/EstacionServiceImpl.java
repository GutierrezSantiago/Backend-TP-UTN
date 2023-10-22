package ar.edu.frc.utn.bda.alquilerDeBicicletas.services;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Estacion;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.repositories.EstacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacionServiceImpl implements EstacionService{
    private EstacionRepository estacionRepository;
    public EstacionServiceImpl(EstacionRepository estacionRepository){
        this.estacionRepository = estacionRepository;
    }

    @Override
    public void add(Estacion entity) {
        this.estacionRepository.save(entity);
    }

    @Override
    public void update(Estacion entity) {
        this.estacionRepository.save(entity);
    }

    @Override
    public Estacion delete(Long id) {
        Estacion estacion = this.getById(id);
        if (estacion != null) {
            this.estacionRepository.delete(estacion);
        }
        return estacion;
    }

    @Override
    public Estacion getById(Long id) {
        return this.estacionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Estacion> getAll() {
        return this.estacionRepository.findAll();
    }
}
