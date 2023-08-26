package citasmedicas.models.mappers;

import citasmedicas.models.dto.CitaDTO;
import citasmedicas.models.entities.Cita;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CitaMapper {
    CitaMapper INSTANCE = Mappers.getMapper(CitaMapper.class);

    @Mapping(source = "cliente", target = "clienteDTO")
    @Mapping(source = "area", target = "areaDTO")
    CitaDTO citaToCitaDTO(Cita cita);

    @Mapping(source = "clienteDTO", target = "cliente")
    @Mapping(source = "areaDTO", target = "area")
    Cita citaDTOToCita(CitaDTO citaDTO);

    @Mapping(source = "cliente", target = "clienteDTO")
    @Mapping(source = "area", target = "areaDTO")
    List<CitaDTO> citasToCitasDTO(List<Cita> citas);

    @Mapping(source = "clienteDTO", target = "cliente")
    @Mapping(source = "areaDTO", target = "area")
    List<Cita> citasDTOToCitas(List<CitaDTO> citasDTO);
}
