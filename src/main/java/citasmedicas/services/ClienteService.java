package citasmedicas.services;

import citasmedicas.models.dto.ClienteDTO;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<ClienteDTO> listar();

    ClienteDTO guardar(ClienteDTO clienteDTO);

    Optional<ClienteDTO> obtenerClientePorId(Integer id);

    ClienteDTO actualizar(ClienteDTO clienteDTO, Integer id);

    void eliminar(Integer id);
}
