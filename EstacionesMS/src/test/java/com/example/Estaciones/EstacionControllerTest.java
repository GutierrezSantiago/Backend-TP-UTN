package com.example.Estaciones;

import ar.edu.frc.utn.bda.Estaciones.controllers.EstacionController;
import ar.edu.frc.utn.bda.Estaciones.entities.Estacion;
import ar.edu.frc.utn.bda.Estaciones.entities.requests.EstacionCreateRequest;
import ar.edu.frc.utn.bda.Estaciones.repositories.EstacionRepository;
import ar.edu.frc.utn.bda.Estaciones.services.implementations.EstacionServiceImpl;
import ar.edu.frc.utn.bda.Estaciones.services.interfaces.EstacionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class EstacionControllerTest {
    private EstacionController estacionController;
    private EstacionRepository estacionRepository;
    private final Estacion ESTACION = new Estacion(1, "EstacionTest", LocalDateTime.now(), 40, 40);

    @BeforeEach
    public void setup(){
        estacionRepository = Mockito.mock(EstacionRepository.class);
        EstacionServiceImpl service = new EstacionServiceImpl(estacionRepository);
        estacionController = new EstacionController(service);
    }

    @Test
    void testGetAll(){
        List<Estacion> estacionList = new ArrayList<>();
        estacionList.add(ESTACION);
        Mockito.when(estacionRepository.findAll()).thenReturn(estacionList);
        Assertions.assertEquals(
                HttpStatus.OK,
                estacionController.getAll().getStatusCode()
        );
    }

    @Test
    void testGetAllEmpty(){
        Mockito.when(estacionRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertEquals(
                HttpStatus.NO_CONTENT,
                estacionController.getAll().getStatusCode()
        );
    }

    @Test
    void testGetById(){
        Mockito.when(estacionRepository.findById(any(Integer.class))).thenReturn(Optional.of(ESTACION));
        Assertions.assertEquals(
                HttpStatus.OK,
                estacionController.getById(1).getStatusCode()
        );
    }
    @Test
    void testGetByIdNotFound(){
        Mockito.when(estacionRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Assertions.assertEquals(
                HttpStatus.NOT_FOUND,
                estacionController.getById(1).getStatusCode()
        );
    }
    @Test
    void testAdd(){
        Mockito.when(estacionRepository.save(any(Estacion.class))).thenReturn(ESTACION);
        Mockito.when(estacionRepository.saveAndFlush(any(Estacion.class))).thenReturn(ESTACION);
        Assertions.assertEquals(
                HttpStatus.OK,
                estacionController.add(new EstacionCreateRequest("EstacionRC", 1, 1)).getStatusCode()
        );
    }

    @Test
    void testGetEstacionByLatitudAndLongitud(){
        Mockito.when(estacionRepository.findByClosestTo(1, 1)).thenReturn(ESTACION);
        Assertions.assertEquals(
                HttpStatus.OK,
                estacionController.getEstacionByLatitudAndLongitud(1.0, 1.0).getStatusCode()
        );
    }

    @Test
    void testCalcularDistanciaEntreEstaciones(){
        Mockito.when(estacionRepository.findById(1)).thenReturn(Optional.of(ESTACION));
        Mockito.when(estacionRepository.findById(1)).thenReturn(Optional.of(ESTACION));
        Assertions.assertEquals(
                HttpStatus.OK,
                estacionController.calcularDistanciaEntreEstaciones(1, 1).getStatusCode()
        );
    }

    @Test
    void testCalcularDistanciaEntreEstacionesEstacionNoExiste(){
        Mockito.when(estacionRepository.findById(2)).thenReturn(Optional.empty());
        Mockito.when(estacionRepository.findById(1)).thenReturn(Optional.of(ESTACION));
        Assertions.assertEquals(
                HttpStatus.BAD_REQUEST,
                estacionController.calcularDistanciaEntreEstaciones(1, 2).getStatusCode()
        );
    }




}
