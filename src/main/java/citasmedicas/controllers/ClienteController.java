package citasmedicas.controllers;

import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.services.ClienteService;
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
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(summary = "Lista todos los clientes", description = "Lista todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = ClienteDTO.class))
            )
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @Operation(summary = "Registra un cliente", description = "Registra un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = ClienteDTO.class))
            )
    })
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(service.guardar(clienteDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Elimina un cliente por id", description = "Elimina un cliente por ID",
            parameters = @Parameter(name = "id", description = "ID del cliente", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "void", implementation = void.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Actualizar un cliente", description = "Actualiza un cliente",
            parameters = @Parameter(name = "id", description = "ID del cliente", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = ClienteDTO.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
        return new ResponseEntity<>(service.actualizar(clienteDTO, id), HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un cliente por ID", description = "Obtiene un cliente por ID",
            parameters = @Parameter(name = "id", description = "ID del cliente", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(type = "array", implementation = ClienteDTO.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Integer id) {
        return new ResponseEntity<>(service.obtenerClientePorId(id), HttpStatus.OK);
    }
}
