package citasmedicas.models.mappers;

import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDTO clienteToClienteDTO(Cliente cliente);

    Cliente clienteDTOToCliente(ClienteDTO clienteDTO);

    List<ClienteDTO> clientesToClientesDTO(List<Cliente> clientes);

    List<Cliente> clientesDTOToClientes(List<ClienteDTO> clientesDTO);
}
