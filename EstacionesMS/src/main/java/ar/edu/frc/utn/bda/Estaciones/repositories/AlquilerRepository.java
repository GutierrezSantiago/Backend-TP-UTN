package ar.edu.frc.utn.bda.Estaciones.repositories;

import ar.edu.frc.utn.bda.Estaciones.entities.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {
}
