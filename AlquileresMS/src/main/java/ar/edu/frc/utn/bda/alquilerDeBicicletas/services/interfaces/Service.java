package ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces;

import java.util.List;

public interface Service<T, ID> {

    T add(T entity);
    T update(T entity);
    T delete(ID id);
    T findById(ID id);
    List<T> findAll();
}
