package citasmedicas.repositories;

import citasmedicas.models.entities.TipoEmpleado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TipoEmpleadoRepositoryTest {

    @Autowired
    private TipoEmpleadoRepository repository;

    @Test
    public void testFinByid_DadoIdInvalido_RetornaNull() {
        // Arrange
        TipoEmpleado tipoEmpleado = new TipoEmpleado("Medico General");
        repository.save(tipoEmpleado);

        // Act
        Optional<TipoEmpleado> tipoEmpleadoEncontrado = repository.findById(0);

        // Assert
        assertThat(tipoEmpleadoEncontrado).isEmpty();
    }

    @Test
    public void testFinByid_DadoIdValido_RetornaTipoEmpleado() {
        // Arrange
        TipoEmpleado tipoEmpleado = new TipoEmpleado("Medico General");
        repository.save(tipoEmpleado);

        // Act
        Optional<TipoEmpleado> tipoEmpleadoEncontrado = repository.findById(tipoEmpleado.getId());

        // Assert
        assertThat(tipoEmpleadoEncontrado).isNotNull();
        assertThat(tipoEmpleadoEncontrado.isPresent()).isTrue();
        assertThat(tipoEmpleadoEncontrado.get().getNombre()).isEqualTo("Medico General");
    }

    @Test
    public void testActualizar_DadoTipoEmpleadoValido_RetornaTipoEmpleadoActualizado() {
        // Arrange
        TipoEmpleado tipoEmpleado = new TipoEmpleado("Medico General");
        repository.save(tipoEmpleado);

        // Act
        tipoEmpleado.setNombre("Medico General Actualizado");
        TipoEmpleado tipoEmpleadoActualizado = repository.save(tipoEmpleado);

        // Assert
        assertThat(tipoEmpleadoActualizado).isNotNull();
        assertThat(tipoEmpleadoActualizado.getId()).isGreaterThan(0);
        assertThat(tipoEmpleadoActualizado.getNombre()).isEqualTo("Medico General Actualizado");
    }

    @Test
    public void testGuardar_DadoTipoEmpleadoValido_RetornaTipoEmpleadoGuardado() {
        // Arrange
        TipoEmpleado tipoEmpleado = new TipoEmpleado("Medico General");

        // Act
        repository.save(tipoEmpleado);

        // Assert
        assertThat(tipoEmpleado).isNotNull();
        assertThat(tipoEmpleado.getId()).isGreaterThan(0);
        assertThat(tipoEmpleado.getNombre()).isEqualTo("Medico General");
    }

    @Test
    public void testListar_DadoNoParametros_RetornaListaTiposEmpleados() {
        // Arrange
        TipoEmpleado tipoEmpleado = new TipoEmpleado("Medico General");
        TipoEmpleado tipoEmpleado2 = new TipoEmpleado("Enfermero");
        TipoEmpleado tipoEmpleado3 = new TipoEmpleado("Obstetra");
        TipoEmpleado tipoEmpleado4 = new TipoEmpleado("Pediatra");

        repository.save(tipoEmpleado);
        repository.save(tipoEmpleado2);
        repository.save(tipoEmpleado3);
        repository.save(tipoEmpleado4);

        // Act
        List<TipoEmpleado> tiposEmpleados = repository.findAll();

        // Assert
        assertThat(tiposEmpleados.size()).isGreaterThan(1);
    }

    @Test
    public void testFindByNombre_DadoNombreValido_RetornaTipoEmpleado() {
        //Arrange
        TipoEmpleado tipoEmpleado = new TipoEmpleado("Medico General");
        repository.save(tipoEmpleado);
        TipoEmpleado tipoEmpleado2 = new TipoEmpleado("Enfermero");
        repository.save(tipoEmpleado2);

        //Act
        Optional<TipoEmpleado> tipoEmpleadoEncontrado = repository.findByNombre("Medico General");
        Optional<TipoEmpleado> tipoEmpleadoEncontrado2 = repository.findByNombre("Enfermero");

        //Assert
        assertThat(tipoEmpleadoEncontrado).isNotNull();
        assertThat(tipoEmpleadoEncontrado.isPresent()).isTrue();
        assertThat(tipoEmpleadoEncontrado.get().getNombre()).isEqualTo("Medico General");

        assertThat(tipoEmpleadoEncontrado2).isNotNull();
        assertThat(tipoEmpleadoEncontrado2.isPresent()).isTrue();
        assertThat(tipoEmpleadoEncontrado2.get().getNombre()).isEqualTo("Enfermero");
    }

    @Test
    public void testFindByNombre_DadoNombreInvalido_RetornNull() {
        //Arrange
        TipoEmpleado tipoEmpleado = new TipoEmpleado("Medico General");
        repository.save(tipoEmpleado);

        //Act
        Optional<TipoEmpleado> tipoEmpleadoEncontrado = repository.findByNombre("Enfermero");

        //Assert
        assertThat(tipoEmpleadoEncontrado).isEmpty();
    }
}
