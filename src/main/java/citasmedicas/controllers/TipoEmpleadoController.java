package citasmedicas.controllers;

import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.services.TipoEmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("")
    public ResponseEntity<List<TipoEmpleadoDTO>> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @Operation(summary = "Guardar un tipo de empleado", description = "Recibe un tipo de empleado")
    @PostMapping("")
    public ResponseEntity<TipoEmpleadoDTO> guardar(@RequestBody TipoEmpleadoDTO tipoEmpleadoDTO) {
        return new ResponseEntity<>(service.guardar(tipoEmpleadoDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Busca un tipo de empleado por su id", description = "Recibe el id de un tipo de empleado")
    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<TipoEmpleadoDTO> buscarPorId(@PathVariable Integer id) {
        return new ResponseEntity<>(service.buscarPorId(id), HttpStatus.FOUND);
    }

    @Operation(summary = "Busca un tipo de empleado por su nombre", description = "Recibe el nombre de un tipo de empleado")
    @GetMapping("/buscar-nombre/{nombre}")
    public ResponseEntity<TipoEmpleadoDTO> buscarPorNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(service.buscarPorNombre(nombre), HttpStatus.FOUND);
    }

    @Operation(summary = "Elimina un tipo de empleado", description = "Recibe un tipo de empleado")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }

    @Operation(summary = "Actualiza un tipo de empleado", description = "Recibe un tipo de empleado")
    @PutMapping("")
    public ResponseEntity<TipoEmpleadoDTO> actualizar(@RequestBody TipoEmpleadoDTO tipoEmpleado) {
        return new ResponseEntity<>(service.actualizar(tipoEmpleado), HttpStatus.OK);
    }
}
