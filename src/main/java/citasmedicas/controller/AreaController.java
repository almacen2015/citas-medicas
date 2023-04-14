package citasmedicas.controller;

import citasmedicas.model.dto.AreaDTO;
import citasmedicas.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/area")
@CrossOrigin(origins = "http://localhost:4200")
public class AreaController {

    @Autowired
    private AreaService service;

    @Operation(description = "Lista las areas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = AreaDTO.class))
            )
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @Operation(description = "Obtiene un area por ID",
            parameters = @Parameter(name = "id", description = "ID del área", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = AreaDTO.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerArea(@PathVariable Integer id) {
        return new ResponseEntity<>(service.obtenerPorId(id), HttpStatus.OK);
    }

    @Operation(description = "Registra un área")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = AreaDTO.class))
            )
    })
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody AreaDTO areaDTO) {
        return new ResponseEntity<>(service.guardar(areaDTO), HttpStatus.OK);
    }

    @Operation(description = "Actualiza un área por ID",
            parameters = @Parameter(name = "id", description = "ID del área", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = AreaDTO.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody AreaDTO areaDTO, @PathVariable Integer id) {
        return new ResponseEntity<>(service.actualizar(areaDTO, id), HttpStatus.OK);
    }

    @Operation(description = "Elimina un área por ID",
            parameters = @Parameter(name = "id", description = "ID del área", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "void", implementation = void.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
