package ar.edu.frc.utn.bda.alquilerDeBicicletas.controllers;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Alquiler;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.implementations.AlquilerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
public class AlquileresController {
    private AlquilerServiceImpl alquilerService;

    public AlquileresController(AlquilerServiceImpl alquilerService){
        this.alquilerService = alquilerService;
    }
    // TODO: Implementar
    @GetMapping
    public ResponseEntity<List<Alquiler>> getAlquileresPeriodo(LocalDateTime desde, LocalDateTime hasta){
        List<Alquiler> values = this.alquilerService.findAll();
        return ResponseEntity.ok(values);
    }

}
