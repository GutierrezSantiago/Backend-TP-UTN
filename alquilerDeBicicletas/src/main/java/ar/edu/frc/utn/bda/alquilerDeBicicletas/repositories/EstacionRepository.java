package ar.edu.frc.utn.bda.alquilerDeBicicletas.repositories;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionRepository  extends JpaRepository<Estacion, Long> {
}
