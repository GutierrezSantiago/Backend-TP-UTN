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
            ResponseEntity<Object> res = template.getForEntity(
                    "http://localhost:8082/api/estacion/{id}", Object.class, idEstacion
            );

            // Se comprueba si el código de repuesta es de la familia 200
            return res.getStatusCode().is2xxSuccessful();

        } catch (HttpServerErrorException ex) {
            // Handle HTTP 5xx errors
            System.out.println("HTTP Server Error: " + ex.getMessage());
            throw new IllegalArgumentException("No se pudo realizar la petición al servicio Estacion", ex);
            //throw new IllegalArgumentException("No se pudo realizar la petición al servicio Estacion");
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
            ex.printStackTrace();
            throw new IllegalArgumentException("No se pudo realizar la petición al servicio Estacion");
        }
    }
}
