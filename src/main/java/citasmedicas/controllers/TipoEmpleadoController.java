package citasmedicas.controllers;

import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.services.TipoEmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipoempleado")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoEmpleadoController {

    private final TipoEmpleadoService service;

    public TipoEmpleadoController(TipoEmpleadoService service) {
        this.service = service;
    }

    @Operation(summary = "Lista todos tipos de empleados", description = "Lista todos los tipos de empleados")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<TipoEmpleadoDTO>> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @Operation(summary = "Guardar un tipo de empleado", description = "Recibe un tipo de empleado")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<TipoEmpleadoDTO> guardar(@RequestBody TipoEmpleadoDTO tipoEmpleadoDTO) {
        return new ResponseEntity<>(service.guardar(tipoEmpleadoDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Busca un tipo de empleado por su id", description = "Recibe el id de un tipo de empleado")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<TipoEmpleadoDTO> buscarPorId(@PathVariable Integer id) {
        TipoEmpleadoDTO tipoEmpleadoEncontrado = service.buscarPorId(id);
        if (tipoEmpleadoEncontrado != null) {
            return new ResponseEntity<>(tipoEmpleadoEncontrado, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Busca un tipo de empleado por su nombre", description = "Recibe el nombre de un tipo de empleado")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/buscar-nombre/{nombre}")
    public ResponseEntity<TipoEmpleadoDTO> buscarPorNombre(@PathVariable String nombre) {
        TipoEmpleadoDTO encontrado = service.buscarPorNombre(nombre);
        if (encontrado != null) {
            return new ResponseEntity<>(encontrado, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Elimina un tipo de empleado", description = "Recibe un tipo de empleado")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }

    @Operation(summary = "Actualiza un tipo de empleado", description = "Recibe un tipo de empleado")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("")
    public ResponseEntity<TipoEmpleadoDTO> actualizar(@RequestBody TipoEmpleadoDTO tipoEmpleado) {
        return new ResponseEntity<>(service.actualizar(tipoEmpleado), HttpStatus.OK);
    }
}
