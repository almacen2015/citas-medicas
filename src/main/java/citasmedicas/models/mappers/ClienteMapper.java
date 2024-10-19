package citasmedicas.models.mappers;

import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Cliente;
import citasmedicas.utils.Util;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = Util.class)
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento", dateFormat = "yyyy-MM-dd")
    ClienteDTO clienteToClienteDTO(Cliente cliente);

    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento" , dateFormat = "yyyy-MM-dd")
    Cliente clienteDTOToCliente(ClienteDTO clienteDTO);

    List<ClienteDTO> clientesToClientesDTO(List<Cliente> clientes);

    List<Cliente> clientesDTOToClientes(List<ClienteDTO> clientesDTO);
}
