package citasmedicas.services.impl;

import citasmedicas.exceptions.ClienteException;
import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Cliente;
import citasmedicas.models.mappers.ClienteMapper;
import citasmedicas.repositories.ClienteRepository;
import citasmedicas.services.ClienteService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repository;
    private final ClienteMapper clienteMapper = ClienteMapper.INSTANCE;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ClienteDTO> listar() {
        List<Cliente> clientes = repository.findAll();
        return clienteMapper.clientesToClientesDTO(clientes);
    }

    @Override
    @Transactional(rollbackOn = ClienteException.class)
    public ClienteDTO guardar(ClienteDTO clienteDTO) {
        validarDatos(clienteDTO);
        Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
        Cliente clienteGuardado = repository.save(cliente);
        return clienteMapper.clienteToClienteDTO(clienteGuardado);
    }

    @Override
    public Optional<ClienteDTO> obtenerClientePorId(Integer id) {
        validarIdCliente(id);
        Optional<Cliente> clienteConsultado = repository.findById(id);
        if (clienteConsultado.isPresent()) {
            return Optional.ofNullable(clienteMapper.clienteToClienteDTO(clienteConsultado.get()));
        }
        throw new ClienteException(ClienteException.CLIENTE_NO_ENCONTRADO);
    }

    @Override
    @Transactional(rollbackOn = ClienteException.class)
    public ClienteDTO actualizar(ClienteDTO clienteDTO, Integer id) {
        validarIdCliente(id);
        validarDatos(clienteDTO);
        Optional<Cliente> clienteConsultado = repository.findById(id);
        if (clienteConsultado.isPresent()) {
            Cliente clienteActualizar = asignarDatos(clienteDTO, clienteConsultado);
            Cliente updatedCliente = repository.save(clienteActualizar);
            return clienteMapper.clienteToClienteDTO(updatedCliente);
        }
        return null;
    }

    @Override
    @Transactional(rollbackOn = ClienteException.class)
    public void eliminar(Integer id) {
        validarIdCliente(id);
        repository.deleteById(id);
    }

    private Cliente asignarDatos(ClienteDTO clienteDTO, Optional<Cliente> clienteConsultado) {
        Cliente clienteActualizar = new Cliente();
        clienteActualizar = clienteMapper.clienteDTOToCliente(clienteDTO);
        if (clienteConsultado.isPresent()) {
            clienteActualizar.setId(clienteConsultado.get().getId());
        }
        return clienteActualizar;
    }

    @Override
    public void validarDatos(ClienteDTO clienteDTO) {
        String nombre = clienteDTO.nombre();
        String apellidoPaterno = clienteDTO.apellidoPaterno();
        String apellidoMaterno = clienteDTO.apellidoMaterno();
        String numeroDocumento = clienteDTO.numeroDocumento();
        String sexo = clienteDTO.sexo();

        validarNombre(nombre);
        validarApellidoMaterno(apellidoMaterno);
        validarApellidoPaterno(apellidoPaterno);
        validarNumeroDocumento(numeroDocumento);
        validarSexo(sexo);
        validarFechaNacimiento(clienteDTO);
    }

    private void validarIdCliente(Integer id) {
        if (id == null || id <= 0) {
            throw new ClienteException(ClienteException.ID_NO_VALIDO);
        }
    }

    private void validarFechaNacimiento(ClienteDTO clienteDTO) {
        if (clienteDTO.fechaNacimiento() == null) {
            throw new ClienteException(ClienteException.FECHA_NACIMIENTO_NO_VALIDO);
        }

        LocalDate fechaNacimiento = LocalDate.parse(clienteDTO.fechaNacimiento());
        if (esFechaNacimientoHoy(fechaNacimiento) || esFechaNacimientoDespuesHoy(fechaNacimiento)) {
            throw new ClienteException(ClienteException.FECHA_NACIMIENTO_NO_VALIDO);
        }
    }

    private static void validarSexo(String sexo) {
        if (sexo == null || sexo.isEmpty() || sexo.isBlank()) {
            throw new ClienteException(ClienteException.SEXO_NO_VALIDO);
        }

        if (!sexo.equals("F") && !sexo.equals("M")) {
            throw new ClienteException(ClienteException.SEXO_NO_VALIDO);
        }
    }

    private void validarNumeroDocumento(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.isBlank() || numeroDocumento.length() != 8 || !soloNumeros(numeroDocumento)) {
            throw new ClienteException(ClienteException.NUMERO_DOCUMENTO_NO_VALIDO);
        }
    }

    private static void validarApellidoPaterno(String apellidoPaterno) {
        if (apellidoPaterno == null || apellidoPaterno.isBlank() || apellidoPaterno.isEmpty() || apellidoPaterno.length() > 255) {
            throw new ClienteException(ClienteException.APELLIDO_PATERNO_NO_VALIDO);
        }
    }

    private static void validarApellidoMaterno(String apellidoMaterno) {
        if (apellidoMaterno == null || apellidoMaterno.isBlank() || apellidoMaterno.isEmpty() || apellidoMaterno.length() > 255) {
            throw new ClienteException(ClienteException.APELLIDO_MATERNO_NO_VALIDO);
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank() || nombre.isEmpty() || nombre.length() > 255) {
            throw new ClienteException(ClienteException.NOMBRE_NO_VALIDO);
        }
    }

    private boolean soloNumeros(String str) {
        return str != null && str.matches("\\d+");
    }

    private boolean esFechaNacimientoHoy(LocalDate fecha) {
        LocalDate fechaHoy = LocalDate.now();
        return fecha.isEqual(fechaHoy);
    }

    private boolean esFechaNacimientoDespuesHoy(LocalDate fecha) {
        LocalDate fechaHoy = LocalDate.now();
        return fecha.isAfter(fechaHoy);
    }
}
