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
        buscarPorNombre(tipoEmpleado.getNombre());
        TipoEmpleado tipoEmpleadoGuardado = repository.save(tipoEmpleado);
        return tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleadoGuardado);
    }

    @Override
    public TipoEmpleadoDTO buscarPorId(Integer id) {
        Optional<TipoEmpleado> tipoEmpleado = repository.findById(id);
        if (tipoEmpleado.isEmpty()) {
            throw new TipoEmpleadoException(TipoEmpleadoException.ID_NO_EXISTE);
        }
        return tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleado.get());
    }

    @Override
    public TipoEmpleadoDTO buscarPorNombre(String nombre) {
        Optional<TipoEmpleado> tipoEmpleado = repository.findByNombre(nombre);
        if (tipoEmpleado.isEmpty()) {
            throw new TipoEmpleadoException(TipoEmpleadoException.NOMBRE_NO_EXISTE);
        }
        return tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleado.get());
    }

    @Override
    public TipoEmpleadoDTO actualizar(TipoEmpleadoDTO tipoEmpleadoDTO) {
        TipoEmpleado tipoEmpleado = tipoEmpleadoMapper.tipoEmpleadoDTOToTipoEmpleado(tipoEmpleadoDTO);
        Optional<TipoEmpleado> tipoEmpleadoEncontrado = repository.findById(tipoEmpleado.getId());
        if (tipoEmpleadoEncontrado.isEmpty()) {
            throw new TipoEmpleadoException(TipoEmpleadoException.ID_NO_EXISTE);
        }
        TipoEmpleado tipoEmpleadoActualizado = repository.save(tipoEmpleadoMapper.tipoEmpleadoDTOToTipoEmpleado(tipoEmpleadoDTO));
        return tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleadoActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        TipoEmpleadoDTO tipoEmpleadoDTOEncontrado = buscarPorId(id);
        repository.deleteById(tipoEmpleadoDTOEncontrado.id());
    }
}
