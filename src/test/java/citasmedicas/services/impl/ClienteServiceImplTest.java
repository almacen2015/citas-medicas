package citasmedicas.services.impl;

import citasmedicas.exceptions.ClienteException;
import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Cliente;
import citasmedicas.models.mappers.ClienteMapper;
import citasmedicas.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteServiceImpl service;

    ClienteMapper mapper = ClienteMapper.INSTANCE;

    Cliente cliente1;
    Cliente cliente2;

    @BeforeEach
    void setUp() {
        cliente1 = new Cliente(1, "Victor", "Orbegozo", "Percovich", "70553916", LocalDate.of(1994, 4, 5), "M", "111111", "vorbegozop@gmail.com");
        cliente2 = new Cliente(2, "Fernando", "Torres", "Pacheco", "98765434", LocalDate.of(1996, 9, 20), "M", "222222", "julia@gmail.com");
    }

    @Test
    public void testActualizar_DadoSexoEsDiferenteMasculinoYFemenino_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-10-10", "A", "11111111", "vorbegozop@gmail.com");
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoSexoTieneEspacioBlanco_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-10-10", " ", "11111111", "vorbegozop@gmail.com");
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoSexoEsVacio_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-10-10", "", "11111111", "vorbegozop@gmail.com");
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoSexoEsNull_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-10-10", null, "11111111", "vorbegozop@gmail.com");
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoFechaNacimientoEsInvalido_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-20-10", "M", "11111111", "vorbegozop@gmail.com");
        assertThrows(DateTimeParseException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoFechaNacimientoEsDespuesHoy_RetornaError() {
        cliente1.setFechaNacimiento(LocalDate.of(2050, 10, 20));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoFechaNacimientoEsHoy_RetornaError() {
        cliente1.setFechaNacimiento(LocalDate.now());
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoFechaNacimientoEsNull_RetornaError() {
        cliente1.setFechaNacimiento(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNumeroDocumentoTieneMenos8Caracteres_RetornaError() {
        cliente1.setNumeroDocumento("123456");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNumeroDocumentoTieneCaracterEspecial_RetornaError() {
        cliente1.setNumeroDocumento("123*3456");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNumeroDocumentoTieneUnaLetra_RetornaError() {
        cliente1.setNumeroDocumento("123d3456");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNumeroDocumentoMayor8Caracteres_RetornaError() {
        cliente1.setNumeroDocumento("1".repeat(9));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNumeroDocumentoTieneEspaciosBlanco_RetornaError() {
        cliente1.setNumeroDocumento("    ");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNumeroDocumentoEsVacio_RetornaError() {
        cliente1.setNumeroDocumento("");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNumeroDocumentoEsNull_RetornaError() {
        cliente1.setNumeroDocumento(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoApellidoMaternoSoloTieneEspaciosBlanco_RetornaError() {
        cliente1.setApellidoMaterno("                                                       ");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoApellidoMaternoEsNull_RetornaError() {
        cliente1.setApellidoMaterno(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoApellidoMaternoVacio_RetornaError() {
        cliente1.setApellidoMaterno("");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoApellidoMaternoMayor255Caracteres_RetornaError() {
        cliente1.setApellidoMaterno("a".repeat(256));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoApellidoPaternoSoloTieneEspaciosBlanco_RetornaError() {
        cliente1.setApellidoPaterno("                                                       ");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoApellidoPaternoEsNull_RetornaError() {
        cliente1.setApellidoPaterno(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoApellidoPaternoVacio_RetornaError() {
        cliente1.setApellidoPaterno("");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoApellidoPaternoMayor255Caracteres_RetornaError() {
        cliente1.setApellidoPaterno("a".repeat(256));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNombreSoloTieneEspaciosBlanco_RetornaError() {
        cliente1.setNombre("                                                       ");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNombreEsNull_RetornaError() {
        cliente1.setNombre(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNombreMayor255Caracteres_RetornaError() {
        cliente1.setNombre("a".repeat(256));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoNombreVacio_RetornaError() {
        cliente1.setNombre("");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, 1));
    }

    @Test
    public void testActualizar_DadoIdMenorZero_RetornaError() {
        int id = -1;
        ClienteDTO clienteDTO = new ClienteDTO(2, "Fernando", "Torres", "Pacheco", "98765434", "2024-10-10", "M", "222222", "julia@gmail.com");
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, id));
    }

    @Test
    public void testActualizar_DadoIdZero_RetornaError() {
        int id = 0;
        ClienteDTO clienteDTO = new ClienteDTO(2, "Fernando", "Torres", "Pacheco", "98765434", "2024-10-10", "M", "222222", "julia@gmail.com");
        assertThrows(ClienteException.class, () -> service.actualizar(clienteDTO, id));
    }

    @Test
    public void testActualizar_DadoDatosValidos_RetornaCliente() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(cliente1));
        when(repository.save(any(Cliente.class))).thenReturn(cliente2);

        ClienteDTO clienteDTO = new ClienteDTO(2, "Fernando", "Torres", "Pacheco", "98765434", "2024-10-10", "M", "222222", "julia@gmail.com");

        ClienteDTO clienteActualizado = service.actualizar(clienteDTO, 2);

        assertThat(clienteActualizado).isNotNull();
        assertThat(clienteActualizado.id()).isEqualTo(2);
        assertThat(clienteActualizado.nombre()).isEqualTo("Fernando");

        verify(repository).findById(any(Integer.class));
        verify(repository).save(any(Cliente.class));
    }

    @Test
    public void testGuardar_DadoSexoEsDiferenteMasculinoYFemenino_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-10-10", "A", "11111111", "vorbegozop@gmail.com");
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoSexoTieneEspacioBlanco_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-10-10", " ", "11111111", "vorbegozop@gmail.com");
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoSexoEsVacio_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-10-10", "", "11111111", "vorbegozop@gmail.com");
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoSexoEsNull_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-10-10", null, "11111111", "vorbegozop@gmail.com");
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoFechaNacimientoEsInvalido_RetornaError() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-20-10", "M", "11111111", "vorbegozop@gmail.com");
        assertThrows(DateTimeParseException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoFechaNacimientoEsDespuesHoy_RetornaError() {
        cliente1.setFechaNacimiento(LocalDate.of(2050, 10, 20));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoFechaNacimientoEsHoy_RetornaError() {
        cliente1.setFechaNacimiento(LocalDate.now());
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoFechaNacimientoEsNull_RetornaError() {
        cliente1.setFechaNacimiento(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNumeroDocumentoTieneMenos8Caracteres_RetornaError() {
        cliente1.setNumeroDocumento("123456");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNumeroDocumentoTieneCaracterEspecial_RetornaError() {
        cliente1.setNumeroDocumento("123*3456");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNumeroDocumentoTieneUnaLetra_RetornaError() {
        cliente1.setNumeroDocumento("123d3456");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNumeroDocumentoMayor8Caracteres_RetornaError() {
        cliente1.setNumeroDocumento("1".repeat(9));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNumeroDocumentoTieneEspaciosBlanco_RetornaError() {
        cliente1.setNumeroDocumento("    ");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNumeroDocumentoEsVacio_RetornaError() {
        cliente1.setNumeroDocumento("");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNumeroDocumentoEsNull_RetornaError() {
        cliente1.setNumeroDocumento(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoApellidoMaternoSoloTieneEspaciosBlanco_RetornaError() {
        cliente1.setApellidoMaterno("                                                       ");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoApellidoMaternoEsNull_RetornaError() {
        cliente1.setApellidoMaterno(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoApellidoMaternoVacio_RetornaError() {
        cliente1.setApellidoMaterno("");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoApellidoMaternoMayor255Caracteres_RetornaError() {
        cliente1.setApellidoMaterno("a".repeat(256));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoApellidoPaternoSoloTieneEspaciosBlanco_RetornaError() {
        cliente1.setApellidoPaterno("                                                       ");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoApellidoPaternoEsNull_RetornaError() {
        cliente1.setApellidoPaterno(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoApellidoPaternoVacio_RetornaError() {
        cliente1.setApellidoPaterno("");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoApellidoPaternoMayor255Caracteres_RetornaError() {
        cliente1.setApellidoPaterno("a".repeat(256));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNombreSoloTieneEspaciosBlanco_RetornaError() {
        cliente1.setNombre("                                                       ");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNombreEsNull_RetornaError() {
        cliente1.setNombre(null);
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNombreMayor255Caracteres_RetornaError() {
        cliente1.setNombre("a".repeat(256));
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoNombreVacio_RetornaError() {
        cliente1.setNombre("");
        ClienteDTO clienteDTO = mapper.clienteToClienteDTO(cliente1);
        assertThrows(ClienteException.class, () -> service.guardar(clienteDTO));
    }

    @Test
    public void testGuardar_DadoDatosValidos_RetornaCliente() {
        when(repository.save(any(Cliente.class))).thenReturn(cliente1);
        ClienteDTO clienteGuardar = mapper.clienteToClienteDTO(cliente1);
        ClienteDTO clienteDTO = service.guardar(clienteGuardar);

        assertThat(clienteDTO).isNotNull();
        assertThat(clienteDTO.nombre()).isEqualTo("Victor");
        verify(repository).save(cliente1);
    }

    @Test
    public void testObtenerClientePorId_DadoIdMenorZero_RetornaError() {
        int clienteId = -1;
        assertThrows(ClienteException.class, () -> service.obtenerClientePorId(clienteId));
    }

    @Test
    public void testObtenerClientePorId_DadoIdIgualZero_RetornaError() {
        int clienteId = 0;
        assertThrows(ClienteException.class, () -> service.obtenerClientePorId(clienteId));
    }

    @Test
    public void testObtenerClientePorId_DadoIdValido_RetornaCliente() {
        int clienteId = 1;
        when(repository.findById(clienteId)).thenReturn(Optional.of(cliente1));

        Optional<ClienteDTO> clienteDTO = service.obtenerClientePorId(1);

        assertThat(clienteDTO).isNotNull();
        assertThat(clienteDTO).isPresent();
        assertThat(clienteDTO.get().nombre()).isEqualTo("Victor");

        verify(repository).findById(1);
    }

    @Test
    public void testListarClientes_DadoSinParametro_RetornaClientes() {
        when(repository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<ClienteDTO> clientes = service.listar();

        verify(repository).findAll();
        assertThat(clientes).isNotEmpty();
    }


}