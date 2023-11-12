package ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonedaRequest {
    private String moneda_destino;
    private double importe;
}
