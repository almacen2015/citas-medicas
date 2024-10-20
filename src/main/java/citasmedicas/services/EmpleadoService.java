package citasmedicas.services;

import citasmedicas.models.dto.EmpleadoDTO;
import citasmedicas.repositories.filtros.FiltroEmpleado;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
public interface EmpleadoService {
    EmpleadoDTO guardar(EmpleadoDTO empleadoDTO);

    EmpleadoDTO buscarPorId(Integer id);

    EmpleadoDTO buscarPorNumeroDocumento(String numeroDocumento);

    List<EmpleadoDTO> listarPorEstado(boolean estado, Integer paginaInicio, Integer cantidadDatos);

    List<EmpleadoDTO> listarEmpleados(FiltroEmpleado filtro, Integer paginaInicio, Integer cantidadDatos);

    EmpleadoDTO actualizar(EmpleadoDTO empleadoDTO);

    List<EmpleadoDTO> listar();

    void eliminar(Integer id);
}
