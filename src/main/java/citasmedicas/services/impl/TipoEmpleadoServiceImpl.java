package citasmedicas.services.impl;

import citasmedicas.exceptions.TipoEmpleadoException;
import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.models.entities.TipoEmpleado;
import citasmedicas.models.mappers.TipoEmpleadoMapper;
import citasmedicas.repositories.TipoEmpleadoRepository;
import citasmedicas.services.TipoEmpleadoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        return tipoEmpleadoMapper.tipoEmpleadosToTipoEmpleadosDTO(tiposEmpleados);
    }

    private void validarDatosTipoEmpleado(TipoEmpleadoDTO tipoEmpleadoDTO) {
        verificarNombre(tipoEmpleadoDTO.nombre());
    }

    @Override
    @Transactional
    public TipoEmpleadoDTO guardar(TipoEmpleadoDTO tipoEmpleadoDTO) {
        validarDatosTipoEmpleado(tipoEmpleadoDTO);
        TipoEmpleado tipoEmpleado = tipoEmpleadoMapper.tipoEmpleadoDTOToTipoEmpleado(tipoEmpleadoDTO);
        Optional<TipoEmpleadoDTO> tipoEmpleadoEncontrado = Optional.ofNullable(buscarPorNombre(tipoEmpleado.getNombre()));
        if (tipoEmpleadoEncontrado.isPresent()) {
            throw new TipoEmpleadoException(TipoEmpleadoException.NOMBRE_EXISTE);
        }
        TipoEmpleado tipoEmpleadoGuardado = repository.save(tipoEmpleado);
        return tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleadoGuardado);
    }

    @Override
    public TipoEmpleadoDTO buscarPorId(Integer id) {
        verificarId(id);
        Optional<TipoEmpleado> tipoEmpleado = repository.findById(id);
        if (tipoEmpleado.isPresent()) {
            return tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleado.get());
        }
        return null;
    }

    private void verificarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty() || nombre.isBlank()) {
            throw new TipoEmpleadoException(TipoEmpleadoException.NOMBRE_VACIO);
        }

        if (nombre.length() > 255) {
            throw new TipoEmpleadoException(TipoEmpleadoException.NOMBRE_EXCESO_CARACTERES);
        }
    }

    private void verificarId(Integer id) {
        if (id == null || id <= 0) {
            throw new TipoEmpleadoException(TipoEmpleadoException.ID_NO_EXISTE);
        }
    }

    @Override
    public TipoEmpleadoDTO buscarPorNombre(String nombre) {
        verificarNombre(nombre);
        Optional<TipoEmpleado> tipoEmpleado = repository.findByNombre(nombre);
        if (tipoEmpleado.isPresent()) {
            return tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleado.get());
        }
        return null;
    }

    @Override
    @Transactional
    public TipoEmpleadoDTO actualizar(TipoEmpleadoDTO tipoEmpleadoDTO) {
        TipoEmpleado tipoEmpleado = tipoEmpleadoMapper.tipoEmpleadoDTOToTipoEmpleado(tipoEmpleadoDTO);
        Integer id = tipoEmpleado.getId();
        String nombre = tipoEmpleado.getNombre();

        verificarId(id);
        verificarNombre(nombre);

        Optional<TipoEmpleado> tipoEmpleadoIdEncontrado = repository.findById(id);
        if (tipoEmpleadoIdEncontrado.isEmpty()) {
            throw new TipoEmpleadoException(TipoEmpleadoException.ID_NO_EXISTE);
        }

        Optional<TipoEmpleado> nombreEncontrado = repository.findByNombre(nombre);
        if (nombreEncontrado.isPresent()) {
            throw new TipoEmpleadoException(TipoEmpleadoException.NOMBRE_EXISTE);
        }

        TipoEmpleado tipoEmpleadoActualizado = repository.save(tipoEmpleado);
        TipoEmpleadoDTO tipoEmpleadoActualizadoDTO = tipoEmpleadoMapper.tipoEmpleadoToTipoEmpleadoDTO(tipoEmpleadoActualizado);
        return tipoEmpleadoActualizadoDTO;
    }

    @Override
    public void eliminar(Integer id) {
        TipoEmpleadoDTO tipoEmpleadoDTOEncontrado = buscarPorId(id);
        repository.deleteById(tipoEmpleadoDTOEncontrado.id());
    }
}
