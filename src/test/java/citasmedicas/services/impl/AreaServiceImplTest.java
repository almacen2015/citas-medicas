package citasmedicas.services.impl;

import citasmedicas.exceptions.AreaException;
import citasmedicas.models.dto.AreaDTO;
import citasmedicas.models.entities.Area;
import citasmedicas.repositories.AreaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AreaServiceImplTest {
    @Mock
    private AreaRepository repository;

    @InjectMocks
    private AreaServiceImpl service;

    @Test
    public void testGuardar_DadoAreaValida_RetornaAreaDtoGuardada() {
        // Arrange
        Area area = new Area();
        area.setId(1);
        area.setNombre("Medicina General");
        area.setEstado(true);

        AreaDTO areaDTO = new AreaDTO(1, "Medicina General", true);

        // Act
        when(repository.save(area)).thenReturn(area);
        AreaDTO areaDTOGuardada = service.guardar(areaDTO);

        // Assert
        assertThat(areaDTOGuardada).isNotNull();
        assertThat(areaDTOGuardada.id()).isGreaterThan(0);
        assertThat(areaDTOGuardada.nombre()).isEqualTo("Medicina General");

    }

    @Test
    public void testGuardar_DadoAreaNombreVacio_RetornaError() {
        //Arrange
        AreaDTO areaDTO = new AreaDTO(1, "", true);

        //Act & Assert
        assertThrows(AreaException.class, () -> service.guardar(areaDTO));
    }

    @Test
    public void testGuardar_DadoAreaNombreCaracterMayorA255_RetornaError() {
        //Arrange
        AreaDTO areaDTO = new AreaDTO(1, "a".repeat(256), true);

        //Act & Assert
        assertThrows(AreaException.class, () -> service.guardar(areaDTO));
    }

    @Test
    public void testGuardar_DadoAreaNombreExistente_RetornaError() {
        //Arrange
        Area area = new Area();
        area.setId(1);
        area.setNombre("Medicina General");
        area.setEstado(true);

        AreaDTO areaDTO = new AreaDTO(1, "Medicina General", true);

        when(repository.findByNombre(areaDTO.nombre())).thenReturn(Optional.of(area));

        //Act & Assert
        assertThrows(AreaException.class, () -> service.guardar(areaDTO));
    }

    @Test
    public void testObtenerPorNombre_DadoNombreExistente_RetornaAreaDTO() {
        //Arrange
        Area area = new Area();
        area.setId(1);
        area.setNombre("Medicina General");
        area.setEstado(true);

        AreaDTO areaDTO = new AreaDTO(1, "Medicina General", true);

        when(repository.findByNombre(area.getNombre())).thenReturn(Optional.of(area));

        //Act
        AreaDTO areaDtoEncontrado = service.obtenerPorNombre(areaDTO.nombre());

        //Assert
        assertThat(areaDtoEncontrado).isNotNull();
        assertThat(areaDtoEncontrado.id()).isEqualTo(1);
        assertThat(areaDtoEncontrado.nombre()).isEqualTo("Medicina General");
    }

    @Test
    public void testObtenerPorNombre_DadoNombreNoExiste_RetornaVacio() {
        //Arrange
        String nombre = "Carpinteria";

        when(repository.findByNombre(nombre)).thenReturn(Optional.empty());

        //Act
        AreaDTO areaDTOEncontrada = service.obtenerPorNombre(nombre);

        //Assert
        assertThat(areaDTOEncontrada).isNull();
    }

    @Test
    public void testObtenerPorId_DadoIdExistente_RetornaAreaDTO() {
        //Arrange
        Area area = new Area();
        area.setId(1);
        area.setNombre("Medicina General");
        area.setEstado(true);

        final int id = 1;

        when(repository.findById(id)).thenReturn(Optional.of(area));

        //Act
        AreaDTO areaDTOEncontrada = service.obtenerPorId(id);

        //Assert
        assertThat(areaDTOEncontrada).isNotNull();

    }

    @Test
    public void testObtenerPorId_DadoIdNoExiste_RetornaVacio() {
        //Arrange
        final int id = 500;

        when(repository.findById(id)).thenReturn(Optional.empty());

        //Act
        AreaDTO areaDTOEncontrada = service.obtenerPorId(id);

        //Assert
        assertThat(areaDTOEncontrada).isNull();
    }

    @Test
    public void testObtenerPorId_DadoIdVacio_RetornaError() {
        //Arrange
        Integer id = null;

        //Act & Assert
        assertThrows(AreaException.class, () -> service.obtenerPorId(id));
    }

    @Test
    public void testObtenerPorId_DadoIdMenorOIgualCero_RetornaError() {
        //Arrange
        final int id = -1;

        //Act & Assert
        assertThrows(AreaException.class, () -> service.obtenerPorId(id));
    }
}