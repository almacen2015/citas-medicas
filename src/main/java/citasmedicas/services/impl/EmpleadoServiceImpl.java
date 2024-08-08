package citasmedicas.services.impl;

import citasmedicas.exceptions.EmpleadoException;
import citasmedicas.models.dto.EmpleadoDTO;
import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.models.entities.Empleado;
import citasmedicas.models.mappers.EmpleadoMapper;
import citasmedicas.repositories.EmpleadoRepository;
import citasmedicas.repositories.filtros.FiltroEmpleado;
import citasmedicas.services.EmpleadoService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<EmpleadoDTO> listarEmpleados(FiltroEmpleado filtro, Integer paginaInicio, Integer cantidadDatos) {
        Pageable paginado = construirPaginado(paginaInicio, cantidadDatos);
        List<Empleado> empleados = repository.buscarPorFiltroEmpleado(filtro, paginado);
        return mapper.empleadosToEmpleadosDTO(empleados);
    }

    @Override
    public List<EmpleadoDTO> listarPorEstado(boolean estado, Integer paginaInicio, Integer cantidadDatos) {
        Pageable paginado = construirPaginado(paginaInicio, cantidadDatos);
        List<Empleado> empleados = repository.findAllByEstado(estado, paginado);
        return mapper.empleadosToEmpleadosDTO(empleados);
    }

    @Override
    @Transactional
    public EmpleadoDTO guardar(EmpleadoDTO empleadoDTO) {
        verificarDatosParaGuardar(empleadoDTO);
        Empleado empleado = mapper.empleadoDTOToEmpleado(empleadoDTO);
        Empleado empleadoGuardado = repository.save(empleado);
        EmpleadoDTO empleadoDTOGuardado = mapper.empleadoToEmpleadoDTO(empleadoGuardado);
        return empleadoDTOGuardado;
    }

    @Override
    public EmpleadoDTO buscarPorId(Integer id) {
        verificarId(id);
        Optional<Empleado> empleado = repository.findById(id);
        if (empleado.isEmpty()) {
            throw new EmpleadoException(EmpleadoException.EMPLEADO_NO_ENCONTRADO);
        }
        return mapper.empleadoToEmpleadoDTO(empleado.get());
    }

    @Override
    public EmpleadoDTO buscarPorNumeroDocumento(String numeroDocumento) {
        verificarNumeroDocumento(numeroDocumento);
        Empleado empleadoEncontrado = repository.findByNumeroDocumento(numeroDocumento).orElseThrow(() -> new EmpleadoException(EmpleadoException.EMPLEADO_NO_ENCONTRADO));
        EmpleadoDTO empleadoDTOEncontrado = mapper.empleadoToEmpleadoDTO(empleadoEncontrado);
        return empleadoDTOEncontrado;
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
        verificarId(id);
        EmpleadoDTO empleadoDTO = buscarPorId(id);
        repository.deleteById(empleadoDTO.id());
    }

    private Pageable construirPaginado(Integer pagina, Integer cantidadDatos) {
        if (pagina < 0 || cantidadDatos <= 0) {
            throw new EmpleadoException(EmpleadoException.ERROR_PAGINADO);
        }

        Pageable pageable = PageRequest.of(pagina, cantidadDatos);
        return pageable;
    }

    private void verificarId(Integer id) {
        if (id == null || id <= 0) {
            throw new EmpleadoException(EmpleadoException.ID_NO_EXISTE);
        }
    }

    private void verificarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty() || nombre.isBlank()) {
            throw new EmpleadoException(EmpleadoException.NOMBRE_VACIO);
        }
        if (nombre.length() > 255) {
            throw new EmpleadoException(EmpleadoException.NOMBRE_EXCESO_CARACTERES);
        }
    }

    private void verificarApellidoPaterno(String apellidoPaterno) {
        if (apellidoPaterno == null || apellidoPaterno.isEmpty() || apellidoPaterno.isBlank()) {
            throw new EmpleadoException(EmpleadoException.APELLIDO_PATERNO_VACIO);
        }
        if (apellidoPaterno.length() > 255) {
            throw new EmpleadoException(EmpleadoException.APELLIDO_PATERNO_EXCESO_CARACTERES);
        }
    }

    private void verificarApellidoMaterno(String apellidoMaterno) {
        if (apellidoMaterno == null || apellidoMaterno.isEmpty() || apellidoMaterno.isBlank()) {
            throw new EmpleadoException(EmpleadoException.APELLIDO_MATERNO_VACIO);
        }
        if (apellidoMaterno.length() > 255) {
            throw new EmpleadoException(EmpleadoException.APELLIDO_MATERNO_EXCESO_CARACTERES);
        }
    }

    private void verificarTipoEmpleado(TipoEmpleadoDTO tipoEmpleado) {
        if (tipoEmpleado == null) {
            throw new EmpleadoException(EmpleadoException.TIPO_EMPLEADO_VACIO);
        }
    }

    private void verificarNumeroDocumento(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.isEmpty() || numeroDocumento.isBlank()) {
            throw new EmpleadoException(EmpleadoException.NUMERO_DOCUMENTO_VACIO);
        }
        if (numeroDocumento.length() > 8) {
            throw new EmpleadoException(EmpleadoException.NUMERO_DOCUMENTO_EXCESO_CARACTERES);
        }
    }

    private void verificarDatosParaGuardar(EmpleadoDTO empleadoDTO) {
        verificarNombre(empleadoDTO.nombre());
        verificarApellidoPaterno(empleadoDTO.apellidoPaterno());
        verificarApellidoMaterno(empleadoDTO.apellidoMaterno());
        verificarTipoEmpleado(empleadoDTO.tipoEmpleadoDTO());
        verificarNumeroDocumento(empleadoDTO.numeroDocumento());
    }
}
