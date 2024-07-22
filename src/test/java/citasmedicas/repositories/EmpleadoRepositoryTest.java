package citasmedicas.repositories;

import citasmedicas.models.entities.Empleado;
import citasmedicas.models.entities.TipoEmpleado;
import citasmedicas.repositories.filtros.FiltroEmpleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository repository;

    @Autowired
    private TipoEmpleadoRepository repositoryTipoEmpleado;

    TipoEmpleado tipoEmpleado;
    TipoEmpleado tipoEmpleado2;
    TipoEmpleado tipoEmpleado3;

    Empleado empleado;
    Empleado empleado2;
    Empleado empleado3;

    Pageable paginado;

    @BeforeEach
    public void setUp() {
        tipoEmpleado = new TipoEmpleado(null, "Medico General");
        tipoEmpleado2 = new TipoEmpleado(null, "Enfermera");
        tipoEmpleado3 = new TipoEmpleado(null, "Odontólogo");

        repositoryTipoEmpleado.save(tipoEmpleado);
        repositoryTipoEmpleado.save(tipoEmpleado2);
        repositoryTipoEmpleado.save(tipoEmpleado3);

        empleado = new Empleado(null, "Victor", "Orbegozo", "Percovich", "70553916", tipoEmpleado, true);
        empleado2 = new Empleado(null, "Maria", "Cardenas", "Sanchez", "12345678", tipoEmpleado2, false);
        empleado3 = new Empleado(null, "Luis", "Torres", "Manrique", "54367823", tipoEmpleado3, true);

        repository.save(empleado);
        repository.save(empleado2);
        repository.save(empleado3);

        paginado = PageRequest.of(0, 10);
    }

    @Test
    public void testFindAll_DadoSinParametros_RetornaEmpleados() {
        List<Empleado> empleados = repository.findAll();

        assertThat(empleados).isNotEmpty();
        assertThat(empleados.size()).isEqualTo(3);
        assertThat(empleados.size()).isGreaterThan(1);
    }

    @Test
    public void testFindAllByNombre_DadoNombreValido_RetonarEmpleado() {
        final String nombre = "victor";

        List<Empleado> empleadoEncontrado = repository.findAllByNombreContainingIgnoreCase(nombre, paginado);

        assertThat(empleadoEncontrado).isNotEmpty();
        assertThat(empleadoEncontrado.size()).isGreaterThan(0);
    }

    @Test
    public void testFindAllByNombre_DadoNombreNull_RetonarVacio() {
        List<Empleado> empleadoEncontrado = repository.findAllByNombreContainingIgnoreCase(null, paginado);

        assertThat(empleadoEncontrado).isEmpty();
    }

    @Test
    public void testFindAllByNombre_DadoNombreVacio_RetonarEmpleados() {
        final String nombre = "";

        List<Empleado> empleadoEncontrado = repository.findAllByNombreContainingIgnoreCase(nombre, paginado);

        assertThat(empleadoEncontrado).isNotEmpty();
    }

    @Test
    public void testFindAllByNombre_DadoNombreEspacioBlanco_RetonarVacio() {
        final String nombre = "        ";

        List<Empleado> empleadoEncontrado = repository.findAllByNombreContainingIgnoreCase(nombre, paginado);

        assertThat(empleadoEncontrado).isEmpty();
    }


    @Test
    public void testFindAllByNombre_DadoNombreNoEncontrado_RetonarVacio() {
        final String nombre = "Pericles";

        List<Empleado> empleadoEncontrado = repository.findAllByNombreContainingIgnoreCase(nombre, paginado);

        assertThat(empleadoEncontrado).isEmpty();
    }

    @Test
    public void testFindAllByApellidoPaterno_DadoApellidoPaternoEncontrado_RetonarEmpleado() {
        final String nombre = "Orbegozo";

        List<Empleado> empleadoEncontrado = repository.findAllByApellidoPaternoContainingIgnoreCase(nombre, paginado);

        assertThat(empleadoEncontrado).isNotEmpty();
    }

    @Test
    public void testFindAllByApellidoPaterno_DadoApellidoPaternoNoEncontrado_RetonarVacio() {
        final String nombre = "Sala";

        List<Empleado> empleadoEncontrado = repository.findAllByApellidoPaternoContainingIgnoreCase(nombre, paginado);

        assertThat(empleadoEncontrado).isEmpty();
    }

    @Test
    public void testFindAllByApellidoPaterno_DadoApellidoPaternoNull_RetonarVacio() {

        List<Empleado> empleadoEncontrado = repository.findAllByApellidoPaternoContainingIgnoreCase(null, paginado);

        assertThat(empleadoEncontrado).isEmpty();
    }

    @Test
    public void testFindAllByApellidoPaterno_DadoApellidoPaternoEspaciosBlanco_RetonarVacio() {
        final String nombre = "        ";
        List<Empleado> empleadoEncontrado = repository.findAllByApellidoPaternoContainingIgnoreCase(nombre, paginado);

        assertThat(empleadoEncontrado).isEmpty();
    }

    @Test
    public void testFindAllByApellidoPaterno_DadoApellidoPaternoVacio_RetonarEmpleados() {
        final String nombre = "";
        List<Empleado> empleadoEncontrado = repository.findAllByApellidoPaternoContainingIgnoreCase(nombre, paginado);

        assertThat(empleadoEncontrado).isNotEmpty();
    }

    @Test
    public void testFindAllByEstado_DadoEstadoEsTrue_RetonarEmpleados() {
        List<Empleado> empleadoEncontrado = repository.findAllByEstado(true, paginado);

        assertThat(empleadoEncontrado).isNotEmpty();
    }

    @Test
    public void testFindAllByEstado_DadoEstadoEsFalse_RetonarEmpleados() {
        List<Empleado> empleadoEncontrado = repository.findAllByEstado(false, paginado);

        assertThat(empleadoEncontrado).isNotEmpty();
    }

    @Test
    public void testFindByNumeroDocumento_DadoNumeroDocumentoValido_RetornaEmpleado() {
        final String numeroDocumento = "70553916";

        Empleado empleadoEncontrado = repository.findByNumeroDocumento(numeroDocumento).orElse(null);

        assertThat(empleadoEncontrado).isNotNull();
        assertThat(empleadoEncontrado.getNumeroDocumento()).isEqualTo(numeroDocumento);
    }

    @Test
    public void testFindByNumeroDocumento_DadoNumeroDocumentoInvalido_RetornaVacio() {
        final String numeroDocumento = "99999999";

        Optional<Empleado> empleadoEncontrado = repository.findByNumeroDocumento(numeroDocumento);

        assertThat(empleadoEncontrado).isEmpty();
    }

    @Test
    public void testFindByNumeroDocumento_DadoNumeroDocumentoNull_RetornaVacio() {

        Optional<Empleado> empleadoEncontrado = repository.findByNumeroDocumento(null);

        assertThat(empleadoEncontrado).isEmpty();
    }

    @Test
    public void testFindByNumeroDocumento_DadoNumeroDocumentoVacio_RetornaVacio() {
        final String numeroDocumento = "";

        Optional<Empleado> empleadoEncontrado = repository.findByNumeroDocumento(numeroDocumento);

        assertThat(empleadoEncontrado).isEmpty();
    }

    @Test
    public void testFindByNumeroDocumento_DadoNumeroDocumentoEspaciosBlanco_RetornaVacio() {
        final String numeroDocumento = "        ";

        Optional<Empleado> empleadoEncontrado = repository.findByNumeroDocumento(numeroDocumento);

        assertThat(empleadoEncontrado).isEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroValido_RetornaEmpleado() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
        assertThat(empleadosEncontrados.size()).isGreaterThan(0);
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroInvalido_RetornaVacio() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = false;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroNull_RetornaEmpleados() {
        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(null, null, null, null, null);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();

    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroVacio_RetornaEmpleados() {
        FiltroEmpleado filtroEmpleado = new FiltroEmpleado("", "", "", "", null);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroEspaciosBlanco_RetornaEmpleados() {
        FiltroEmpleado filtroEmpleado = new FiltroEmpleado("        ", "        ", "        ", "        ", null);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroNombreNull_RetornaEmpleados() {
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(null, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroApellidoPaternoNull_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, null, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroApellidoMaternoNull_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, null, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroNumeroDocumentoNull_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, null, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroEstadoNull_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, null);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroNombreVacio_RetornaEmpleados() {
        final String nombre = "";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroApellidoPaternoVacio_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoPaterno = "";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroApellidoMaternoVacio_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroNumeroDocumentoVacio_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroNombreEspaciosBlanco_RetornaEmpleados() {
        final String nombre = "        ";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroApellidoPaternoEspaciosBlanco_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoPaterno = "        ";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroApellidoMaternoEspaciosBlanco_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "        ";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroNumeroDocumentoEspaciosBlanco_RetornaEmpleados() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "        ";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isNotEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroNombreNoEncontrado_RetornaVacio() {
        final String nombre = "Pericles";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroApellidoPaternoNoEncontrado_RetornaVacio() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Sala";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroApellidoMaternoNoEncontrado_RetornaVacio() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Sala";
        final String numeroDocumento = "70553916";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroNumeroDocumentoNoEncontrado_RetornaVacio() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "99999999";
        final Boolean estado = true;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isEmpty();
    }

    @Test
    public void testBuscarPorFiltroEmpleado_DadoFiltroEstadoNoEncontrado_RetornaVacio() {
        final String nombre = "Victor";
        final String apellidoPaterno = "Orbegozo";
        final String apellidoMaterno = "Percovich";
        final String numeroDocumento = "70553916";
        final Boolean estado = false;

        FiltroEmpleado filtroEmpleado = new FiltroEmpleado(nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, estado);

        List<Empleado> empleadosEncontrados = repository.buscarPorFiltroEmpleado(filtroEmpleado);

        assertThat(empleadosEncontrados).isEmpty();
    }
}