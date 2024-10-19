package citasmedicas.models.mappers;

import citasmedicas.models.dto.CitaDTO;
import citasmedicas.models.entities.Cita;
import citasmedicas.utils.Util;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = Util.class)
public interface CitaMapper {
    CitaMapper INSTANCE = Mappers.getMapper(CitaMapper.class);

    @Mapping(source = "cliente", target = "clienteDTO")
    @Mapping(source = "area", target = "areaDTO")
    @Mapping(source = "fechaInicio", target = "fechaInicio", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "fechaFin", target = "fechaFin", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CitaDTO citaToCitaDTO(Cita cita);

    @Mapping(source = "clienteDTO", target = "cliente")
    @Mapping(source = "areaDTO", target = "area")
    @Mapping(source = "fechaInicio", target = "fechaInicio", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "fechaFin", target = "fechaFin", qualifiedByName = "stringToLocalDateTime")
    Cita citaDTOToCita(CitaDTO citaDTO);

    @Mapping(source = "cliente", target = "clienteDTO")
    @Mapping(source = "area", target = "areaDTO")
    List<CitaDTO> citasToCitasDTO(List<Cita> citas);

    @Mapping(source = "clienteDTO", target = "cliente")
    @Mapping(source = "areaDTO", target = "area")
    List<Cita> citasDTOToCitas(List<CitaDTO> citasDTO);


}
