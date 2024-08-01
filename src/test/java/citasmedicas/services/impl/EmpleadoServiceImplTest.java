package citasmedicas.services.impl;

import citasmedicas.models.entities.Empleado;
import citasmedicas.models.entities.TipoEmpleado;
import citasmedicas.repositories.EmpleadoRepository;
import citasmedicas.repositories.TipoEmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository repository;

    @Mock
    private TipoEmpleadoRepository tipoEmpleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl service;

    @BeforeEach
    void setUp() {
        TipoEmpleado tipoEmpleado = new TipoEmpleado(null, "Médico");
        TipoEmpleado tipoEmpleado2 = new TipoEmpleado(null, "Enfermero");

        tipoEmpleadoRepository.save(tipoEmpleado);
        tipoEmpleadoRepository.save(tipoEmpleado2);

        Empleado empleado = new Empleado();
        empleado.setId(null);
        empleado.setNombre("Juan");
        empleado.setApellidoPaterno("Pérez");
        empleado.setApellidoMaterno("Gómez");
        empleado.setNumeroDocumento("12345678");
        empleado.setTipoEmpleado(tipoEmpleado);

        Empleado empleado2 = new Empleado();
        empleado2.setId(null);
        empleado2.setNombre("María");
        empleado2.setApellidoPaterno("González");
        empleado2.setApellidoMaterno("Pérez");
        empleado2.setNumeroDocumento("87654321");
        empleado2.setTipoEmpleado(tipoEmpleado2);

        repository.save(empleado);
        repository.save(empleado2);
    }
}