package ar.edu.frc.utn.bda.alquilerDeBicicletas.services.implementations;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.EstacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EstacionServiceImpl implements EstacionService {

    @Override
    public boolean existeEstacion(Integer idEstacion) {
        try {
            RestTemplate template = new RestTemplate();
            System.out.println("todo pelota");
            ResponseEntity<Object> res = template.getForEntity(
                    "http://localhost:8082/api/estacion/{id}", Object.class, idEstacion
            );
            return res.getStatusCode().is2xxSuccessful();

        } catch (HttpServerErrorException ex) {
            // Handle HTTP 5xx errors
            return false;
            //throw new IllegalArgumentException("No se pudo realizar la petición al servicio Estacion");
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Double calcularDistancia(Integer idEstacionRetiro, Integer idEstacionDevolucion) {
        try {
            RestTemplate template = new RestTemplate();
            Double distancia = template.getForObject(
                    "http://localhost:8082/api/estacion/distancia/{idEstacionRetiro}&{idEstacionDevolucion}", Double.class, idEstacionRetiro, idEstacionDevolucion
            );
            System.out.println(template);
            System.out.println(distancia);
            return distancia;

        } catch (Exception ex) {
            throw new IllegalArgumentException("No se pudo realizar la petición al servicio Estacion");
        }
    }
}
