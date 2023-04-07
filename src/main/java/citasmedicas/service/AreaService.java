package citasmedicas.service;

import citasmedicas.model.dto.AreaDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional(rollbackOn = Exception.class)
public interface AreaService {
    AreaDTO guardar(AreaDTO areaDTO);

    List<AreaDTO> listar();

    AreaDTO actualizar(AreaDTO areaDTO, Integer id);

    Optional<AreaDTO> obtenerPorId(Integer id);

    void eliminar(Integer id);
}
