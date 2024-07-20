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
import static org.junit.jupiter.api.Assertions.assertNull;
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
        assertThrows(TipoEmpleadoException.class, () -> service.guardar(tipoEmpleadoDTO));
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
        assertThrows(TipoEmpleadoException.class, () -> service.guardar(tipoEmpleadoDTO));

    }

    @Test
    public void testBuscarPorId_DadoIdTipoEmpleadoValido_RetornaTipoEmpleado() {
        TipoEmpleado tipoEmpleado = new TipoEmpleado();
        tipoEmpleado.setId(1);
        tipoEmpleado.setNombre("Enfermera");

        final int id = 1;
        when(repository.findById(id)).thenReturn(Optional.of(tipoEmpleado));

        TipoEmpleadoDTO tipoEmpleadoEncontrado = service.buscarPorId(id);

        assertThat(tipoEmpleadoEncontrado).isNotNull();
        assertThat(tipoEmpleadoEncontrado.id()).isGreaterThan(0);
        assertThat(tipoEmpleadoEncontrado.id()).isEqualTo(1);
        assertThat(tipoEmpleadoEncontrado.nombre()).isEqualTo("Enfermera");
    }

    @Test
    public void testBuscarPorId_DadoIdTipoEmpleadoNull_RetornaError() {
        assertThrows(TipoEmpleadoException.class, () -> service.buscarPorId(null));
    }

    @Test
    public void testBuscarPorId_DadoIdTipoEmpleadoZero_RetornaError() {
        final int id = 0;
        assertThrows(TipoEmpleadoException.class, () -> service.buscarPorId(id));
    }

    @Test
    public void testBuscarPorId_DadoIdTipoEmpleadoMenorZero_RetornaError() {
        final int id = -1;
        assertThrows(TipoEmpleadoException.class, () -> service.buscarPorId(id));
    }

    @Test
    public void testBuscarPorId_DadoIdTipoEmpleadoNoExiste_RetornNull() {
        final int id = 9999;
        when(repository.findById(id)).thenReturn(Optional.empty());

        TipoEmpleadoDTO tipoEmpleadoEncontrado = service.buscarPorId(id);

        assertNull(tipoEmpleadoEncontrado);
    }

    @Test
    public void testBuscarPorNombre_DadoNombreValido_RetornaTipoEmpleado() {
        TipoEmpleado tipoEmpleado = new TipoEmpleado();
        tipoEmpleado.setId(1);
        tipoEmpleado.setNombre("Enfermera");

        final String nombre = "Enfermera";

        when(repository.findByNombre(nombre)).thenReturn(Optional.of(tipoEmpleado));

        TipoEmpleadoDTO tipoEmpleadoEncontrado = service.buscarPorNombre(nombre);

        assertThat(tipoEmpleadoEncontrado).isNotNull();
        assertThat(tipoEmpleadoEncontrado.id()).isEqualTo(1);
        assertThat(tipoEmpleadoEncontrado.nombre()).isEqualTo("Enfermera");
    }

    @Test
    public void testBuscarPorNombre_DadoNombreNull_RetornaError() {
        assertThrows(TipoEmpleadoException.class, ()-> service.buscarPorNombre(null));
    }

    @Test
    public void testBuscarPorNombre_DadoNombreVacio_RetornaError() {
        final String nombre = "";
        assertThrows(TipoEmpleadoException.class, ()-> service.buscarPorNombre(nombre));
    }

    @Test
    public void testBuscarPorNombre_DadoNombreEspaciosBlanco_RetornaError() {
        final String nombre = "      ";
        assertThrows(TipoEmpleadoException.class, ()-> service.buscarPorNombre(nombre));
    }
}