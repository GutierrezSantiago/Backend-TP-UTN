package ar.edu.frc.utn.bda.alquilerDeBicicletas.repositories;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {
    @Query("SELECT a FROM Alquiler a WHERE a.fechaHoraRetiro >= ?1 AND a.fechaHoraRetiro <= ?2 " +
            "AND a.fechaHoraDevolucion >= ?1 AND a.fechaHoraDevolucion <= ?2")
    List<Alquiler> findAllEnPerido(LocalDateTime desde, LocalDateTime hasta);
}
