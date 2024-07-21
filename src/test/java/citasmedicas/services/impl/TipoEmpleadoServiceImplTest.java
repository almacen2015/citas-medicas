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
    public void testGuardar_DadoNombreTipoEmpleadoMayor255Caracteres_RetornaError() {
        //Arrange
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(1, "a".repeat(256));

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
        assertThrows(TipoEmpleadoException.class, () -> service.buscarPorNombre(null));
    }

    @Test
    public void testBuscarPorNombre_DadoNombreVacio_RetornaError() {
        final String nombre = "";
        assertThrows(TipoEmpleadoException.class, () -> service.buscarPorNombre(nombre));
    }

    @Test
    public void testBuscarPorNombre_DadoNombreEspaciosBlanco_RetornaError() {
        final String nombre = "      ";
        assertThrows(TipoEmpleadoException.class, () -> service.buscarPorNombre(nombre));
    }

    @Test
    public void testBuscarPorNombre_DadoNombreNoExiste_RetornaError() {
        final String nombre = "TTT";

        when(repository.findByNombre(nombre)).thenReturn(Optional.empty());
        TipoEmpleadoDTO tipoEmpleadoEncontrado = service.buscarPorNombre(nombre);

        assertThat(tipoEmpleadoEncontrado).isNull();
    }

    @Test
    public void testActualizar_DadoTipoEmpleadoValido_RetornaTipoEmpleado() {
        TipoEmpleado tipoEmpleado = new TipoEmpleado(1, "Enfermera");
        TipoEmpleadoDTO tipoEmpleadoParaGuardar = new TipoEmpleadoDTO(1, "Medico");
        TipoEmpleado tipoEmpleadoGuardado = new TipoEmpleado(1, "Medico");

        when(repository.findById(tipoEmpleadoParaGuardar.id())).thenReturn(Optional.of(tipoEmpleado));
        when(repository.findByNombre(tipoEmpleadoParaGuardar.nombre())).thenReturn(Optional.empty());
        when(repository.save(tipoEmpleadoGuardado)).thenReturn(tipoEmpleadoGuardado);

        TipoEmpleadoDTO tipoEmpleadoDTOGuardado = service.actualizar(tipoEmpleadoParaGuardar);

        assertThat(tipoEmpleadoDTOGuardado).isNotNull();
        assertThat(tipoEmpleadoDTOGuardado.nombre()).isEqualTo("Medico");
        assertThat(tipoEmpleadoDTOGuardado.id()).isEqualTo(1);
    }

    @Test
    public void testActualizar_DadoIdNull_RetornaError() {
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(null, "Odontologo");
        assertThrows(TipoEmpleadoException.class, () -> service.actualizar(tipoEmpleadoDTO));
    }

    @Test
    public void testActualizar_DadoIdMenorZero_RetornaError() {
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(-1, "Odontologo");
        assertThrows(TipoEmpleadoException.class, () -> service.actualizar(tipoEmpleadoDTO));
    }

    @Test
    public void testActualizar_DadoIdIgualZero_RetornaError() {
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(0, "Odontologo");
        assertThrows(TipoEmpleadoException.class, () -> service.actualizar(tipoEmpleadoDTO));
    }

    @Test
    public void testActualizar_DadoIdNoEncontrado_RetornaError() {
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(9999, "Odontologo");
        assertThrows(TipoEmpleadoException.class, () -> service.actualizar(tipoEmpleadoDTO));
    }

    @Test
    public void testActualizar_DadoNombreNull_RetornaError() {
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(1, null);
        assertThrows(TipoEmpleadoException.class, () -> service.actualizar(tipoEmpleadoDTO));
    }

    @Test
    public void testActualizar_DadoNombreVacio_RetornaError() {
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(1, "");
        assertThrows(TipoEmpleadoException.class, () -> service.actualizar(tipoEmpleadoDTO));
    }

    @Test
    public void testActualizar_DadoNombreEspacioBlanco_RetornaError() {
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(1, "    ");
        assertThrows(TipoEmpleadoException.class, () -> service.actualizar(tipoEmpleadoDTO));
    }

    @Test
    public void testActualizar_DadoNombreMayor255Caracteres_RetornaError() {
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(1, "a".repeat(256));
        assertThrows(TipoEmpleadoException.class, () -> service.actualizar(tipoEmpleadoDTO));
    }

    @Test
    public void testActualizar_DadoNombreEncontrado_RetornaError() {
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO(1, "Traumatólogo");
        TipoEmpleado tipoEmpleadoEncontradoPorId = new TipoEmpleado(1, "Odontólogo");
        TipoEmpleado tipoEmpleadoEncontradoPorNombre = new TipoEmpleado(2, "Traumatólogo");

        when(repository.findById(tipoEmpleadoDTO.id())).thenReturn(Optional.of(tipoEmpleadoEncontradoPorId));
        when(repository.findByNombre(tipoEmpleadoDTO.nombre())).thenReturn(Optional.of(tipoEmpleadoEncontradoPorNombre));

        assertThrows(TipoEmpleadoException.class, () -> service.actualizar(tipoEmpleadoDTO));
    }
}