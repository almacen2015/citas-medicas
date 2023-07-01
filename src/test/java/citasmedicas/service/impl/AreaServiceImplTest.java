package citasmedicas.service.impl;

import citasmedicas.exceptions.AreaException;
import citasmedicas.model.Area;
import citasmedicas.model.dto.AreaDTO;
import citasmedicas.repository.AreaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AreaServiceImplTest {
    @Mock
    private AreaRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AreaServiceImpl service;

    private static List<Area> areasMock;

    public AreaServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

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

    @DisplayName("deberiaListarAreas")
    @Test
    public void deberiaListarAreas() {
        when(repository.findAll()).thenReturn(areasMock);

        List<AreaDTO> areas = service.listar();

        verify(repository, times(1)).findAll();

        assertEquals(areasMock.size(), areas.size());
    }

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
}