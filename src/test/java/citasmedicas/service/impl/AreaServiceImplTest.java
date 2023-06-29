package citasmedicas.service.impl;

import citasmedicas.model.Area;
import citasmedicas.model.dto.AreaDTO;
import citasmedicas.repository.AreaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AreaServiceImplTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AreaRepository repository;

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

    @Test
    public void listarTest() {
        when(repository.findAll()).thenReturn(areasMock);

        List<AreaDTO> areas = service.listar();

        verify(repository, times(1)).findAll();

        assertEquals(areasMock.size(), areas.size());
    }

    @Test
    void guardarTest() {
        Area areaMock = new Area();
        areaMock.setId(9);
        areaMock.setNombre("Urologia");

    }
}