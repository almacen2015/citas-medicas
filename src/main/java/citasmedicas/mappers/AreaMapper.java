package citasmedicas.mappers;

import citasmedicas.models.dto.AreaDTO;
import citasmedicas.models.entities.Area;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AreaMapper {
    AreaMapper INSTANCE = Mappers.getMapper(AreaMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    AreaDTO areaToAreaDTO(Area persona);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    Area areaDTOToArea(AreaDTO personaDTO);
}
