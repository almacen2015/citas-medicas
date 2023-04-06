package citasmedicas.service.impl;

import citasmedicas.exceptions.ClienteException;
import citasmedicas.model.Cliente;
import citasmedicas.model.dto.ClienteDTO;
import citasmedicas.repository.ClienteRepository;
import citasmedicas.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ClienteDTO> listar() {
        List<Cliente> clientes = repository.findAll();
        List<ClienteDTO> clienteDTOS = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Cliente cliente : clientes) {
            String fechaStr = formatter.format(cliente.getFechaNacimiento());
            ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);
            clienteDTO.setFechaNacimiento(fechaStr);
            clienteDTOS.add(clienteDTO);
        }
        return clienteDTOS;
    }

    @Override
    public Cliente guardar(ClienteDTO clienteDTO) {
        validarDatos(clienteDTO);
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return repository.save(cliente);
    }

    @Override
    public Optional<ClienteDTO> obtenerCliente(Integer id) {
        Optional<Cliente> clienteConsultado = repository.findById(id);
        if (clienteConsultado.isPresent()) {
            return Optional.ofNullable(modelMapper.map(clienteConsultado, ClienteDTO.class));
        }
        throw new ClienteException(ClienteException.CLIENTE_NO_ENCONTRADO);
    }

    @Override
    public ClienteDTO actualizar(ClienteDTO clienteDTO, Integer id) {
        Optional<ClienteDTO> clienteConsultado = obtenerCliente(id);
        if (clienteConsultado.isPresent()) {
            validarDatos(clienteDTO);
            Cliente clienteActualizar = asignarDatos(clienteDTO, clienteConsultado);
            return modelMapper.map(repository.save(clienteActualizar),ClienteDTO.class);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    private Cliente asignarDatos(ClienteDTO clienteDTO, Optional<ClienteDTO> clienteConsultado) {
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
