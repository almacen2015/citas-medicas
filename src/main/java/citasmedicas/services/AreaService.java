package citasmedicas.services;

import citasmedicas.models.dto.AreaDTO;

import java.util.List;

public interface AreaService {
    AreaDTO guardar(AreaDTO areaDTO);

    List<AreaDTO> listar();

    AreaDTO actualizar(AreaDTO areaDTO, Integer id);

    AreaDTO obtenerPorId(Integer id);

    AreaDTO obtenerPorNombre(String nombre);
}
