package citasmedicas.controllers;

import citasmedicas.models.dto.AreaDTO;
import citasmedicas.services.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/area")
@CrossOrigin(origins = "http://localhost:4200")
public class AreaController {

    private final AreaService service;

    public AreaController(AreaService service) {
        this.service = service;
    }

    @Operation(summary = "Lista todas las areas", description = "Lista todas las areas")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "array", implementation = AreaDTO.class)))})
    @GetMapping
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un area por id", description = "Obtiene un area por ID", parameters = @Parameter(name = "id", description = "ID del área", required = true))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "array", implementation = AreaDTO.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerArea(@PathVariable Integer id) {
        return new ResponseEntity<>(service.obtenerPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Guarda un área", description = "Guarda un área")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "array", implementation = AreaDTO.class)))})
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody AreaDTO areaDTO) {
        return new ResponseEntity<>(service.guardar(areaDTO), HttpStatus.OK);
    }

    @Operation(summary = "Actualiza un area", description = "Actualiza un área por ID", parameters = @Parameter(name = "id", description = "ID del área", required = true))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "array", implementation = AreaDTO.class)))})
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody AreaDTO areaDTO, @PathVariable Integer id) {
        return new ResponseEntity<>(service.actualizar(areaDTO, id), HttpStatus.OK);
    }

    @Operation(summary = "Busca un area por nombre", description = "Busca area por nombre", parameters = @Parameter(name = "nombre", description = "nombre del area", required = true))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "array", implementation = AreaDTO.class)))})
    @GetMapping("/buscar-nombre/{nombre}")
    public ResponseEntity<?> obtenerPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(service.obtenerPorNombre(nombre), HttpStatus.OK);
    }
}
