package citasmedicas;

import citasmedicas.models.entities.Permiso;
import citasmedicas.models.entities.Rol;
import citasmedicas.models.entities.RolEnum;
import citasmedicas.models.entities.Usuario;
import citasmedicas.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class BackendCitasMedicasApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendCitasMedicasApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository) {
        return args -> {
            Permiso createPermiso = Permiso.builder()
                    .nombre("CREATE")
                    .build();

            Permiso readPermiso = Permiso.builder()
                    .nombre("READ")
                    .build();

            Permiso updatePermiso = Permiso.builder()
                    .nombre("UPDATE")
                    .build();

            Permiso deletePermiso = Permiso.builder()
                    .nombre("DELETE")
                    .build();

            Rol rolAdmin = Rol.builder()
                    .rolEnum(RolEnum.ADMIN)
                    .permisos(Set.of(createPermiso, readPermiso, updatePermiso, deletePermiso))
                    .build();

            Rol rolDeveloper = Rol.builder()
                    .rolEnum(RolEnum.DEVELOPER)
                    .permisos(Set.of(createPermiso, readPermiso))
                    .build();

            Rol rolUser = Rol.builder()
                    .rolEnum(RolEnum.USER)
                    .permisos(Set.of(createPermiso, readPermiso, updatePermiso, deletePermiso))
                    .build();

            Rol rolInvited = Rol.builder()
                    .rolEnum(RolEnum.INVITED)
                    .permisos(Set.of(readPermiso))
                    .build();

            Usuario usuarioVictor = Usuario.builder()
                    .username("victor")
                    .password("$2a$10$mbkroRGK4o9feC0IBVZa9e8cBjxqDfyXyzYuvEwYTGGAqHh5FM/Bi")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialsNoExpired(true)
                    .roles(Set.of(rolAdmin))
                    .build();

            Usuario usuarioLuis = Usuario.builder()
                    .username("luis")
                    .password("$2a$10$mbkroRGK4o9feC0IBVZa9e8cBjxqDfyXyzYuvEwYTGGAqHh5FM/Bi")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialsNoExpired(true)
                    .roles(Set.of(rolDeveloper))
                    .build();

            Usuario usuarioMartin = Usuario.builder()
                    .username("martin")
                    .password("$2a$10$mbkroRGK4o9feC0IBVZa9e8cBjxqDfyXyzYuvEwYTGGAqHh5FM/Bi")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialsNoExpired(true)
                    .roles(Set.of(rolUser))
                    .build();

            usuarioRepository.saveAll(List.of(usuarioVictor, usuarioMartin, usuarioLuis));
        };
    }
}
