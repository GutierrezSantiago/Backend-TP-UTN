package ar.edu.frc.utn.bda.alquilerDeBicicletas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "ESTACIONES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="NOMBRE")
    private String nombre;

    @Column(name="FECHA_HORA_CREACION")
    private Date fechaHoraCreacion;

    @Column(name="LATITUD")
    private double latitud;

    @Column(name="LONGITUD")
    private double longitud;

}
