package ar.edu.frc.utn.bda.Estaciones.repositories;

import ar.edu.frc.utn.bda.Estaciones.entities.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, Integer> {
    @Query("SELECT e from Estacion e ORDER BY SQRT((e.latitud - ?1) * (e.latitud - ?1) + (e.longitud - ?2) * (e.longitud - ?2)) ASC LIMIT 1")
    public Estacion findByClosestTo(double lat, double lon);
}
