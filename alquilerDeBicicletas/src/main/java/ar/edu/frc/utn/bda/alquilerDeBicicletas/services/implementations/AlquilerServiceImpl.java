package ar.edu.frc.utn.bda.alquilerDeBicicletas.services.implementations;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Alquiler;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Tarifa;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.repositories.AlquilerRepository;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.AlquilerService;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.TarifaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlquilerServiceImpl implements AlquilerService {
    private final AlquilerRepository alquilerRepository;
    private final TarifaService tarifaService;
    public AlquilerServiceImpl(AlquilerRepository alquilerRepository, TarifaService tarifaService){
        this.alquilerRepository = alquilerRepository;
        this.tarifaService = tarifaService;
    }
    @Override
    public Alquiler add(Alquiler entity) {
        Alquiler existe = this.alquilerRepository.findActivoByIdCliente(entity.getIdCliente());
        if(existe != null){
            throw new IllegalArgumentException("Ya existe un alquiler activo para el cliente");
        }
        Tarifa tarifa = this.tarifaService.getTarifaActual(entity.getFechaHoraRetiro());
        entity.setTarifa(tarifa);
        return this.alquilerRepository.save(entity);
    }

    @Override
    public Alquiler update(Alquiler entity) {
        return this.alquilerRepository.save(entity);
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

    @Override
    public List<Alquiler> findAllEnPerido(LocalDateTime desde, LocalDateTime hasta) {
        List<Alquiler> alquileres = this.alquilerRepository.findAllEnPerido(desde, hasta);
        if(alquileres.isEmpty()) throw new IllegalArgumentException("No se encontraron alquileres en el periodo");
        return alquileres;
    }

    // TODO: CONECTAR SERVICIOS DE ESTACIONES
    @Override
    public Alquiler finalizar(String id, Integer estacionId) {
        Alquiler alquiler = this.alquilerRepository.findActivoByIdCliente(id);
        if(alquiler == null) throw new IllegalArgumentException("No se encontro el alquiler activo");
        if(alquiler.getEstacionRetiroId() == estacionId) throw new IllegalArgumentException("La estacion de devolucion no puede ser la misma que la de retiro");
        //double distancia = alquiler.getEstacionRetiro().getDistancia(alquiler.getEstacionDevolucion());
        double distancia = 40;
        alquiler.finalizar(estacionId, distancia);
        return this.alquilerRepository.save(alquiler);
    }
}
