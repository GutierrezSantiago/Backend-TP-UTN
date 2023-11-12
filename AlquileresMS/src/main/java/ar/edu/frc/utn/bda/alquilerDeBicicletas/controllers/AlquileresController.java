package ar.edu.frc.utn.bda.alquilerDeBicicletas.controllers;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Alquiler;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.response.AlquilerResponse;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.response.AlquilerFinResponse;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.AlquilerService;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.EstacionService;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.MonedaService;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.support.LocalDateTimeConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alquiler")
public class AlquileresController {
    private AlquilerService alquilerService;
    private MonedaService monedaService;
    private EstacionService estacionService;

    public AlquileresController(AlquilerService alquilerService, MonedaService monedaService, EstacionService estacionService){
        this.alquilerService = alquilerService;
        
        this.monedaService = monedaService;
        this.estacionService = estacionService;
    }
    @GetMapping
    public ResponseEntity<List<AlquilerResponse>> getAlquileresPeriodo(@RequestParam String desde, @RequestParam String hasta){
        try {
            LocalDateTimeConverter converter = new LocalDateTimeConverter();
            LocalDateTime desdeT = converter.convertToEntityAttribute(Timestamp.valueOf(desde));
            LocalDateTime hastaT = converter.convertToEntityAttribute(Timestamp.valueOf(hasta));
            List<Alquiler> values = this.alquilerService.findAllEnPerido(desdeT, hastaT);
            List<AlquilerResponse> response = values.stream().map(AlquilerResponse::fromAlquiler).toList();
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("{id}")
    public ResponseEntity<AlquilerResponse> add(@PathVariable("id") String idCliente, @RequestParam Integer estacionRetiroId){
        try {
            if (this.alquilerService.findActivoByIdCliente(idCliente)!=null||!this.estacionService.existeEstacion(estacionRetiroId)) throw new IllegalArgumentException("Datos no encontrados.");
            Alquiler aGuardar = new Alquiler(idCliente, estacionRetiroId);
            Alquiler value = this.alquilerService.add(aGuardar);
            return ResponseEntity.ok(AlquilerResponse.fromAlquiler(value));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @PutMapping( "{id}")
    public ResponseEntity<AlquilerFinResponse> finalizar(@PathVariable("id") String idCliente, @RequestParam Integer estacionId,
                                                         @RequestParam(required = false, defaultValue = "ARS") String moneda){
        try {
            Alquiler alquiler = this.alquilerService.findActivoByIdCliente(idCliente);
            if(alquiler == null) return ResponseEntity.badRequest().build();
            if (!this.estacionService.existeEstacion(estacionId)||alquiler.getEstacionRetiroId() == estacionId){
                return ResponseEntity.badRequest().build();
            }
            double distancia = this.estacionService.calcularDistancia(alquiler.getEstacionRetiroId(), estacionId);
            Alquiler value = this.alquilerService.finalizar(alquiler, estacionId, distancia);
            Double montoConvertido = this.monedaService.convertirMoneda(moneda, value.getMonto());
            if (montoConvertido==-1) {
                moneda = "ARS";
                montoConvertido = value.getMonto();
            }
            AlquilerFinResponse response = AlquilerFinResponse.fromAlquiler(value, montoConvertido, moneda);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
