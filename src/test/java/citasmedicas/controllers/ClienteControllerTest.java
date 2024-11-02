package citasmedicas.controllers;

import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Cliente;
import citasmedicas.services.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ClienteDTO clienteDTO1;
    private ClienteDTO clienteDTO2;

    private Cliente cliente1;

    @BeforeEach
    void setUp() {
        cliente1 = new Cliente(1, "Victor", "Orbegozo", "Percovich", "70553916", LocalDate.of(1994, 4, 5), "M", "111111", "vorbegozop@gmail.com");

        clienteDTO1 = new ClienteDTO(1, "Victor", "Orbegozo", "Percovich", "70553916", "2024-10-10", "M", "11111111", "vorbegozop@gmail.com");
        clienteDTO2 = new ClienteDTO(2, "Maria", "Torres", "Gomez", "12345678", "1994-10-10", "F", "11111111", "maria@gmail.com");
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testEliminar() throws Exception {
        doNothing().when(service).eliminar(any(Integer.class));

        mockMvc.perform(delete("/api/cliente/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testGuardar() throws Exception {
        String json = objectMapper.writeValueAsString(clienteDTO1);

        when(service.guardar(any(ClienteDTO.class))).thenReturn(clienteDTO1);

        mockMvc.perform(post("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testListar() throws Exception {
        when(service.listar()).thenReturn(Arrays.asList(clienteDTO1, clienteDTO2));

        mockMvc.perform(get("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Victor"))
                .andExpect(jsonPath("$[1].nombre").value("Maria"));
    }
}