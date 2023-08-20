package citasmedicas.controllers;

import citasmedicas.models.dto.TipoEmpleadoDTO;
import citasmedicas.services.TipoEmpleadoService;
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

    @GetMapping("")
    public List<TipoEmpleadoDTO> listar() {
        return service.listar();
    }

    @PostMapping("")
    public TipoEmpleadoDTO guardar(@RequestBody TipoEmpleadoDTO tipoEmpleadoDTO) {
        return service.guardar(tipoEmpleadoDTO);
    }

    @GetMapping("/{id}")
    public TipoEmpleadoDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id).orElse(null);
    }

    @GetMapping("/buscar-nombre/{nombre}")
    public TipoEmpleadoDTO buscarPorNombre(@PathVariable String nombre) {
        return service.buscarPorNombre(nombre).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
