package citasmedicas.repositories;

import citasmedicas.models.entities.Area;
import citasmedicas.models.entities.Cita;
import citasmedicas.models.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql(scripts = "classpath:sql/insertar_datos_iniciales.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CitaRepositoryTest {

    @Autowired
    private CitaRepository repository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testFindAll_DadoSinParametros_RetornaCitas() {
        List<Cita> citas = repository.findAll();

        assertThat(citas).isNotEmpty();
        assertThat(citas.size()).isGreaterThan(1);
    }

    @Test
    public void testFindCitasByClienteIdAndEstado_DadoClienteValidoYEstadoActivo_RetornaCitas() {
        List<Cita> citas = repository.findCitasByClienteIdAndEstado(1, "A");
        assertThat(citas).isNotEmpty();
    }

    @Test
    public void testFindCitasByClienteIdAndEstado_DadoClienteValidoYEstadoInactivo_RetornaCitas() {

        List<Cita> citas = repository.findCitasByClienteIdAndEstado(2, "I");

        assertThat(citas).isNotEmpty();
    }

    @Test
    public void testFindCitasByClienteId_DadoClienteValido_RetornaCitas() {
        List<Cita> citas = repository.findCitasByClienteId(1);

        assertThat(citas).isNotEmpty();
    }

    @Test
    public void testSave_DadoDatosValidos_RetornaCita() {
        Area area = areaRepository.findById(1).get();
        Cliente cliente = clienteRepository.findById(2).get();
        Cita citaNueva = new Cita();
        citaNueva.setArea(area);
        citaNueva.setCliente(cliente);
        citaNueva.setEstado("P");
        citaNueva.setFechaInicio(LocalDateTime.now());
        citaNueva.setFechaFin(LocalDateTime.now());
        citaNueva.setTitulo("Nueva Cita");

        Cita citaCreada = repository.save(citaNueva);

        assertThat(citaCreada).isNotNull();
    }
}