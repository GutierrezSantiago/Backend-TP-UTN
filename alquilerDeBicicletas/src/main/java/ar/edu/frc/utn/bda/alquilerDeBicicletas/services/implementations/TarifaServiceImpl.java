package ar.edu.frc.utn.bda.alquilerDeBicicletas.services.implementations;

import ar.edu.frc.utn.bda.alquilerDeBicicletas.entities.Tarifa;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.repositories.TarifaRepository;
import ar.edu.frc.utn.bda.alquilerDeBicicletas.services.interfaces.TarifaService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TarifaServiceImpl implements TarifaService {
    private final TarifaRepository tarifaRepository;
    public TarifaServiceImpl(TarifaRepository tarifaRepository){
        this.tarifaRepository = tarifaRepository;
    }
    @Override
    public Tarifa add(Tarifa entity) { return this.tarifaRepository.save(entity); }

    @Override
    public Tarifa update(Tarifa entity) { return this.tarifaRepository.save(entity); }

    @Override
    public Tarifa delete(Integer id) {
        Tarifa tarifa = this.tarifaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encontro la tarifa"));
        this.tarifaRepository.delete(tarifa);
        return tarifa;
    }

    @Override
    public Tarifa findById(Integer id) {
        return this.tarifaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encontro la tarifa"));
    }

    @Override
    public List<Tarifa> findAll() { return this.tarifaRepository.findAll(); }
}
