package ar.edu.frc.utn.bda.Estaciones.repositories;

import ar.edu.frc.utn.bda.Estaciones.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
}
