package citasmedicas.service.impl;

import citasmedicas.exceptions.AreaException;
import citasmedicas.model.Area;
import citasmedicas.model.dto.AreaDTO;
import citasmedicas.repository.AreaRepository;
import citasmedicas.service.AreaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<AreaDTO> obtenerPorNombre(String nombre) {
        Optional<Area> area = repository.findByNombre(nombre);
        return Optional.ofNullable(modelMapper.map(area, AreaDTO.class));
    }

    @Override
    public AreaDTO guardar(AreaDTO areaDTO) {
        validarDatos(areaDTO);
        Area area = modelMapper.map(areaDTO, Area.class);
        return modelMapper.map(repository.save(area), AreaDTO.class);
    }

    private void validarDatos(AreaDTO areaDTO) {
        if (areaDTO.getNombre().equals("")) {
            throw new AreaException(AreaException.NOMBRE_NO_VALIDO);
        }

        if (obtenerPorNombre(areaDTO.getNombre()).isPresent()) {
            throw new AreaException(AreaException.NOMBRE_EXISTE);
        }
    }

    @Override
    public List<AreaDTO> listar() {
        List<Area> areas = repository.findAll();
        List<AreaDTO> areaDTOS = new ArrayList<>();
        for (Area area : areas) {
            areaDTOS.add(modelMapper.map(area, AreaDTO.class));
        }
        return areaDTOS;
    }

    @Override
    public AreaDTO actualizar(AreaDTO areaDTO, Integer id) {
        Optional<AreaDTO> areaDtoConsultado = obtenerPorId(id);
        if (areaDtoConsultado.isPresent()) {
            validarDatos(areaDTO);
            Area area = asignarDatosActualizar(areaDTO, areaDtoConsultado);
            return modelMapper.map(repository.save(area), AreaDTO.class);
        }
        return null;
    }

    private Area asignarDatosActualizar(AreaDTO areaDTO, Optional<AreaDTO> areaConsultado) {
        Area area = modelMapper.map(areaDTO, Area.class);
        area.setId(areaConsultado.get().getId());
        return area;
    }

    @Override
    public Optional<AreaDTO> obtenerPorId(Integer id) {
        Optional<Area> area = repository.findById(id);
        if (area.isPresent()) {
            return Optional.ofNullable(modelMapper.map(area, AreaDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
