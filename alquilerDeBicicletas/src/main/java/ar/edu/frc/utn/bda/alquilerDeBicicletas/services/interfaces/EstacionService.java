package ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces;

public interface EstacionService {
    boolean existeEstacion(Integer idEstacion);
    Double calcularDistancia(Integer idEstacionRetiro, Integer idEstacionDevolucion);

}
