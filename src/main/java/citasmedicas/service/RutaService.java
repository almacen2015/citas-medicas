package citasmedicas.service;

import citasmedicas.model.Ruta;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
public interface RutaService {
    List<Ruta> obtenerRutas();

    Ruta guardar(Ruta ruta);
}
