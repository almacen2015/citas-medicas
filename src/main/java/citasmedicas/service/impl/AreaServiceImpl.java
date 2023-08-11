package citasmedicas.service.impl;

import citasmedicas.exceptions.AreaException;
import citasmedicas.mappers.AreaMapper;
import citasmedicas.models.dto.AreaDTO;
import citasmedicas.models.entities.Area;
import citasmedicas.repository.AreaRepository;
import citasmedicas.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AreaServiceImpl implements AreaService {
    private final AreaRepository repository;
    private final AreaMapper areaMapper = AreaMapper.INSTANCE;

    public AreaServiceImpl(AreaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AreaDTO> obtenerPorNombre(String nombre) {
        Optional<Area> area = repository.findByNombre(nombre);
        if (area.isPresent()) {
            return Optional.ofNullable(areaMapper.areaToAreaDTO(area.get()));
        }
        return Optional.empty();
    }

    @Override
    public AreaDTO guardar(AreaDTO areaDTO) {
        validarDatos(areaDTO);
        Area area = AreaMapper.INSTANCE.areaDTOToArea(areaDTO);
        Area newArea = repository.save(area);
        return areaMapper.areaToAreaDTO(newArea);
    }

    private void validarDatos(AreaDTO areaDTO) {
        if (areaDTO.nombre().isEmpty()) {
            throw new AreaException(AreaException.NOMBRE_NO_VALIDO);
        }

        if (obtenerPorNombre(areaDTO.nombre()).isPresent()) {
            throw new AreaException(AreaException.NOMBRE_EXISTE);
        }
    }

    @Override
    public List<AreaDTO> listar() {
        List<Area> areas = repository.findAll();
        List<AreaDTO> areaDTOS = new ArrayList<>();
        for (Area area : areas) {
            areaDTOS.add(areaMapper.areaToAreaDTO(area));
        }
        return areaDTOS;
    }

    @Override
    public AreaDTO actualizar(AreaDTO areaDTO, Integer id) {
        Optional<AreaDTO> areaDtoConsultado = obtenerPorId(id);
        if (areaDtoConsultado.isPresent()) {
            validarDatos(areaDTO);
            Area area = asignarDatosActualizar(areaDTO, areaDtoConsultado);
            return null;
        }
        return null;
    }

    private Area asignarDatosActualizar(AreaDTO areaDTO, Optional<AreaDTO> areaConsultado) {
        Area area = areaMapper.areaDTOToArea(areaDTO);
        areaConsultado.ifPresent(dto -> area.setId(dto.id()));
        return area;
    }

    @Override
    public Optional<AreaDTO> obtenerPorId(Integer id) {
        Optional<Area> area = repository.findById(id);
        if (area.isPresent()) {
            return Optional.ofNullable(areaMapper.areaToAreaDTO(area.get()));
        }
        return Optional.empty();
    }
}
