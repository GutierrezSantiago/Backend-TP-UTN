package ar.edu.frc.utn.bda.alquilerDeBicicletas.controllers;


import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Estacion;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.requests.EstacionCreateRequest;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.EstacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/estacion")
public class EstacionController {
    private EstacionService estacionService;
    public EstacionController(EstacionService estacionService){
        this.estacionService = estacionService;
    }
    @GetMapping
    public ResponseEntity<List<Estacion>> getAll(){
        List<Estacion> values = this.estacionService.findAll();
        return ResponseEntity.ok(values);
    }
    @PostMapping
    public ResponseEntity<Estacion> add(EstacionCreateRequest aRequest){
        try {
            Estacion estacion = aRequest.toEstacion();
            estacion.setFechaHoraCreacion(LocalDateTime.now().toLocalDate());
            Estacion value = this.estacionService.add(estacion);
            return ResponseEntity.ok(value);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
}
