package citasmedicas.service.impl;

import citasmedicas.exceptions.ClienteException;
import citasmedicas.model.Cliente;
import citasmedicas.repository.ClienteRepository;
import citasmedicas.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
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
    public Cliente actualizar(Cliente cliente, Integer id) {
        Optional<Cliente> clienteConsultado = obtenerCliente(id);
        if (clienteConsultado.isPresent()) {
            validarDatos(cliente);
            Cliente clienteActualizar = asignarDatos(cliente, clienteConsultado);
            return repository.save(clienteActualizar);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    private Cliente asignarDatos(Cliente cliente, Optional<Cliente> clienteConsultado) {
        Cliente clienteActualizar = new Cliente();
        clienteActualizar.setId(clienteConsultado.get().getId());
        clienteActualizar.setNombre(cliente.getNombre());
        clienteActualizar.setApellidoPaterno(cliente.getApellidoPaterno());
        clienteActualizar.setApellidoMaterno(cliente.getApellidoMaterno());
        clienteActualizar.setFechaNacimineto(cliente.getFechaNacimineto());
        clienteActualizar.setEmail(cliente.getEmail());
        clienteActualizar.setNumeroDocumento(cliente.getNumeroDocumento());
        clienteActualizar.setTelefono(cliente.getTelefono());
        return clienteActualizar;
    }

    private void validarDatos(Cliente cliente) {
        if (cliente.getNombre().equals("")) {
            throw new ClienteException(ClienteException.NOMBRE_NO_VALIDO);
        }

        if (cliente.getApellidoMaterno().equals("")) {
            throw new ClienteException(ClienteException.APELLIDO_MATERNO_NO_VALIDO);
        }

        if (cliente.getApellidoPaterno().equals("")) {
            throw new ClienteException(ClienteException.APELLIDO_PATERNO_NO_VALIDO);
        }

        if (cliente.getNumeroDocumento().equals("")) {
            throw new ClienteException(ClienteException.NUMERO_DOCUMENTO_NO_VALIDO);
        }
    }
}
