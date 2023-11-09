package ar.edu.frc.utn.bda.alquilerDeBicicletas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TARIFAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TARIFAS")
    @TableGenerator(name = "TARIFAS", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue="TARIFAS",
            initialValue=1, allocationSize=1)
    private Integer id;

    @Column(name = "TIPO_TARIFA")
    private Integer tipoTarifa;

    @Column(name = "DEFINICION")
    private char definicion;

    @Column(name = "DIA_SEMANA")
    private Integer diaSemana;

    @Column(name = "DIA_MES")
    private Integer diaMes;

    @Column(name = "MES")
    private Integer mes;

    @Column(name = "ANIO")
    private Integer anio;

    @Column(name = "MONTO_FIJO_ALQUILER")
    private Double montoFijoAlquiler;

    @Column(name = "MONTO_MINUTO_FRACCION")
    private Double montoMinutoFraccion;

    @Column(name = "MONTO_KM")
    private Double montoKm;

    @Column(name = "MONTO_HORA")
    private Double montoHora;


    public double calcularMonto(LocalDateTime fechaHoraRetiro, LocalDateTime fechaHoraDevolucion, double distanciaRecorrida){
       double monto = this.montoFijoAlquiler;
        double diferenciaTiempoMinutos = fechaHoraRetiro.until(fechaHoraDevolucion, java.time.temporal.ChronoUnit.MINUTES);
       if(diferenciaTiempoMinutos < 31){
           monto += diferenciaTiempoMinutos * this.montoMinutoFraccion;
       }
       else {
           long diferenciaTiempoHoras = Math.round(diferenciaTiempoMinutos / 60);
           monto += diferenciaTiempoHoras * this.montoHora;
       }
       return monto + distanciaRecorrida * this.montoKm;
    }




}
