package citasmedicas.models.mappers;

import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "apellidoPaterno", target = "apellidoPaterno")
    @Mapping(source = "apellidoMaterno", target = "apellidoMaterno")
    @Mapping(source = "numeroDocumento", target = "numeroDocumento")
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "sexo", target = "sexo")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "email", target = "email")
    ClienteDTO clienteToClienteDTO(Cliente cliente);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "apellidoPaterno", target = "apellidoPaterno")
    @Mapping(source = "apellidoMaterno", target = "apellidoMaterno")
    @Mapping(source = "numeroDocumento", target = "numeroDocumento")
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "sexo", target = "sexo")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "email", target = "email")
    Cliente clienteDTOToCliente(ClienteDTO clienteDTO);
}
