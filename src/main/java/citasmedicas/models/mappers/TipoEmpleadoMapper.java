package citasmedicas.models.mappers;

import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.models.entities.TipoEmpleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TipoEmpleadoMapper {
    TipoEmpleadoMapper INSTANCE = Mappers.getMapper(TipoEmpleadoMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "estado", target = "estado")
    TipoEmpleadoDTO tipoEmpleadoToTipoEmpleadoDTO(TipoEmpleado tipoEmpleado);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "estado", target = "estado")
    TipoEmpleado tipoEmpleadoDTOToTipoEmpleado(TipoEmpleadoDTO tipoEmpleadoDTO);
}
