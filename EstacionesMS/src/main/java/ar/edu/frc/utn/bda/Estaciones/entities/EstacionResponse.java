package ar.edu.frc.utn.bda.Estaciones.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstacionResponse {
    private String nombre;
    private double latitud;
    private double longitud;

    public static EstacionResponse fromEstacion(Estacion estacion){
        return EstacionResponse.builder()
                .nombre(estacion.getNombre())
                .latitud(estacion.getLatitud())
                .longitud(estacion.getLongitud())
                .build();
    }
}
