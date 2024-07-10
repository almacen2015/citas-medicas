package citasmedicas.repositories;

import citasmedicas.models.entities.Area;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AreaRepositoryTest {

    @Autowired
    private AreaRepository areaRepository;

    @Test
    public void testGuardar_DadoAreaValida_RetornaAreaGuardada() {
        // Arrange
        Area area = new Area();
        area.setNombre("Medicina General");
        area.setEstado(true);

        // Act
        areaRepository.save(area);

        // Assert
        assertThat(area).isNotNull();
        assertThat(area.getId()).isGreaterThan(0);
        assertThat(area.getNombre()).isEqualTo("Medicina General");
        assertThat(area.getEstado()).isEqualTo(true);
    }

    @Test
    public void testActualizar_DadoAreaValida_RetornaAreaActualizada() {
        // Arrange
        Area area = new Area();
        area.setNombre("Medicina General");
        area.setEstado(true);
        Area areaGuardada = areaRepository.save(area);

        assertThat(areaGuardada).isNotNull();
        assertThat(areaGuardada.getId()).isGreaterThan(0);

        // Act
        area.setNombre("Medicina General Actualizada");
        area.setEstado(false);
        Area areaActualizada = areaRepository.save(area);

        // Assert
        assertThat(areaActualizada.getNombre()).isEqualTo("Medicina General Actualizada");
        assertThat(areaActualizada.getEstado()).isEqualTo(false);

    }

    @Test
    public void testListar_DadoNoParametros_RetornaListaAreas() {
        // Arrange
        Area area1 = new Area();
        area1.setNombre("Medicina General");
        area1.setEstado(true);
        areaRepository.save(area1);

        Area area2 = new Area();
        area2.setNombre("Odontología");
        area2.setEstado(true);
        areaRepository.save(area2);

        List<Area> areas = areaRepository.findAll();

        assertThat(areas.size()).isGreaterThan(1);
    }

    @Test
    public void testFindByNombre_DadoNombreArea_RetornaArea() {
        //Arrange
        Area area = new Area();
        area.setNombre("Medicina General");
        area.setEstado(true);
        areaRepository.save(area);

        //Act
        Optional<Area> areaEncontrada = areaRepository.findByNombre("Medicina General");

        //Assert
        assertThat(areaEncontrada.isPresent()).isTrue();
        assertThat(areaEncontrada.get().getNombre()).isEqualTo("Medicina General");
    }

}