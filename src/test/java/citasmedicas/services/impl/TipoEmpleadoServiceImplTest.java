package citasmedicas.services.impl;

import citasmedicas.exceptions.TipoEmpleadoException;
import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.models.entities.TipoEmpleado;
import citasmedicas.repositories.TipoEmpleadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TipoEmpleadoServiceImplTest {

    @Mock
    private TipoEmpleadoRepository repository;

    @InjectMocks
    private TipoEmpleadoServiceImpl service;

    @Test
    public void testListar_DadoSinParametros_RetonarListaTipoEmpleadoDTO() {
        // Arrange
        TipoEmpleado tipoEmpleado = new TipoEmpleado("Medico General");
        TipoEmpleado tipoEmpleado2 = new TipoEmpleado("Traumatologo");
        TipoEmpleado tipoEmpleado3 = new TipoEmpleado("Enfermero");
        List<TipoEmpleado> listaEmpleados = Arrays.asList(tipoEmpleado, tipoEmpleado2, tipoEmpleado3);

        when(repository.findAll()).thenReturn(listaEmpleados);

        // Act
        List<TipoEmpleadoDTO> tipoEmpleados = service.listar();

        // Assert
        assertThat(tipoEmpleados).isNotEmpty();
        assertThat(tipoEmpleados.size()).isGreaterThan(1);
    }

    @Test
    public void testGuardar_DadoTipoEmpleadoValido_RetornaTipoEmpleado() {
        //Arrange
        TipoEmpleado tipoEmpleado = new TipoEmpleado();
        tipoEmpleado.setId(1);
        tipoEmpleado.setNombre("Enfermera");
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(1, "Enfermera");

        //Act
        when(repository.save(tipoEmpleado)).thenReturn(tipoEmpleado);
        TipoEmpleadoDTO empleadoGuardado = service.guardar(tipoEmpleadoDTO);

        //Assert
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.id()).isGreaterThan(0);
        assertThat(empleadoGuardado.nombre()).isEqualTo("Enfermera");

    }

    @Test
    public void testGuardar_DadoNombreTipoEmpleadoVacio_RetornaError() {
        //Arrange
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(1, "");

        //Assert
        assertThrows(TipoEmpleadoException.class, () -> service.guardar(tipoEmpleadoDTO));

    }

    @Test
    public void testGuardar_DadoNombreTipoEmpleadoNull_RetornaError() {
        //Arrange
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(1, null);

        //Assert
        assertThrows(TipoEmpleadoException.class,()-> service.guardar(tipoEmpleadoDTO));
    }

    @Test
    public void testGuardar_DadoNombreTipoEmpleadoExiste_RetornaError() {
        // Arrange
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(null, "Enfermera");

        TipoEmpleado tipoEmpleado = new TipoEmpleado();
        tipoEmpleado.setId(1);
        tipoEmpleado.setNombre("Enfermera");

        //Act
        when(repository.findByNombre(tipoEmpleado.getNombre())).thenReturn(Optional.of(tipoEmpleado));

        //Assert
        assertThrows(TipoEmpleadoException.class, ()-> service.guardar(tipoEmpleadoDTO));

    }
}