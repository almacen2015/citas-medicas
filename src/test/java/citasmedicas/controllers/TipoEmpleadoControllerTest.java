package citasmedicas.controllers;

import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.models.entities.TipoEmpleado;
import citasmedicas.services.TipoEmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TipoEmpleadoController.class)
class TipoEmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TipoEmpleadoService service;

    private TipoEmpleado tipoEmpleado1;
    private TipoEmpleado tipoEmpleado2;

    private TipoEmpleadoDTO tipoEmpleadoDTO1;
    private TipoEmpleadoDTO tipoEmpleadoDTO2;

    @BeforeEach
    void setUp() {
        tipoEmpleado1 = new TipoEmpleado(1, "Medico");
        tipoEmpleado2 = new TipoEmpleado(2, "Enfermera");

        tipoEmpleadoDTO1 = new TipoEmpleadoDTO(1, "Medico");
        tipoEmpleadoDTO2 = new TipoEmpleadoDTO(2, "Enfermera");
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testEliminar() throws Exception {
        doNothing().when(service).eliminar(any(Integer.class));

        mockMvc.perform(delete("/api/tipoempleado/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testBuscarPorNombre() throws Exception {
        when(service.buscarPorNombre(any(String.class))).thenReturn(tipoEmpleadoDTO1);

        mockMvc.perform(get("/api/tipoempleado/buscar-nombre/{nombre}", "Medico")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Medico"));
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testBuscarPorId() throws Exception {
        when(service.buscarPorId(any(Integer.class))).thenReturn(tipoEmpleadoDTO1);

        mockMvc.perform(get("/api/tipoempleado/buscar-id/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Medico"));
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testGuardar() throws Exception {
        TipoEmpleadoDTO tipoEmpleadoNuevo = new TipoEmpleadoDTO(null, "Medico");
        String json = objectMapper.writeValueAsString(tipoEmpleadoNuevo);

        when(service.guardar(any(TipoEmpleadoDTO.class))).thenReturn(tipoEmpleadoDTO1);

        mockMvc.perform(post("/api/tipoempleado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testListar() throws Exception {
        when(service.listar()).thenReturn(Arrays.asList(tipoEmpleadoDTO1, tipoEmpleadoDTO2));

        mockMvc.perform(get("/api/tipoempleado")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}