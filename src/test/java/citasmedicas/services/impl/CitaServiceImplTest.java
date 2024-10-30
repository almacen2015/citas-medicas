package citasmedicas.services.impl;

import citasmedicas.exceptions.AreaException;
import citasmedicas.exceptions.CitaException;
import citasmedicas.models.dto.AreaDTO;
import citasmedicas.models.dto.CitaDTO;
import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Area;
import citasmedicas.models.entities.Cita;
import citasmedicas.models.entities.Cliente;
import citasmedicas.models.mappers.AreaMapper;
import citasmedicas.models.mappers.CitaMapper;
import citasmedicas.models.mappers.ClienteMapper;
import citasmedicas.repositories.CitaRepository;
import citasmedicas.services.AreaService;
import citasmedicas.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CitaServiceImplTest {

    @Mock
    private CitaRepository repository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private AreaService areaService;

    @InjectMocks
    private CitaServiceImpl service;

    Cliente cliente1;
    Cliente cliente2;

    Cita cita1;
    Cita cita2;

    Area area1;
    Area area2;

    private AreaMapper areaMapper = AreaMapper.INSTANCE;
    private ClienteMapper clienteMapper = ClienteMapper.INSTANCE;
    private CitaMapper citaMapper = CitaMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNombre("Juan");
        cliente1.setApellidoPaterno("Perez");
        cliente1.setApellidoMaterno("Gomez");
        cliente1.setNumeroDocumento("12345678");
        cliente1.setFechaNacimiento(LocalDate.of(1996, Calendar.NOVEMBER, 10));
        cliente1.setSexo("M");
        cliente1.setTelefono("123456789");
        cliente1.setEmail("vorbegozop@gmail.com");

        cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNombre("Maria");
        cliente2.setApellidoPaterno("Torres");
        cliente2.setApellidoMaterno("Sanchez");
        cliente2.setNumeroDocumento("87654321");
        cliente2.setFechaNacimiento(LocalDate.of(1995, Calendar.FEBRUARY, 1));
        cliente2.setSexo("F");
        cliente2.setTelefono("987654321");
        cliente2.setEmail("maria@gmail.com");

        area1 = new Area(1, "Medicina", true);
        area2 = new Area(2, "Odontologia", true);


        cita1 = new Cita();
        cita1.setId(1);
        cita1.setCliente(cliente1);
        cita1.setFechaInicio(LocalDateTime.of(2024, 10, 12, 12, 0, 0));
        cita1.setFechaFin(LocalDateTime.of(2024, 10, 12, 13, 0, 0));
        cita1.setTitulo("Medicina");
        cita1.setEstado("A");
        cita1.setArea(area1);

        cita2 = new Cita();
        cita2.setId(2);
        cita2.setCliente(cliente2);
        cita2.setFechaFin(LocalDateTime.now());
        cita2.setFechaInicio(LocalDateTime.now());
        cita2.setTitulo("Obtetricia");
        cita2.setEstado("A");
        cita2.setArea(area2);

        repository.save(cita1);
        repository.save(cita2);
    }

    @Test
    public void testGuardarCita_DadoFechaInicioEsMismo_RetornaError() {
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area1);
        ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente2);
        CitaDTO citaDTO = new CitaDTO(1, clienteDTO, areaDTO, "Hola", "2024-11-11 09:00:00", "2024-10-12 13:00:00", "A");

        Cita cita = citaMapper.citaDTOToCita(citaDTO);

        when(repository.findByClienteIdAndAreaIdAndFechaInicio(clienteDTO.id(), areaDTO.id(), cita.getFechaInicio())).thenReturn(Arrays.asList(cita));

        assertThrows(CitaException.class, () -> service.guardar(citaDTO));

    }

    @Test
    public void testGuardarCita_DadoAreaNoValida_RetornaError() {
        area1.setId(null);
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area1);
        ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente1);

        when(areaService.obtenerPorId(null)).thenThrow(AreaException.class);

        CitaDTO citaDTO = new CitaDTO(1, clienteDTO, areaDTO, "Hola", "2024-10-12 12:00:00", "2024-10-12 13:00:00", "A");

        assertThrows(AreaException.class, () -> service.guardar(citaDTO));

        verify(areaService).obtenerPorId(null);
    }

    @Test
    public void testGuardarCita_DadoFechaFinNoValido_RetornaError() {
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area1);
        ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente1);
        CitaDTO citaDTO = new CitaDTO(1, clienteDTO, areaDTO, "Hola", "2024-10-12 12:00:00", "2024-40-12 13:00:00", "A");

        assertThrows(DateTimeParseException.class, () -> service.guardar(citaDTO));
    }

    @Test
    public void testGuardarCita_DadoFechaInicioNoValido_RetornaError() {
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area1);
        ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente1);
        CitaDTO citaDTO = new CitaDTO(1, clienteDTO, areaDTO, "Hola", "2024-20-40 12:00:00", "2024-10-12 13:00:00", "A");

        assertThrows(DateTimeParseException.class, () -> service.guardar(citaDTO));
    }

    @Test
    public void testGuardarCita_DadoTituloEsMayor255Caracteres_RetornaError() {
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area1);
        ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente1);
        CitaDTO citaDTO = new CitaDTO(1, clienteDTO, areaDTO, "a".repeat(256), "2024-10-12 12:00:00", "2024-10-12 13:00:00", "A");

        assertThrows(CitaException.class, () -> service.guardar(citaDTO));
    }

    @Test
    public void testGuardarCita_DadoTituloEspaciosVacios_RetornaError() {
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area1);
        ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente1);
        CitaDTO citaDTO = new CitaDTO(1, clienteDTO, areaDTO, "     ", "2024-10-12 12:00:00", "2024-10-12 13:00:00", "A");

        assertThrows(CitaException.class, () -> service.guardar(citaDTO));
    }

    @Test
    public void testGuardarCita_DadoTituloIsBlank_RetornaError() {
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area1);
        ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente1);
        CitaDTO citaDTO = new CitaDTO(1, clienteDTO, areaDTO, "", "2024-10-12 12:00:00", "2024-10-12 13:00:00", "A");

        assertThrows(CitaException.class, () -> service.guardar(citaDTO));
    }

    @Test
    public void testGuardarCita_DadoTituloIsNull_RetornaError() {
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area1);
        ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente1);
        CitaDTO citaDTO = new CitaDTO(1, clienteDTO, areaDTO, null, "2024-10-12 12:00:00", "2024-10-12 13:00:00", "A");

        assertThrows(CitaException.class, () -> service.guardar(citaDTO));
    }

    @Test
    public void testListar_RetornaCitas() {
        when(repository.findAll()).thenReturn(Arrays.asList(cita1, cita2));

        List<CitaDTO> citas = service.listar();

        assertThat(citas).isNotEmpty();
    }

    @Test
    public void testListarCitasPorCliente_DadoIdValido_RetornaCitas() {
        int clienteId = 1;
        when(repository.findCitasByClienteId(clienteId)).thenReturn(Arrays.asList(cita1));

        List<CitaDTO> citas = service.listarCitasPorCliente(clienteId, "");

        assertThat(citas).isNotEmpty();
    }

    @Test
    public void testListarCitasPorCliente_DadoIdMenorZero_RetornaError() {
        int clienteId = -1;
        assertThrows(CitaException.class, () -> service.listarCitasPorCliente(clienteId, ""));
    }

    @Test
    public void testListarCitasPorCliente_DadoIdIgualZero_RetornaError() {
        int clienteId = 0;
        assertThrows(CitaException.class, () -> service.listarCitasPorCliente(clienteId, ""));
    }

    @Test
    public void testGuardarCita_DadoDatosValidos_RetornaCita() {
        AreaDTO areaDTO = areaMapper.areaToAreaDTO(area1);
        ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente1);

        when(repository.save(any(Cita.class))).thenReturn(cita1);

        CitaDTO citaDTO = new CitaDTO(1, clienteDTO, areaDTO, "Medicina", "2024-10-12 12:00:00", "2024-10-12 13:00:00", "A");
        CitaDTO citaGuardada = service.guardar(citaDTO);

        assertThat(citaGuardada).isNotNull();
    }
}