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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alquiler")
public class AlquileresController {
    private AlquilerService alquilerService;
    private MonedaService monedaService;

    public AlquileresController(AlquilerService alquilerService, MonedaService monedaService){
        this.alquilerService = alquilerService;
        this.monedaService = monedaService;
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

    @PostMapping("{id}")
    public ResponseEntity<Alquiler> add(@PathVariable("id") String idCliente, @RequestBody Integer estacionRetiroId){
        try {
            if (this.alquilerService.findActivoByIdCliente(idCliente)!=null||!this.existeEstacion(estacionRetiroId)) return ResponseEntity.badRequest().build();
            Alquiler aGuardar = new Alquiler(idCliente, estacionRetiroId);
            Alquiler value = this.alquilerService.add(aGuardar);
            return ResponseEntity.ok(value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }


    @PutMapping( "{id}")
    public ResponseEntity<AlquilerFinResponse> finalizar(@PathVariable("id") String idCliente, @RequestParam Integer estacionId,
                                                         @RequestParam(required = false, defaultValue = "ARS") String moneda){
        try {
            Alquiler alquiler = this.alquilerService.findActivoByIdCliente(idCliente);
            if(alquiler == null) return ResponseEntity.badRequest().build();
            if (!this.existeEstacion(estacionId)||alquiler.getEstacionRetiroId() == estacionId){
                return ResponseEntity.badRequest().build();
            }
            double distancia = this.calcularDistancia(alquiler.getEstacionRetiroId(), estacionId);
            Alquiler value = this.alquilerService.finalizar(alquiler, estacionId, distancia);
            Double montoConvertido = this.monedaService.convertirMoneda(moneda, value.getMonto());
            AlquilerFinResponse response = AlquilerFinResponse.fromAlquiler(value, montoConvertido, moneda);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("HTTP: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    public boolean existeEstacion(Integer idEstacion) {
        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<Object> res = template.getForEntity(
                    "http://localhost:8082/api/estacion/{id}", Object.class, idEstacion
            );

            // Se comprueba si el código de repuesta es de la familia 200
            return res.getStatusCode().is2xxSuccessful();

        } catch (HttpServerErrorException ex) {
            // Handle HTTP 5xx errors
            System.out.println("HTTP Server Error: " + ex.getMessage());
            throw new IllegalArgumentException("No se pudo realizar la petición al servicio Estacion", ex);
            //throw new IllegalArgumentException("No se pudo realizar la petición al servicio Estacion");
        }
    }

    public Double calcularDistancia(Integer idEstacionRetiro, Integer idEstacionDevolucion){
        try {
            RestTemplate template = new RestTemplate();
            Double distancia = template.getForObject(
                    "http://localhost:8082/api/estacion/distancia/{idEstacionRetiro}&{idEstacionDevolucion}", Double.class, idEstacionRetiro, idEstacionDevolucion
            );
            System.out.println(template);
            System.out.println(distancia);
            return distancia;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("No se pudo realizar la petición al servicio Estacion");
        }
    }

}
