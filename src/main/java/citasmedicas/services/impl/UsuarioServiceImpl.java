package citasmedicas.services.impl;

import citasmedicas.models.entities.Usuario;
import citasmedicas.repositories.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsername(username);
        if (usuarioEncontrado.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Añadir roles y permisos al usuario encontrado en la base de datos

        usuarioEncontrado.get().getRoles().forEach(rol -> {
            authorityList.add(new SimpleGrantedAuthority("ROLE_" + rol.getRolEnum().name()));
        });

        // Añadir permisos al usuario encontrado en la base de datos (opcional)
        usuarioEncontrado.get().getRoles().stream()
                .flatMap(rol -> rol.getPermisos().stream())
                .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getNombre())));

        // Crear un objeto User con los datos del usuario encontrado en la base de datos

        return new User(usuarioEncontrado.get().getUsername(),
                usuarioEncontrado.get().getPassword(),
                usuarioEncontrado.get().isEnabled(),
                usuarioEncontrado.get().isAccountNoExpired(),
                usuarioEncontrado.get().isCredentialsNoExpired(),
                usuarioEncontrado.get().isAccountNoLocked(),
                authorityList);

    }
}
