package com.tfg.TopTierFlix;

import com.tfg.TopTierFlix.modelo.Rol;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.repositorios.UsuarioRepositorio;
import com.tfg.TopTierFlix.servicio.UsuarioServicioImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServicioImplTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private UsuarioServicioImpl usuarioServicio;

    @Test
    void loadUserByUsername_usuarioExistente_devuelveUserDetails() {
       
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword("password");
        usuario.setRoles(Collections.singletonList(new Rol("ROLE_USER")));

        when(usuarioRepositorio.findByEmail(email)).thenReturn(Optional.of(usuario));

        
        UserDetails userDetails = usuarioServicio.loadUserByUsername(email);

        
        assertEquals(email, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_usuarioNoExistente_lanzaUsernameNotFoundException() {
        // Arrange
        String email = "nonexistent@example.com";
        when(usuarioRepositorio.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> usuarioServicio.loadUserByUsername(email));
        assertEquals("Usuario o contraseña inválidos.", assertThrows(UsernameNotFoundException.class, () -> usuarioServicio.loadUserByUsername(email))
        		.getMessage());
    }
}