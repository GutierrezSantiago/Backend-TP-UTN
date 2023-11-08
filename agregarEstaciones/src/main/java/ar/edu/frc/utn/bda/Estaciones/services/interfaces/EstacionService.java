package ar.edu.frc.utn.bda.Estaciones.services.interfaces;

import ar.edu.frc.utn.bda.Estaciones.entities.Estacion;


public interface EstacionService extends Service<Estacion, Integer> {
    public Estacion findByClosestTo(double latitud, double longitud);
}
