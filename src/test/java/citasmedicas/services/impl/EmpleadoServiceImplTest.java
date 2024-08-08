package citasmedicas.services.impl;

import citasmedicas.exceptions.EmpleadoException;
import citasmedicas.models.dto.EmpleadoDTO;
import citasmedicas.models.entities.Empleado;
import citasmedicas.models.entities.TipoEmpleado;
import citasmedicas.repositories.EmpleadoRepository;
import citasmedicas.repositories.TipoEmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository repository;

    @Mock
    private TipoEmpleadoRepository tipoEmpleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl service;

    Empleado empleado;
    Empleado empleado2;

    Pageable paginado;

    @BeforeEach
    void setUp() {
        TipoEmpleado tipoEmpleado = new TipoEmpleado(null, "Médico");
        TipoEmpleado tipoEmpleado2 = new TipoEmpleado(null, "Enfermero");

        tipoEmpleadoRepository.save(tipoEmpleado);
        tipoEmpleadoRepository.save(tipoEmpleado2);

        empleado = new Empleado();
        empleado.setId(null);
        empleado.setNombre("Juan");
        empleado.setApellidoPaterno("Pérez");
        empleado.setApellidoMaterno("Gómez");
        empleado.setNumeroDocumento("12345678");
        empleado.setTipoEmpleado(tipoEmpleado);
        empleado.setEstado(true);

        empleado2 = new Empleado();
        empleado2.setId(null);
        empleado2.setNombre("María");
        empleado2.setApellidoPaterno("González");
        empleado2.setApellidoMaterno("Pérez");
        empleado2.setNumeroDocumento("87654321");
        empleado2.setTipoEmpleado(tipoEmpleado2);
        empleado2.setEstado(false);

        paginado = PageRequest.of(0, 10);

        repository.save(empleado);
        repository.save(empleado2);
    }

    @Test
    public void testListarPorEstado_DadoPaginaInicioMenor0_RetornaError() {
        assertThrows(EmpleadoException.class, () -> service.listarPorEstado(true, -1, 10));
    }

    @Test
    public void testListarPorEstado_DadoCantidadDatosMenor0_RetornaError() {
        assertThrows(EmpleadoException.class, () -> service.listarPorEstado(true, 0, -1));
    }

    @Test
    public void testListarPorEstado_DadoCantidadDatosIgual0_RetornaError() {
        assertThrows(EmpleadoException.class, () -> service.listarPorEstado(true, 0, 0));
    }

    @Test
    public void testListarPorEstado_DadoEstadoEsTrue_RetornaEmpleados() {
        when(repository.findAllByEstado(true, paginado)).thenReturn(Arrays.asList(empleado));

        List<EmpleadoDTO> empleados = service.listarPorEstado(true, 0, 10);

        assertThat(empleados).isNotEmpty();
        assertThat(empleados.size()).isGreaterThan(0);
    }

    @Test
    public void testListarPorEstado_DadoEstadoEsFalse_RetornaEmpleados() {
        when(repository.findAllByEstado(true, paginado)).thenReturn(Arrays.asList(empleado2));

        List<EmpleadoDTO> empleados = service.listarPorEstado(true, 0, 10);

        assertThat(empleados).isNotEmpty();
        assertThat(empleados.size()).isGreaterThan(0);
    }

    @Test
    public void testBuscarPorId_DadoIdNoEncontrado_RetornaError() {
        final int id = 1000;
        assertThrows(EmpleadoException.class, () -> service.buscarPorId(id));
    }

    @Test
    public void testBuscarPorId_DadoIdEsIgualO_RetornaError() {
        final int id = 0;
        assertThrows(EmpleadoException.class, () -> service.buscarPorId(id));
    }

    @Test
    public void testBuscarPorId_DadoIdEsMenorO_RetornaError() {
        final int id = -1;
        assertThrows(EmpleadoException.class, () -> service.buscarPorId(id));
    }

    @Test
    public void testBuscarPorId_DadoIdEsNull_RetornaError() {
        assertThrows(EmpleadoException.class, () -> service.buscarPorId(null));
    }

    @Test
    public void testBuscarPorId_DadoIdValido_RetornaEmpleado() {
        TipoEmpleado tipoEmpleado = new TipoEmpleado(1, "Medico");
        Empleado empleado = Empleado.builder()
                .id(1)
                .nombre("Juan")
                .apellidoPaterno("Pérez")
                .apellidoMaterno("Gómez")
                .numeroDocumento("12345678")
                .tipoEmpleado(tipoEmpleado)
                .build();

        final Integer id = 1;

        when(repository.findById(id)).thenReturn(Optional.of(empleado));

        EmpleadoDTO empleadoDTO = service.buscarPorId(id);

        assertThat(empleadoDTO).isNotNull();
        assertThat(empleadoDTO.nombre()).isEqualTo("Juan");
        assertThat(empleadoDTO.id()).isEqualTo(1);
    }

    @Test
    public void testBuscarPorNumeroDocumento_DadoNumeroDocumentoValido_RetornaEmpleado() {
        TipoEmpleado tipoEmpleado = new TipoEmpleado(1, "Medico");
        Empleado empleado = Empleado.builder()
                .id(1)
                .nombre("Juan")
                .apellidoPaterno("Pérez")
                .apellidoMaterno("Gómez")
                .numeroDocumento("12345678")
                .tipoEmpleado(tipoEmpleado)
                .build();

        final String numeroDocumento = "12345678";

        when(repository.findByNumeroDocumento(numeroDocumento)).thenReturn(Optional.of(empleado));

        EmpleadoDTO empleadoDTO = service.buscarPorNumeroDocumento(numeroDocumento);

        assertThat(empleadoDTO).isNotNull();
    }

    @Test
    public void testBuscarPorNumeroDocumento_DadoNumeroDocumentoVacio_RetornaError() {
        String numeroDocumento = "";

        assertThrows(EmpleadoException.class,
                () -> service.buscarPorNumeroDocumento(numeroDocumento));
    }

    @Test
    public void testBuscarPorNumeroDocumento_DadoNumeroDocumentoEsNull_RetornaError() {
        assertThrows(EmpleadoException.class, () -> service.buscarPorNumeroDocumento(null));
    }

    @Test
    public void testBuscarPorNumeroDocumento_DadoNumeroDocumentoEspaciosBlanco_RetornaError() {
        final String numeroDocumento = "     ";
        assertThrows(EmpleadoException.class,
                () -> service.buscarPorNumeroDocumento(numeroDocumento));
    }

    @Test
    public void testBuscarPorNumeroDocumento_DadoNumeroDocumentoCaracteresMayor8_RetornaError() {
        final String numeroDocumento = "123456789";
        assertThrows(EmpleadoException.class,
                () -> service.buscarPorNumeroDocumento(numeroDocumento));
    }

    @Test
    public void testBuscarPorNumeroDocumento_DadoNumeroDocumentoCaracteresMenor8_RetornaEmpleadoNoEncontrado() {
        final String numeroDocumento = "123456";
        EmpleadoException exception = assertThrows(EmpleadoException.class,
                () -> service.buscarPorNumeroDocumento(numeroDocumento));

        assertThat("EMPLEADO_NO_ENCONTRADO").isEqualTo(exception.getMessage());
    }
}