package citasmedicas.service;

import citasmedicas.model.dto.AreaDTO;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class)
public interface AreaService {
    AreaDTO guardar(AreaDTO areaDTO);

    List<AreaDTO> listar();

    AreaDTO actualizar(AreaDTO areaDTO, Integer id);

    Optional<AreaDTO> obtenerPorId(Integer id);

    Optional<AreaDTO> obtenerPorNombre(String nombre);
}
