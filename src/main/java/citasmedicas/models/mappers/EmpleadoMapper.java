package citasmedicas.models.mappers;

import citasmedicas.models.dto.EmpleadoDTO;
import citasmedicas.models.entities.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmpleadoMapper {

    EmpleadoMapper INSTANCE = Mappers.getMapper(EmpleadoMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "apellidoPaterno", target = "apellidoPaterno")
    @Mapping(source = "apellidoMaterno", target = "apellidoMaterno")
    @Mapping(source = "numeroDocumento", target = "numeroDocumento")
    @Mapping(source = "tipoEmpleadoDTO", target = "tipoEmpleado")
    @Mapping(source = "estado", target = "estado")
    Empleado empleadoDTOToEmpleado(EmpleadoDTO empleadoDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "apellidoPaterno", target = "apellidoPaterno")
    @Mapping(source = "apellidoMaterno", target = "apellidoMaterno")
    @Mapping(source = "numeroDocumento", target = "numeroDocumento")
    @Mapping(source = "tipoEmpleado", target = "tipoEmpleadoDTO")
    @Mapping(source = "estado", target = "estado")
    EmpleadoDTO empleadoToEmpleadoDTO(Empleado empleado);
}
