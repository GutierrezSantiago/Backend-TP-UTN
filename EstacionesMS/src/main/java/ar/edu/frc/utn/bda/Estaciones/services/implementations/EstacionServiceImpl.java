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
    public Estacion add(Estacion entity) {
        return this.estacionRepository.save(entity);
    }

    @Override
    public Estacion update(Estacion entity) {
        if (!this.estacionRepository.existsById(entity.getId())) throw new IllegalArgumentException("No existe la estación a agregar.");
        return this.estacionRepository.save(entity);
    }

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
    public List<Estacion> findAll() {
        List<Estacion> estaciones = this.estacionRepository.findAll();
        if(estaciones.isEmpty()){throw new IllegalArgumentException("No categories found");}
        return estaciones;
    }

    @Override
    public Estacion findByClosestTo(double latitud, double longitud) {
        Estacion estacion = this.estacionRepository.findByClosestTo(latitud, longitud);
        if(estacion == null){
            throw new IllegalArgumentException("No se encontro la estacion");
        }
        return estacion;
    }

    @Override
    public double calcularDistancia(Integer idEstacionRetiro, Integer idEstacionDevolucion) {
        Estacion estacionDevolucion = this.estacionRepository.findById(idEstacionDevolucion).orElseThrow(() -> new IllegalArgumentException("No se encontro la estacionRetiro"));
        Estacion estacionRetiro = this.estacionRepository.findById(idEstacionRetiro).orElseThrow(() -> new IllegalArgumentException("No se encontro la estacionDevolucion"));
        double distancia = estacionDevolucion.calcularDistancia(estacionRetiro.getLatitud(), estacionRetiro.getLongitud());
        return distancia;
    }


}
