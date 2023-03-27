package citasmedicas.service;

import citasmedicas.model.Cliente;
import citasmedicas.model.dto.ClienteDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class)
public interface ClienteService {
    List<Cliente> listar();

    Cliente guardar(ClienteDTO clienteDTO);

    Optional<Cliente> obtenerCliente(Integer id);

    Cliente actualizar(ClienteDTO clienteDTO, Integer id);

    void eliminar(Integer id);
}
