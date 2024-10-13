package citasmedicas.controllers;

import citasmedicas.models.dto.CitaDTO;
import citasmedicas.services.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/cita")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar citas", description = "Lista las citas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = CitaDTO.class))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @Operation(summary = "Guarda una cita", description = "Guarda la cita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = CitaDTO.class)
            ))
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> guardar(@RequestBody CitaDTO citaDTO) throws ParseException {
        return new ResponseEntity<>(service.guardar(citaDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar citas por cliente ID", description = "Listar citas por cliente ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = CitaDTO.class)
            ))
    })
    @GetMapping("/listar-por-cliente")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> listarPorClienteId(@RequestParam Integer clienteId, @RequestParam String estado) {
        return new ResponseEntity<>(service.listarCitasPorCliente(clienteId, estado), HttpStatus.OK);
    }
}
