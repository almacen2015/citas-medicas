package citasmedicas.services;

import citasmedicas.models.dto.TipoEmpleadoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class)
public interface TipoEmpleadoService {
    List<TipoEmpleadoDTO> listar();

    TipoEmpleadoDTO guardar(TipoEmpleadoDTO tipoEmpleadoDTO);

    Optional<TipoEmpleadoDTO> buscarPorId(Integer id);

    Optional<TipoEmpleadoDTO> buscarPorNombre(String nombre);

    TipoEmpleadoDTO actualizar(TipoEmpleadoDTO tipoEmpleadoDTO);

    void eliminar(Integer id);
}
