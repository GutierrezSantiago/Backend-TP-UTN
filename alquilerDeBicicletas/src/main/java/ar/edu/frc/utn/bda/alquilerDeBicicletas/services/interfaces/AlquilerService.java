package ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Alquiler;

import java.time.LocalDateTime;
import java.util.List;

public interface AlquilerService extends Service<Alquiler, Integer>{
    public List<Alquiler> findAllEnPerido(LocalDateTime desde, LocalDateTime hasta);
}
