package citasmedicas.service;

import citasmedicas.model.Cliente;
import citasmedicas.model.dto.ClienteDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class)
public interface ClienteService {
    List<ClienteDTO> listar();

    Cliente guardar(ClienteDTO clienteDTO);

    Optional<ClienteDTO> obtenerCliente(Integer id);

    ClienteDTO actualizar(ClienteDTO clienteDTO, Integer id);

    void eliminar(Integer id);
}
