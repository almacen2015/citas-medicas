package citasmedicas.services.impl;

import citasmedicas.exceptions.TipoEmpleadoException;
import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.models.entities.TipoEmpleado;
import citasmedicas.models.mappers.TipoEmpleadoMapper;
import citasmedicas.repositories.TipoEmpleadoRepository;
import citasmedicas.services.TipoEmpleadoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoEmpleadoServiceImpl implements TipoEmpleadoService {
    private final TipoEmpleadoRepository repository;
    private final TipoEmpleadoMapper tipoEmpleadoMapper = TipoEmpleadoMapper.INSTANCE;

    public TipoEmpleadoServiceImpl(TipoEmpleadoRepository tipoEmpleadoRepository) {
        this.repository = tipoEmpleadoRepository;
    }

    @Override
    public List<TipoEmpleadoDTO> listar() {
        List<TipoEmpleado> tiposEmpleados = repository.findAll();
        List<TipoEmpleadoDTO> tiposEmpleadosDTO = new ArrayList<>();
        for (TipoEmpleado tipoEmpleado : tiposEmpleados) {
            tiposEmpleadosDTO.add(tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleado));
        }
        return tiposEmpleadosDTO;
    }

    @Override
    public TipoEmpleadoDTO guardar(TipoEmpleadoDTO tipoEmpleadoDTO) {
        TipoEmpleado tipoEmpleado = tipoEmpleadoMapper.tipoEmpleadoDTOToTipoEmpleado(tipoEmpleadoDTO);
        if (buscarPorNombre(tipoEmpleado.getNombre()).isPresent()) {
            throw new TipoEmpleadoException(TipoEmpleadoException.NOMBRE_REPETIDO);
        }
        TipoEmpleado tipoEmpleadoGuardado = repository.save(tipoEmpleado);
        return tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleadoGuardado);
    }

    @Override
    public Optional<TipoEmpleadoDTO> buscarPorId(Integer id) {
        Optional<TipoEmpleado> tipoEmpleado = repository.findById(id);
        if (tipoEmpleado.isEmpty()) return Optional.empty();
        TipoEmpleadoDTO tipoEmpleadoDTO = tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleado.get());
        return Optional.of(tipoEmpleadoDTO);
    }

    @Override
    public Optional<TipoEmpleadoDTO> buscarPorNombre(String nombre) {
        Optional<TipoEmpleado> tipoEmpleado = repository.findByNombre(nombre);
        if (tipoEmpleado.isEmpty()) return Optional.empty();
        TipoEmpleadoDTO tipoEmpleadoDTO = tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleado.get());
        return Optional.of(tipoEmpleadoDTO);
    }

    @Override
    public TipoEmpleadoDTO actualizar(TipoEmpleadoDTO tipoEmpleadoDTO) {
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        Optional<TipoEmpleado> tipoEmpleado = repository.findById(id);
        if (tipoEmpleado.isEmpty()) {
            throw new TipoEmpleadoException(TipoEmpleadoException.ID_NO_EXISTE);
        }
        repository.deleteById(id);
    }
}
