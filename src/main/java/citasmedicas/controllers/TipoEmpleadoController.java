package citasmedicas.controllers;

import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.services.TipoEmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
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
    public List<TipoEmpleadoDTO> listar() {
        return service.listar();
    }

    @Operation(summary = "Guardar un tipo de empleado", description = "Recibe un tipo de empleado")
    @PostMapping("")
    public TipoEmpleadoDTO guardar(@RequestBody TipoEmpleadoDTO tipoEmpleadoDTO) {
        return service.guardar(tipoEmpleadoDTO);
    }

    @Operation(summary = "Busca un tipo de empleado por su id", description = "Recibe el id de un tipo de empleado")
    @GetMapping("/{id}")
    public TipoEmpleadoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Busca un tipo de empleado por su nombre", description = "Recibe el nombre de un tipo de empleado")
    @GetMapping("/buscar-nombre/{nombre}")
    public TipoEmpleadoDTO buscarPorNombre(@PathVariable String nombre) {
        return service.buscarPorNombre(nombre);
    }

    @Operation(summary = "Actualiza un tipo de empleado", description = "Recibe un tipo de empleado")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
