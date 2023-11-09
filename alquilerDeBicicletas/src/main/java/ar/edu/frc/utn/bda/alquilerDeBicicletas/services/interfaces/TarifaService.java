package ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public interface TarifaService extends Service<Tarifa, Integer> {
    public Tarifa getTarifaActual(LocalDateTime fechaHora);
}
