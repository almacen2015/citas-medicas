package citasmedicas.service.impl;

import citasmedicas.model.Area;
import citasmedicas.model.dto.AreaDTO;
import citasmedicas.repository.AreaRepository;
import org.junit.jupiter.api.BeforeAll;
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
    private AreaRepository areaRepository;

    @InjectMocks
    private AreaServiceImpl areaService;

    private static Area area= new Area();
    private static List<Area> areasMock = new ArrayList<>();

    public AreaServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void beforeAll() {

        area.setId(1);
        area.setNombre("Traumatologia");
        areasMock.add(area);

        area.setId(2);
        area.setNombre("Odontologia");
        areasMock.add(area);

        area.setId(3);
        area.setNombre("Obstetricia");
        areasMock.add(area);

        area.setId(4);
        area.setNombre("Enfermeria");
        areasMock.add(area);

        area.setId(5);
        area.setNombre("Cardiologia");
        areasMock.add(area);

        area.setId(6);
        area.setNombre("Medicina");
        areasMock.add(area);

        area.setId(7);
        area.setNombre("Pediatria");
        areasMock.add(area);

        area.setId(8);
        area.setNombre("Nuvea Pediatria");
        areasMock.add(area);
    }

    @Test
    public void listarTest() {
        when(areaRepository.findAll()).thenReturn(areasMock);

        List<AreaDTO> areas = areaService.listar();

        verify(areaRepository, times(1)).findAll();

        assertEquals(areasMock.size(), areas.size());

    }
}