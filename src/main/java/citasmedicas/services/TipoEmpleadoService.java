package citasmedicas.services;

import citasmedicas.models.dto.TipoEmpleadoDTO;

import java.util.List;

public interface TipoEmpleadoService {
    List<TipoEmpleadoDTO> listar();

    TipoEmpleadoDTO guardar(TipoEmpleadoDTO tipoEmpleadoDTO);

    TipoEmpleadoDTO buscarPorId(Integer id);

    TipoEmpleadoDTO buscarPorNombre(String nombre);

    TipoEmpleadoDTO actualizar(TipoEmpleadoDTO tipoEmpleadoDTO);

    void eliminar(Integer id);
}
