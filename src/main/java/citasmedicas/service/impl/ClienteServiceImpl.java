package citasmedicas.service.impl;

import citasmedicas.exceptions.ClienteException;
import citasmedicas.model.Cliente;
import citasmedicas.model.dto.ClienteDTO;
import citasmedicas.repository.ClienteRepository;
import citasmedicas.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @Override
    public Cliente guardar(ClienteDTO clienteDTO) {
        validarDatos(clienteDTO);
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return repository.save(cliente);
    }

    @Override
    public Optional<Cliente> obtenerCliente(Integer id) {
        Optional<Cliente> clienteConsultado = repository.findById(id);
        if (clienteConsultado.isPresent()) {
            return clienteConsultado;
        }
        throw new ClienteException(ClienteException.CLIENTE_NO_ENCONTRADO);
    }

    @Override
    public Cliente actualizar(ClienteDTO clienteDTO, Integer id) {
        Optional<Cliente> clienteConsultado = obtenerCliente(id);
        if (clienteConsultado.isPresent()) {
            validarDatos(clienteDTO);
            Cliente clienteActualizar = asignarDatos(clienteDTO, clienteConsultado);
            return repository.save(clienteActualizar);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    private Cliente asignarDatos(ClienteDTO clienteDTO, Optional<Cliente> clienteConsultado) {
        Cliente clienteActualizar = new Cliente();
        clienteActualizar = modelMapper.map(clienteDTO, Cliente.class);
        clienteActualizar.setId(clienteConsultado.get().getId());
        return clienteActualizar;
    }

    private void validarDatos(ClienteDTO clienteDTO) {
        if (clienteDTO.getNombre().equals("")) {
            throw new ClienteException(ClienteException.NOMBRE_NO_VALIDO);
        }

        if (clienteDTO.getApellidoMaterno().equals("")) {
            throw new ClienteException(ClienteException.APELLIDO_MATERNO_NO_VALIDO);
        }

        if (clienteDTO.getApellidoPaterno().equals("")) {
            throw new ClienteException(ClienteException.APELLIDO_PATERNO_NO_VALIDO);
        }

        if (clienteDTO.getNumeroDocumento().equals("")) {
            throw new ClienteException(ClienteException.NUMERO_DOCUMENTO_NO_VALIDO);
        }

        if (clienteDTO.getSexo().equals("")) {
            throw new ClienteException(ClienteException.SEXO_NO_VALIDO);
        }
    }
}
