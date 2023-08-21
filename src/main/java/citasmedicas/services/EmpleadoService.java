package citasmedicas.services;

import citasmedicas.models.dto.EmpleadoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
public interface EmpleadoService {
    EmpleadoDTO guardar(EmpleadoDTO empleadoDTO);

    EmpleadoDTO buscarPorId(Integer id);

    EmpleadoDTO buscarPorNumeroDocumento(String numeroDocumento);

    EmpleadoDTO actualizar(EmpleadoDTO empleadoDTO);

    List<EmpleadoDTO> listar();

    void eliminar(Integer id);
}
