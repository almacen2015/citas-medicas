package citasmedicas.services.impl;

import citasmedicas.exceptions.EmpleadoException;
import citasmedicas.models.dto.EmpleadoDTO;
import citasmedicas.models.entities.Empleado;
import citasmedicas.models.mappers.EmpleadoMapper;
import citasmedicas.repositories.EmpleadoRepository;
import citasmedicas.services.EmpleadoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoRepository repository;
    private EmpleadoMapper mapper = EmpleadoMapper.INSTANCE;

    public EmpleadoServiceImpl(EmpleadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmpleadoDTO guardar(EmpleadoDTO empleadoDTO) {
        return null;
    }

    @Override
    public EmpleadoDTO buscarPorId(Integer id) {
        Optional<Empleado> empleado = repository.findById(id);
        if (empleado.isEmpty()) {
            throw new EmpleadoException(EmpleadoException.ID_NO_EXISTE);
        }
        return mapper.empleadoToEmpleadoDTO(empleado.get());
    }

    @Override
    public EmpleadoDTO buscarPorNumeroDocumento(String numeroDocumento) {
        return null;
    }

    @Override
    public EmpleadoDTO actualizar(EmpleadoDTO empleadoDTO) {
        return null;
    }

    @Override
    public List<EmpleadoDTO> listar() {
        List<Empleado> empleados = repository.findAll();
        return mapper.empleadosToEmpleadosDTO(empleados);
    }

    @Override
    public void eliminar(Integer id) {
        EmpleadoDTO empleadoDTO = buscarPorId(id);
        repository.deleteById(empleadoDTO.id());
    }
}
