package ar.edu.frc.utn.bda.alquilerDeBicicletas.services.implementations;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.requests.MonedaRequest;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.response.MonedaResponse;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.MonedaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class MonedaServiceImpl implements MonedaService {

    private final String URL_CONVERTIR = "http://34.82.105.125:8080/convertir";


    @Override
    public double convertirMoneda(String monedaDestino, double importe) {
        RestTemplate template = new RestTemplate();

        try {
            if(monedaDestino.equals("ARS")) return importe;
            ResponseEntity<MonedaResponse> response = template.postForEntity(URL_CONVERTIR, new MonedaRequest(monedaDestino, importe), MonedaResponse.class);
            return response.getBody().getImporte();
        } catch (HttpServerErrorException e) {
            return -1;
        } catch (Exception e){
            return -1;
        }

    }
}

