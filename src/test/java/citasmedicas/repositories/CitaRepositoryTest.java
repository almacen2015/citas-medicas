package citasmedicas.repositories;

import citasmedicas.models.entities.Cita;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql(scripts = "classpath:sql/insertar_datos_iniciales.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CitaRepositoryTest {

    @Autowired
    private CitaRepository repository;

    @Test
    public void testFindAll_DadoSinParametros_RetornaCitas() {
        List<Cita> citas = repository.findAll();

        assertThat(citas).isNotEmpty();
        assertThat(citas.size()).isGreaterThan(1);
    }

    @Test
    public void testFindCitasByClienteIdAndEstado_DadoClienteValidoYEstadoActivo_RetonaCitas() {
        List<Cita> citas = repository.findCitasByClienteIdAndEstado(1, "A");
        assertThat(citas).isNotEmpty();
    }

    @Test
    public void testFindCitasByClienteIdAndEstado_DadoClienteValidoYEstadoInactivo_RetonaCitas() {

        List<Cita> citas = repository.findCitasByClienteIdAndEstado(2, "I");

        assertThat(citas).isNotEmpty();
    }

    @Test
    public void testFindCitasByClienteId_DadoClienteValido_RetonaCitas() {
        List<Cita> citas = repository.findCitasByClienteId(1);

        assertThat(citas).isNotEmpty();
    }
}