package ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.response;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Alquiler;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@Data
public class AlquilerResponse {
    private String idCliente;
    private Integer estado;
    private Integer estacionRetiroId;
    private String fechaHoraRetiro;

    public static AlquilerResponse fromAlquiler(Alquiler alquiler){
        return AlquilerResponse.builder()
                .idCliente(alquiler.getIdCliente())
                .estacionRetiroId(alquiler.getEstacionRetiroId())
                .fechaHoraRetiro(alquiler.getFechaHoraRetiro().toString())
                .estado(alquiler.getEstado())
                .build();
    }
}
