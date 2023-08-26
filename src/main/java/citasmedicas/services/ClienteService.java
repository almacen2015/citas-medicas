package citasmedicas.services;

import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Cliente;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class)
public interface ClienteService {
    List<ClienteDTO> listar();

    Cliente guardar(ClienteDTO clienteDTO);

    Optional<ClienteDTO> obtenerClientePorId(Integer id);

    ClienteDTO actualizar(ClienteDTO clienteDTO, Integer id);

    void eliminar(Integer id);
}
