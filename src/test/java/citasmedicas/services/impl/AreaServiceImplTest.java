package citasmedicas.services.impl;

import citasmedicas.models.mappers.AreaMapper;
import citasmedicas.models.entities.Area;
import citasmedicas.repositories.AreaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AreaServiceImplTest {
    @Mock
    private AreaRepository repository;
    @Mock
    private AreaMapper areaMapper = AreaMapper.INSTANCE;
    @InjectMocks
    private AreaServiceImpl service;
    private static List<Area> areasMock;

    @BeforeEach
    public void setUp() {
        areasMock = new ArrayList<>();
        areasMock.add(new Area(1, "Traumatologia"));
        areasMock.add(new Area(2, "Odontologia"));
        areasMock.add(new Area(3, "Obstetricia"));
        areasMock.add(new Area(4, "Enfermeria"));
        areasMock.add(new Area(5, "Cardiologia"));
        areasMock.add(new Area(6, "Medicina"));
        areasMock.add(new Area(7, "Pediatria"));
        areasMock.add(new Area(8, "Nueva Pediatria"));
    }

    public AreaServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("deberiaListarAreas")
    @Test
    public void testListar() {
        when(repository.findAll()).thenReturn(areasMock);
        service.listar();
        verify(repository).findAll();
        assertEquals(areasMock.size(), service.listar().size());
    }

    /*

    @DisplayName("deberiaGuardarConDatosCorrectos")
    @Test
    public void deberiaGuardarConDatosCorrectos() {
        Area areaMock = new Area(9, "Urologia");
        AreaDTO areaDTOMock = new AreaDTO();
        areaDTOMock.setId(9);
        areaDTOMock.setNombre("Urologia");

        when(modelMapper.map(areaDTOMock, Area.class)).thenReturn(areaMock);
        when(repository.save(areaMock)).thenReturn(areaMock);
        when(modelMapper.map(areaMock, AreaDTO.class)).thenReturn(areaDTOMock);

        AreaDTO result = service.guardar(areaDTOMock);

        verify(modelMapper).map(areaDTOMock, Area.class);
        verify(modelMapper).map(areaMock, AreaDTO.class);
        verify(repository).save(areaMock);

        assertEquals(areaDTOMock, result);
    }

    @DisplayName("deberiaLanzarErrorCuandoNombreAreaVacio")
    @Test
    public void deberiaLanzarErrorCuandoNombreAreaVacioTest() {
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setNombre("");

        assertThrows(AreaException.class, () -> service.guardar(areaDTO));
    }

    @DisplayName("deberiaLanzarErrorCuandoNombreAreaExiste")
    @Test
    public void deberiaLanzarErrorCuandoNombreAreaExisteTest() {
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setId(4);
        areaDTO.setNombre("Enfermeria");

        Optional<Area> area = Optional.of(new Area(4, "Enfermeria"));

        when(repository.findByNombre(areaDTO.getNombre())).thenReturn(area);
        when(modelMapper.map(area, AreaDTO.class)).thenReturn(areaDTO);

        assertThrows(AreaException.class, () -> service.guardar(areaDTO));
    }

    @DisplayName("deberiaObtenerDatoAreaFiltradoPorNombre")
    @Test
    public void deberiaObtenerDatoAreaFiltradoPorNombre() {
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setId(4);
        areaDTO.setNombre("Enfermeria");

        Optional<Area> area = Optional.of(new Area(4, "Enfermeria"));

        Optional<AreaDTO> expect = Optional.of(areaDTO);

        when(repository.findByNombre(areaDTO.getNombre())).thenReturn(area);
        when(modelMapper.map(area, AreaDTO.class)).thenReturn(areaDTO);

        Optional<AreaDTO> result = service.obtenerPorNombre(areaDTO.getNombre());

        verify(repository).findByNombre(areaDTO.getNombre());
        verify(modelMapper).map(area, AreaDTO.class);

        assertEquals(expect, result);
    }

    @DisplayName("deberiaObtenerNullAreaPorNombre")
    @Test
    public void deberiaObtenerNullAreaPorNombre() {
        String nombre = "aaa";

        when(repository.findByNombre(nombre)).thenReturn(Optional.empty());
        when(modelMapper.map(Optional.empty(), AreaDTO.class)).thenReturn(null);

        Optional<AreaDTO> result = service.obtenerPorNombre(nombre);

        verify(repository).findByNombre(nombre);
        verify(modelMapper).map(Optional.empty(), AreaDTO.class);

        assertFalse(result.isPresent());
    }

    @DisplayName("deberiaObtenerAreaPorId")
    @Test
    public void deberiaObtenerAreaPorId() {
        Integer id = 1;
        Optional<Area> areaEncontrada = Optional.of(areasMock.stream()
                .filter(a -> a.getId().equals(id)).findFirst().get());
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setId(1);
        areaDTO.setNombre("Traumatologia");

        when(repository.findById(id)).thenReturn(areaEncontrada);
        when(modelMapper.map(areaEncontrada, AreaDTO.class)).thenReturn(areaDTO);

        Optional<AreaDTO> result = service.obtenerPorId(id);

        verify(repository, times(1)).findById(id);

        assertEquals(areaDTO.getId(), result.get().getId());
        assertEquals(areaDTO.getNombre(), result.get().getNombre());
    }

    @DisplayName("deberiaObtenerAreaVacioCuandoIdNoExiste")
    @Test
    public void deberiaObtenerAreaVacioCuandoIdNoExiste() {
        Integer id = 99999;

        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<AreaDTO> result = service.obtenerPorId(id);

        verify(repository).findById(id);

        assertFalse(result.isPresent());
    }
     */
}