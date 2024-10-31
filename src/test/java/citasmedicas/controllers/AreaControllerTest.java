package citasmedicas.controllers;

import citasmedicas.models.dto.AreaDTO;
import citasmedicas.services.AreaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AreaController.class)
class AreaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AreaService service;

    private AreaDTO area1;
    private AreaDTO area2;

    @BeforeEach
    void setUp() {
        area1 = new AreaDTO(1, "Medicina", true);
        area2 = new AreaDTO(2, "Odontologia", true);
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testListar() throws Exception {
        when(service.listar()).thenReturn(Arrays.asList(area1, area2));

        mockMvc.perform(get("/api/area")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Medicina"))
                .andExpect(jsonPath("$[1].nombre").value("Odontologia"));
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testObtenerPorId() throws Exception {
        when(service.obtenerPorId(1)).thenReturn(area1);

        mockMvc.perform(get("/api/area/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Medicina"));
    }

    @Test
    @WithMockUser(username = "victor", password = "1234", roles = {"ADMIN"})
    public void testObtenerPorIdNoEncontrado() throws Exception {
        when(service.obtenerPorId(1)).thenReturn(area1);

        mockMvc.perform(get("/api/area/{id}", 99)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}