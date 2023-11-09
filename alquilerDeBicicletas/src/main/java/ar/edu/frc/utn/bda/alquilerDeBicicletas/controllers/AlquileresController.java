package ar.edu.frc.utn.bda.alquilerDeBicicletas.controllers;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Alquiler;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.AlquilerService;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.support.LocalDateTimeConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
public class AlquileresController {
    private AlquilerService alquilerService;

    public AlquileresController(AlquilerService alquilerService){
        this.alquilerService = alquilerService;
    }
    @GetMapping
    public ResponseEntity<List<Alquiler>> getAlquileresPeriodo(@RequestParam String desde, @RequestParam String hasta){
        try {
            LocalDateTimeConverter converter = new LocalDateTimeConverter();
            LocalDateTime desdeT = converter.convertToEntityAttribute(Timestamp.valueOf(desde));
            LocalDateTime hastaT = converter.convertToEntityAttribute(Timestamp.valueOf(hasta));
            List<Alquiler> values = this.alquilerService.findAllEnPerido(desdeT, hastaT);
            return ResponseEntity.ok(values);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }

    // TODO: DEBERIA CHEQUEAR QUE EXISTA LA ESTACION y chequear lo del idCliente
    @PostMapping("{id}")
    public ResponseEntity<Alquiler> add(@PathVariable("id") String idCliente, @RequestBody Integer estacionRetiroId){
        try {
            Alquiler aGuardar = new Alquiler(idCliente, estacionRetiroId);
            Alquiler value = this.alquilerService.add(aGuardar);
            return ResponseEntity.ok(value);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }


    @PutMapping("{id}")
    public ResponseEntity<Alquiler> finalizar(@PathVariable("id") String id, @RequestParam Integer estacionId){
        try {
            Alquiler value = this.alquilerService.finalizar(id, estacionId);
            return ResponseEntity.ok(value);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
