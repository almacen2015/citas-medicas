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
}
