package ar.edu.frc.utn.bda.alquilerDeBicicletas.services.implementations;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Alquiler;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Tarifa;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.repositories.AlquilerRepository;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.AlquilerService;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.TarifaService;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

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
    public Alquiler finalizar(Alquiler alquiler, Integer estacionId, Double distancia) {
        alquiler.finalizar(estacionId, distancia);
        return this.alquilerRepository.save(alquiler);
    }

    @Override
    public Alquiler findActivoByIdCliente(String idCliente) {
        Alquiler alquiler =this.alquilerRepository.findActivoByIdCliente(idCliente);
        return alquiler;
    }
}
