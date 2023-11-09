package ar.edu.frc.utn.bda.alquilerDeBicicletas.entities;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.support.LocalDateTimeConverter;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALQUILERES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alquiler {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ALQUILERES")
    @TableGenerator(name = "ALQUILERES", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue="ALQUILERES",
            initialValue=1, allocationSize=1)
    private Integer id;

    @Column(name = "ID_CLIENTE")
    private String idCliente;

    @Column(name = "ESTADO")
    private Integer estado;

    @Column(name = "FECHA_HORA_RETIRO")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime fechaHoraRetiro;

    @Column(name = "FECHA_HORA_DEVOLUCION")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime fechaHoraDevolucion;

    @Column(name = "MONTO")
    private Double monto;

    @Column(name = "ESTACION_RETIRO")
    private Integer estacionRetiroId;

    @Column(name = "ESTACION_DEVOLUCION")
    private Integer estacionDevolucionId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_TARIFA", referencedColumnName = "ID")
    private Tarifa tarifa;

    public Alquiler(
            String idCliente,
            Integer estacionRetiroId
    ){
        this.id = null;
        this.idCliente = idCliente;
        this.estado = 1;
        this.fechaHoraRetiro = LocalDateTime.now();
        this.fechaHoraDevolucion = null;
        this.monto = null;
        this.estacionRetiroId = estacionRetiroId;
    }

    public void finalizar(Integer estacionDevolucionId, double distanciaRecorrida){
        this.estado = 2;
        this.fechaHoraDevolucion = LocalDateTime.now();
        this.estacionDevolucionId = estacionDevolucionId;
        this.monto = this.tarifa.calcularMonto(this.fechaHoraRetiro, this.fechaHoraDevolucion, distanciaRecorrida);
    }

}
