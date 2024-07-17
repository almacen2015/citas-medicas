package citasmedicas.services.impl;

import citasmedicas.exceptions.AreaException;
import citasmedicas.models.dto.AreaDTO;
import citasmedicas.models.entities.Area;
import citasmedicas.models.mappers.AreaMapper;
import citasmedicas.repositories.AreaRepository;
import citasmedicas.services.AreaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public AreaDTO obtenerPorNombre(String nombre) {
        Optional<Area> area = repository.findByNombre(nombre);
        return area.map(areaMapper::areaToAreaDTO).orElse(null);
    }

    @Override
    @Transactional(rollbackOn = AreaException.class)
    public AreaDTO guardar(AreaDTO areaDTO) {
        validarDatos(areaDTO);
        Area area = areaMapper.areaDTOToArea(areaDTO);
        Area areaGuardada = repository.save(area);
        return areaMapper.areaToAreaDTO(areaGuardada);
    }

    private void validarDatos(AreaDTO areaDTO) {
        final int MAX_LENGHT_NOMBRE = 255;

        if (areaDTO.nombre().length() > MAX_LENGHT_NOMBRE) {
            throw new AreaException(AreaException.NOMBRE_NO_VALIDO);
        }

        if (areaDTO.nombre().isEmpty()) {
            throw new AreaException(AreaException.NOMBRE_NO_VALIDO);
        }

        if (repository.findByNombre(areaDTO.nombre()).isPresent()) {
            throw new AreaException(AreaException.NOMBRE_EXISTE);
        }
    }

    @Override
    public List<AreaDTO> listar() {
        List<Area> areas = repository.findAll();
        return areaMapper.areasToAreasDTO(areas);
    }

    @Override
    @Transactional(rollbackOn = AreaException.class)
    public AreaDTO actualizar(AreaDTO areaDTO, Integer id) {
        Optional<Area> areaEncontrado = repository.findById(id);
        if (areaEncontrado.isPresent()) {
            validarDatos(areaDTO);
            Area area = asignarDatosActualizar(areaDTO, areaEncontrado);
            return areaMapper.areaToAreaDTO(repository.save(area));
        }
        return null;
    }

    @Override
    public AreaDTO obtenerPorId(Integer id) {
        validarId(id);

        Optional<Area> area = repository.findById(id);
        return area.map(areaMapper::areaToAreaDTO).orElse(null);
    }

    private Area asignarDatosActualizar(AreaDTO areaDTO, Optional<Area> areaConsultado) {
        Area area = areaMapper.areaDTOToArea(areaDTO);
        areaConsultado.ifPresent(dto -> area.setId(dto.getId()));
        return area;
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new AreaException(AreaException.ID_NO_VALIDO);
        }
    }
}
