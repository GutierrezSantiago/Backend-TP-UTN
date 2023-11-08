package ar.edu.frc.utn.bda.alquilerDeBicicletas.entities;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.support.LocalDateTimeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "ESTACIONES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estacion {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ESTACIONES")
    @TableGenerator(name = "ESTACIONES", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue="ESTACIONES",
            initialValue=1, allocationSize=1)
    private Integer id;

    @Column(name="NOMBRE")
    private String nombre;

    @Column(name="FECHA_HORA_CREACION")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDate fechaHoraCreacion;

    @Column(name="LATITUD")
    private double latitud;

    @Column(name="LONGITUD")
    private double longitud;

}
