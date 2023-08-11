package citasmedicas.mappers;

import citasmedicas.models.dto.CitaDTO;
import citasmedicas.models.entities.Cita;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CitaMapper {
    CitaMapper INSTANCE = Mappers.getMapper(CitaMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cliente", target = "clienteDTO")
    @Mapping(source = "area", target = "areaDTO")
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "fechaInicio", target = "fechaInicio")
    @Mapping(source = "fechaFin", target = "fechaFin")
    @Mapping(source = "estado", target = "estado")
    CitaDTO citaToCitaDTO(Cita cita);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "clienteDTO", target = "cliente")
    @Mapping(source = "areaDTO", target = "area")
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "fechaInicio", target = "fechaInicio")
    @Mapping(source = "fechaFin", target = "fechaFin")
    @Mapping(source = "estado", target = "estado")
    Cita citaDTOToCita(CitaDTO citaDTO);
}
