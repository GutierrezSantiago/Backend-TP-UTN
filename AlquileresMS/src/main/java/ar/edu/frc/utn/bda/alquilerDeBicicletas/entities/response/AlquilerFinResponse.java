package ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.response;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Alquiler;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.support.LocalDateTimeConverter;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Builder
@ToString
@Data
public class AlquilerFinResponse {
    private String idCliente;
    private Integer estacionRetiroId;
    private Integer estacionDevolucionId;
    private String fechaHoraRetiro;
    private String fechaHoraDevolucion;
    private Double monto;
    private String moneda;



    public static AlquilerFinResponse fromAlquiler(Alquiler alquiler, Double montoConvertido, String moneda){
        LocalDateTimeConverter converter = new LocalDateTimeConverter();
        String rDate = converter.convertToJsonAttribute(alquiler.getFechaHoraRetiro());
        String dDate = converter.convertToJsonAttribute(alquiler.getFechaHoraDevolucion());

        return AlquilerFinResponse.builder()
                .idCliente(alquiler.getIdCliente())
                .estacionRetiroId(alquiler.getEstacionRetiroId())
                .estacionDevolucionId(alquiler.getEstacionDevolucionId())
                .fechaHoraRetiro(rDate)
                .fechaHoraDevolucion(dDate)
                .monto(montoConvertido)
                .moneda(moneda)
                .build();
    }


}
