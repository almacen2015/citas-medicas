package citasmedicas.service;

import citasmedicas.model.Cliente;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class)
public interface ClienteService {
    List<Cliente> listar();

    Cliente guardar(Cliente cliente);

    Optional<Cliente> obtenerCliente(Integer id);

    Cliente actualizar(Cliente cliente, Integer id);

    void eliminar(Integer id);
}
