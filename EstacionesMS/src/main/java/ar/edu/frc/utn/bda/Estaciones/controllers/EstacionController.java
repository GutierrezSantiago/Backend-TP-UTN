package ar.edu.frc.utn.bda.Estaciones.controllers;


import ar.edu.frc.utn.bda.Estaciones.entities.Estacion;
import ar.edu.frc.utn.bda.Estaciones.entities.response.EstacionResponse;
import ar.edu.frc.utn.bda.Estaciones.entities.requests.EstacionCreateRequest;
import ar.edu.frc.utn.bda.Estaciones.services.interfaces.EstacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<EstacionResponse>> getAll(){
        try {
            List<Estacion> values = this.estacionService.findAll();
            List<EstacionResponse> response = values.stream().map(EstacionResponse::fromEstacion).toList();
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
    @PostMapping
    public ResponseEntity<EstacionResponse> add(EstacionCreateRequest aRequest){
        try {
            Estacion estacion = aRequest.toEstacion();
            estacion.setFechaHoraCreacion(LocalDateTime.now());
            Estacion value = this.estacionService.add(estacion);
            EstacionResponse response = EstacionResponse.fromEstacion(value);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
    @GetMapping("/ubicacion/{latitud}&{longitud}")
    public ResponseEntity<EstacionResponse> getEstacionByLatitudAndLongitud(@PathVariable("latitud")Double latitud, @PathVariable("longitud")Double longitud){
        try {
            Estacion value = this.estacionService.findByClosestTo(latitud, longitud);
            EstacionResponse response = EstacionResponse.fromEstacion(value);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionResponse> getById(@PathVariable("id") Integer estacionId){
        try {
            Estacion estacion = this.estacionService.findById(estacionId);
            EstacionResponse response = EstacionResponse.fromEstacion(estacion);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/distancia/{idEstacionRetiro}&{idEstacionDevolucion}")
    public ResponseEntity<Double> calcularDistanciaEntreEstaciones(@PathVariable("idEstacionRetiro")Integer idEstacionRetiro, @PathVariable("idEstacionDevolucion")Integer idEstacionDevolucion){
        try {
            Double value = this.estacionService.calcularDistancia(idEstacionRetiro, idEstacionDevolucion);
            return ResponseEntity.ok(value);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
