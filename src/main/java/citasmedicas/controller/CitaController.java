package citasmedicas.controller;

import citasmedicas.models.dto.CitaDTO;
import citasmedicas.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/cita")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @Operation(description = "Lista las citas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = CitaDTO.class))
            )
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @Operation(description = "Guarda la cita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = CitaDTO.class)
            ))
    })
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody CitaDTO citaDTO) throws ParseException {
        return new ResponseEntity<>(service.guardar(citaDTO), HttpStatus.CREATED);
    }
}
