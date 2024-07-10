package citasmedicas.repositories;

import citasmedicas.models.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testGuardar_DadoClienteValido_RetornaClienteGuardado() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellidoPaterno("Perez");
        cliente.setApellidoMaterno("Gomez");
        cliente.setNumeroDocumento("12345678");
        cliente.setFechaNacimiento(new Date(1990, 1, 1));
        cliente.setSexo("M");
        cliente.setTelefono("123456789");
        cliente.setEmail("vorbegozop@gmail.com");

        // Act
        Cliente clienteGuardado = clienteRepository.save(cliente);

        // Assert
        assertThat(clienteGuardado).isNotNull();
        assertThat(clienteGuardado.getId()).isGreaterThan(0);
        assertThat(clienteGuardado.getNombre()).isEqualTo("Juan");
        assertThat(clienteGuardado.getApellidoPaterno()).isEqualTo("Perez");
        assertThat(clienteGuardado.getApellidoMaterno()).isEqualTo("Gomez");
        assertThat(clienteGuardado.getNumeroDocumento()).isEqualTo("12345678");
        assertThat(clienteGuardado.getFechaNacimiento()).isEqualTo(new Date(1990, 1, 1));
        assertThat(clienteGuardado.getSexo()).isEqualTo("M");
        assertThat(clienteGuardado.getTelefono()).isEqualTo("123456789");
        assertThat(clienteGuardado.getEmail()).isEqualTo("vorbegozop@gmail.com");
    }

    @Test
    public void testActualizar_DadoClienteValido_RetornaClienteActualizado() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellidoPaterno("Perez");
        cliente.setApellidoMaterno("Gomez");
        cliente.setNumeroDocumento("12345678");
        cliente.setFechaNacimiento(new Date(1990, 1, 1));
        cliente.setSexo("M");
        cliente.setTelefono("123456789");
        cliente.setEmail("vorbegozop@gmail.com");

        Cliente clienteGuardado = clienteRepository.save(cliente);

        assertThat(clienteGuardado).isNotNull();
        assertThat(clienteGuardado.getId()).isGreaterThan(0);

        // Act
        cliente.setNombre("Maria");
        cliente.setApellidoPaterno("Torres");
        cliente.setApellidoMaterno("Sanchez");
        cliente.setNumeroDocumento("87654321");
        cliente.setFechaNacimiento(new Date(1995, 1, 1));
        cliente.setSexo("F");
        cliente.setTelefono("987654321");
        cliente.setEmail("maria@gmail.com");

        Cliente clienteActualizado = clienteRepository.save(cliente);

        // Assert
        assertThat(clienteActualizado).isNotNull();
        assertThat(clienteActualizado.getId()).isGreaterThan(0);
        assertThat(clienteActualizado.getNombre()).isEqualTo("Maria");
        assertThat(clienteActualizado.getApellidoPaterno()).isEqualTo("Torres");
        assertThat(clienteActualizado.getApellidoMaterno()).isEqualTo("Sanchez");
        assertThat(clienteActualizado.getNumeroDocumento()).isEqualTo("87654321");
        assertThat(clienteActualizado.getFechaNacimiento()).isEqualTo(new Date(1995, 1, 1));
        assertThat(clienteActualizado.getSexo()).isEqualTo("F");
        assertThat(clienteActualizado.getTelefono()).isEqualTo("987654321");
        assertThat(clienteActualizado.getEmail()).isEqualTo("maria@gmail.com");
    }

    @Test
    public void testListar_DadoNoParametros_RetornaListaClientes() {
        // Act
        List<Cliente> clientes = clienteRepository.findAll();

        // Assert
        assertThat(clientes.size()).isGreaterThan(1);
    }
}