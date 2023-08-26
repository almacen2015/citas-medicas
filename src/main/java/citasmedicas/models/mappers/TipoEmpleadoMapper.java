package citasmedicas.models.mappers;

import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.models.entities.TipoEmpleado;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoEmpleadoMapper {
    TipoEmpleadoMapper INSTANCE = Mappers.getMapper(TipoEmpleadoMapper.class);

    TipoEmpleadoDTO tipoEmpleadoToTipoEmpleadoDTO(TipoEmpleado tipoEmpleado);

    TipoEmpleado tipoEmpleadoDTOToTipoEmpleado(TipoEmpleadoDTO tipoEmpleadoDTO);

    List<TipoEmpleadoDTO> tipoEmpleadosToTipoEmpleadosDTO(List<TipoEmpleado> tipoEmpleados);

    List<TipoEmpleado> tipoEmpleadosDTOToTipoEmpleados(List<TipoEmpleadoDTO> tipoEmpleadosDTO);
}
