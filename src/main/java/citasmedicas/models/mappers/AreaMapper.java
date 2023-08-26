package citasmedicas.models.mappers;

import citasmedicas.models.dto.AreaDTO;
import citasmedicas.models.entities.Area;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AreaMapper {
    AreaMapper INSTANCE = Mappers.getMapper(AreaMapper.class);

    AreaDTO areaToAreaDTO(Area area);

    Area areaDTOToArea(AreaDTO areaDTO);

    List<Area> areasDTOToAreas(List<AreaDTO> areasDTO);

    List<AreaDTO> areasToAreasDTO(List<Area> areas);
}
