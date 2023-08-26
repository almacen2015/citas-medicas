package citasmedicas.models.mappers;

import citasmedicas.models.dto.EmpleadoDTO;
import citasmedicas.models.entities.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmpleadoMapper {

    EmpleadoMapper INSTANCE = Mappers.getMapper(EmpleadoMapper.class);

    @Mapping(source = "tipoEmpleadoDTO", target = "tipoEmpleado")
    Empleado empleadoDTOToEmpleado(EmpleadoDTO empleadoDTO);

    @Mapping(source = "tipoEmpleado", target = "tipoEmpleadoDTO")
    EmpleadoDTO empleadoToEmpleadoDTO(Empleado empleado);

    @Mapping(source = "tipoEmpleado", target = "tipoEmpleadoDTO")
    List<EmpleadoDTO> empleadosToEmpleadosDTO(List<Empleado> empleados);

    @Mapping(source = "tipoEmpleadoDTO", target = "tipoEmpleado")
    List<Empleado> empleadosDTOToEmpleados(List<EmpleadoDTO> empleadosDTO);
}
