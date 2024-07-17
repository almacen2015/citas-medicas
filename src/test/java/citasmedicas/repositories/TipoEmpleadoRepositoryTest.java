package citasmedicas.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TipoEmpleadoRepositoryTest {

    @Test
    public void testListar_DadoNoParametros_RetornaListaTiposEmpleados() {
        // Arrange

        // Act

        // Assert
    }
}
