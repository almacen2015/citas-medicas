package citasmedicas.service.impl;

import citasmedicas.exceptions.ClienteException;
import citasmedicas.mappers.ClienteMapper;
import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Cliente;
import citasmedicas.repository.ClienteRepository;
import citasmedicas.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<ClienteDTO> clienteDTOS = new ArrayList<>();
        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente);
            clienteDTOS.add(clienteDTO);
        }
        return clienteDTOS;
    }

    @Override
    public Cliente guardar(ClienteDTO clienteDTO) {
        validarDatos(clienteDTO);
        Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
        return repository.save(cliente);
    }

    @Override
    public Optional<ClienteDTO> obtenerCliente(Integer id) {
        Optional<Cliente> clienteConsultado = repository.findById(id);
        if (clienteConsultado.isPresent()) {
            return Optional.ofNullable(clienteMapper.clienteToClienteDTO(clienteConsultado.get()));
        }
        throw new ClienteException(ClienteException.CLIENTE_NO_ENCONTRADO);
    }

    @Override
    public ClienteDTO actualizar(ClienteDTO clienteDTO, Integer id) {
        Optional<ClienteDTO> clienteConsultado = obtenerCliente(id);
        if (clienteConsultado.isPresent()) {
            validarDatos(clienteDTO);
            Cliente clienteActualizar = asignarDatos(clienteDTO, clienteConsultado);
            Cliente updatedCliente = repository.save(clienteActualizar);
            return clienteMapper.clienteToClienteDTO(updatedCliente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    private Cliente asignarDatos(ClienteDTO clienteDTO, Optional<ClienteDTO> clienteConsultado) {
        Cliente clienteActualizar = new Cliente();
        clienteActualizar = clienteMapper.clienteDTOToCliente(clienteDTO);
        if (clienteConsultado.isPresent()) {
            clienteActualizar.setId(clienteConsultado.get().id());
        }
        return clienteActualizar;
    }

    private void validarDatos(ClienteDTO clienteDTO) {
        if (clienteDTO.nombre().equals("")) {
            throw new ClienteException(ClienteException.NOMBRE_NO_VALIDO);
        }

        if (clienteDTO.apellidoMaterno().equals("")) {
            throw new ClienteException(ClienteException.APELLIDO_MATERNO_NO_VALIDO);
        }

        if (clienteDTO.apellidoPaterno().equals("")) {
            throw new ClienteException(ClienteException.APELLIDO_PATERNO_NO_VALIDO);
        }

        if (clienteDTO.numeroDocumento().equals("")) {
            throw new ClienteException(ClienteException.NUMERO_DOCUMENTO_NO_VALIDO);
        }

        if (clienteDTO.sexo().equals("")) {
            throw new ClienteException(ClienteException.SEXO_NO_VALIDO);
        }

        if (clienteDTO.fechaNacimiento() == null) {
            throw new ClienteException(ClienteException.FECHA_NACIMIENTO_NO_VALIDO);
        }
    }
}
