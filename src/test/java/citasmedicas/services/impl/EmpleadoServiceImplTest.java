package citasmedicas.services.impl;

import citasmedicas.exceptions.EmpleadoException;
import citasmedicas.models.dto.EmpleadoDTO;
import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.models.entities.Empleado;
import citasmedicas.models.entities.TipoEmpleado;
import citasmedicas.repositories.EmpleadoRepository;
import citasmedicas.repositories.TipoEmpleadoRepository;
import citasmedicas.repositories.filtros.FiltroEmpleado;
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
    public void testActualizarEmpleado_DadoEmpleadoValido_RetornaEmpleado() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "11111111",
                new TipoEmpleadoDTO(1, "Médico"),
                true);

        Empleado empleado = new Empleado();
        empleado.setId(1);
        empleado.setNombre("Victor");
        empleado.setApellidoPaterno("Orbegozo");
        empleado.setApellidoMaterno("Percovich");
        empleado.setNumeroDocumento("11111111");
        empleado.setTipoEmpleado(new TipoEmpleado(1, "Médico"));
        empleado.setEstado(true);

        final Integer id = empleadoDTO.id();
        final String numeroDocumento = empleadoDTO.numeroDocumento();

        when(repository.findByNumeroDocumentoAndIdNot(numeroDocumento, id)).thenReturn(Optional.empty());
        when(repository.save(empleado)).thenReturn(empleado);

        EmpleadoDTO empleadoActualizado = service.actualizar(empleadoDTO);

        assertThat(empleadoActualizado).isNotNull();
        assertThat(empleadoActualizado.nombre()).isEqualTo("Victor");
        assertThat(empleadoActualizado.apellidoPaterno()).isEqualTo("Orbegozo");
        assertThat(empleadoActualizado.apellidoMaterno()).isEqualTo("Percovich");
        assertThat(empleadoActualizado.numeroDocumento()).isEqualTo("11111111");
    }

    @Test
    public void testActualizarEmpleado_DadoNumeroDocumentoExiste_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Médico"),
                true);

        final String numeroDocumento = empleadoDTO.numeroDocumento();
        final Integer id = empleadoDTO.id();

        when(repository.findByNumeroDocumentoAndIdNot(numeroDocumento, id)).thenReturn(Optional.of(empleado));

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoEstadoEsNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Médico"),
                null);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoIdTipoEmpleadoEsMenor0_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(-1, "Médico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoIdTipoEmpleadoEsIgual0_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(0, "Médico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoIdTipoEmpleadoEsNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(null, "Médico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoTipoEmpleadoEsNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                null,
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoNumeroDocumentoMayor8Caracteres_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "123456789",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoNumeroDocumentoTieneEspaciosBlanco_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "   ",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoNumeroDocumentoVacio_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoNumeroDocumentoEsNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                null,
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoApellidoMaternoTieneEspaciosBlanco_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "    ",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoApellidoMaternoEsVacio_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoApellidoMaternoEsNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                null,
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoApellidoMaternoMayor255Caracteres_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "a".repeat(256),
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoApellidoPaternoMayor255Caracteres_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "a".repeat(256),
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoApellidoPaternoTieneEspaciosBlanco_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "   ",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                null);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoApellidoPaternoVacio_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoApellidoPaternoEsNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                null,
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoIdEsMenor0_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                -1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoIdEsIgual0_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                0,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoIdNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoNombreTieneEspaciosBlanco_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "     ",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoNombreMayor255Caracteres_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "a".repeat(256),
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoNombreVacio_RetornarError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoNombreNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                null,
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.actualizar(empleadoDTO));
    }

    @Test
    public void testActualizarEmpleado_DadoEmpleadoNulo_RetornaError() {
        assertThrows(EmpleadoException.class, () -> service.actualizar(null));
    }

    @Test
    public void testGuardarEmpleado_DadoEstadoNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                null);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoTipoEmpleadoIdNulo_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(null, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoTipoEmpleadoNulo_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                null,
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoNumeroDocumentoEspaciosBlanco_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                "      ",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoNumeroDocumentoNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                null,
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoNumeroDocumentoMayor8Caracteres_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                "123456789",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoNumeroDocumentoVacio_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                "",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoApellidoMaternoEspaciosBlanco_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "    ",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoApellidoMaternoNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                null,
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoApellidoMaternoVacio_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoApellidoMaternoMayor255Caracteres_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "a".repeat(256),
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpelado_DadoApellidoPaternoMayor255Caracteres_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "a".repeat(256),
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoApellidoPaternoEspaciosBlanco_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "    ",
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void TestGuardarEmpleado_DadoApellidoPaternoVacio_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "",
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoApellidoPaternoNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                null,
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoNombreMayor255Caracteres_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "a".repeat(256),
                "Orbegozo",
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoNombreEspaciosBlanco_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "                         ",
                "Orbegozo",
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoNombreNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                null,
                "Orbegozo",
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoNombreVacio_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "",
                "Orbegozo",
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoIdDiferenteNull_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                1,
                "Victor",
                "Orbegozo",
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoEmpleadoConNumeroDocumentoExistente_RetornaError() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                "12345678",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        when(repository.findByNumeroDocumento(empleadoDTO.numeroDocumento())).thenReturn(Optional.of(empleado));

        assertThrows(EmpleadoException.class, () -> service.guardar(empleadoDTO));
    }

    @Test
    public void testGuardarEmpleado_DadoEmpleadoNulo_RetornaError() {
        assertThrows(EmpleadoException.class, () -> service.guardar(null));
    }

    @Test
    public void testGuardarEmpleado_DadoEmpleadoValido_RetornaEmpleado() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(
                null,
                "Victor",
                "Orbegozo",
                "Percovich",
                "70553916",
                new TipoEmpleadoDTO(1, "Medico"),
                true);

        when(repository.findByNumeroDocumento(empleadoDTO.numeroDocumento())).thenReturn(Optional.empty());

        Empleado empleado = new Empleado();
        empleado.setNombre("Victor");
        empleado.setApellidoPaterno("Orbegozo");
        empleado.setApellidoMaterno("Percovich");
        empleado.setNumeroDocumento("70553916");
        empleado.setTipoEmpleado(new TipoEmpleado(1, "Medico"));
        empleado.setEstado(true);

        when(repository.save(empleado)).thenReturn(empleado);

        EmpleadoDTO empleadoGuardado = service.guardar(empleadoDTO);

        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.nombre()).isEqualTo("Victor");
        assertThat(empleadoGuardado.apellidoPaterno()).isEqualTo("Orbegozo");
        assertThat(empleadoGuardado.apellidoMaterno()).isEqualTo("Percovich");
        assertThat(empleadoGuardado.numeroDocumento()).isEqualTo("70553916");
        assertThat(empleadoGuardado.tipoEmpleadoDTO().nombre()).isEqualTo("Medico");
    }


    @Test
    public void testListarEmpleado_DadoFiltroSoloNombreYApellidoMaterno_RetornaEmpleado() {
        FiltroEmpleado filtro = new FiltroEmpleado("Juan", "", "Gómez", "", true);
        when(repository.buscarPorFiltroEmpleado(filtro, paginado)).thenReturn(Arrays.asList(empleado));

        List<EmpleadoDTO> empleados = service.listarEmpleados(filtro, 0, 10);

        assertThat(empleados).isNotEmpty();
    }

    @Test
    public void testListarEmpleado_DadoFiltroSoloNombreYApellidoPaterno_RetornaEmpleado() {
        FiltroEmpleado filtro = new FiltroEmpleado("Juan", "Pérez", "", "", true);
        when(repository.buscarPorFiltroEmpleado(filtro, paginado)).thenReturn(Arrays.asList(empleado));

        List<EmpleadoDTO> empleados = service.listarEmpleados(filtro, 0, 10);

        assertThat(empleados).isNotEmpty();
    }

    @Test
    public void testListarEmpleado_DadoFiltroSoloNumeroDocumento_RetornaEmpleado() {
        FiltroEmpleado filtro = new FiltroEmpleado("", "", "", "12345678", true);
        when(repository.buscarPorFiltroEmpleado(filtro, paginado)).thenReturn(Arrays.asList(empleado));

        List<EmpleadoDTO> empleados = service.listarEmpleados(filtro, 0, 10);

        assertThat(empleados).isNotEmpty();
    }

    @Test
    public void testListarEmpleado_DadoFiltroSoloApellidoMaterno_RetornaEmpleado() {
        FiltroEmpleado filtro = new FiltroEmpleado("", "", "Gómez", "", true);
        when(repository.buscarPorFiltroEmpleado(filtro, paginado)).thenReturn(Arrays.asList(empleado));

        List<EmpleadoDTO> empleados = service.listarEmpleados(filtro, 0, 10);

        assertThat(empleados).isNotEmpty();
    }

    @Test
    public void testListarEmpleado_DadoFiltroSoloApellidoPaterno_RetornaEmpleado() {
        FiltroEmpleado filtro = new FiltroEmpleado("", "Pérez", "", "", true);
        when(repository.buscarPorFiltroEmpleado(filtro, paginado)).thenReturn(Arrays.asList(empleado));

        List<EmpleadoDTO> empleados = service.listarEmpleados(filtro, 0, 10);

        assertThat(empleados).isNotEmpty();
    }

    @Test
    public void testListarEmpleado_DadoFiltroSoloNombre_RetornaEmpleado() {
        FiltroEmpleado filtro = new FiltroEmpleado("Juan", "", "", "", true);
        when(repository.buscarPorFiltroEmpleado(filtro, paginado)).thenReturn(Arrays.asList(empleado));

        List<EmpleadoDTO> empleados = service.listarEmpleados(filtro, 0, 10);

        assertThat(empleados).isNotEmpty();
    }

    @Test
    public void testListarEmpleado_DadoFiltroVacio_RetornaVacio() {
        FiltroEmpleado filtro = new FiltroEmpleado("", "", "", "", true);
        List<EmpleadoDTO> empleados = service.listarEmpleados(filtro, 0, 10);
        assertThat(empleados).isEmpty();
    }

    @Test
    public void testListarEmpleado_DadoFiltroNulo_RetornaVacio() {
        List<EmpleadoDTO> empleados = service.listarEmpleados(null, 0, 10);
        assertThat(empleados).isEmpty();
    }

    @Test
    public void testListarEmpleado_DadoCantidadDatosIgual0_RetornaError() {
        FiltroEmpleado filtro = new FiltroEmpleado("Juan", "Pérez", "Gómez", "12345678", true);
        assertThrows(EmpleadoException.class, () -> service.listarEmpleados(filtro, 0, 0));
    }

    @Test
    public void testListarEmpleado_DadoCantidadDatosMenor0_RetornaError() {
        FiltroEmpleado filtro = new FiltroEmpleado("Juan", "Pérez", "Gómez", "12345678", true);
        assertThrows(EmpleadoException.class, () -> service.listarEmpleados(filtro, 0, -1));
    }

    @Test
    public void testListarEmpleado_DadoPaginaInicioMenor0_RetornaError() {
        FiltroEmpleado filtro = new FiltroEmpleado("Juan", "Pérez", "Gómez", "12345678", true);
        assertThrows(EmpleadoException.class, () -> service.listarEmpleados(filtro, -1, 10));
    }

    @Test
    public void testListarEmpleado_DadoFiltroValido_RetornaEmpleado() {
        FiltroEmpleado filtro = new FiltroEmpleado("Juan", "Pérez", "Gómez", "12345678", true);
        when(repository.buscarPorFiltroEmpleado(filtro, paginado)).thenReturn(Arrays.asList(empleado));

        List<EmpleadoDTO> empleados = service.listarEmpleados(filtro, 0, 10);

        assertThat(empleados).isNotEmpty();
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