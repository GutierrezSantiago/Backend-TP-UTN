package ar.edu.frc.utn.bda.Estaciones.entities.requests;

import ar.edu.frc.utn.bda.Estaciones.entities.Estacion;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EstacionCreateRequest {
    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "Latitud es obligatoria")
    private double latitud;
    @NotBlank(message = "Longitud es obligatoria")
    private double longitud;

    public Estacion toEstacion() {
        return new Estacion(
                null,
                nombre,
                null,
                latitud,
                longitud
        );
    }
}
