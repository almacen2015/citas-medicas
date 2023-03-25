package citasmedicas.service.impl;

import citasmedicas.exceptions.RutaException;
import citasmedicas.model.Ruta;
import citasmedicas.repository.RutaRepository;
import citasmedicas.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutaServiceImpl implements RutaService {
    @Autowired
    private RutaRepository repository;

    @Override
    public List<Ruta> obtenerRutas() {
        return repository.findAll();
    }

    @Override
    public Ruta guardar(Ruta ruta) {
        validarDatos(ruta);
        return repository.save(ruta);
    }

    private void validarDatos(Ruta ruta) {
        if (ruta.getPath().equals("")) {
            throw new RutaException(RutaException.PATH_NO_VALIDO);
        }
        if (ruta.getComponent().equals("")) {
            throw new RutaException(RutaException.COMPONENT_NO_VALIDO);
        }
    }
}
