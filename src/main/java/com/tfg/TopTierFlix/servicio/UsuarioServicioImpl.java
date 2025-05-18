package com.tfg.TopTierFlix.servicio;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfg.TopTierFlix.dto.UsuarioRegistroDTO;
import com.tfg.TopTierFlix.modelo.Rol;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.repositorios.RolRepositorio;
import com.tfg.TopTierFlix.repositorios.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Asegúrate de tener este bean configurado

    @Override
    @Transactional
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        // Siempre buscamos el rol existente en la base de datos
        Rol rolUsuario = rolRepositorio.findByNombre("ROLE_USER");

        if (rolUsuario == null) {//se implementa por error en fase de dsarrollo         
            rolUsuario = new Rol("ROLE_USER");
            rolRepositorio.save(rolUsuario);
        }
        //Se mapea de Usuario a UsuarioRegistroDTO
        Usuario usuario = new Usuario(registroDTO.getNombre(), registroDTO.getApellido(), registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()), Arrays.asList(rolUsuario));//se codifica la contraseña
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(email);
        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario o contraseña inválidos."));

        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(),
                mapearAutoridadesDeRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesDeRoles(Collection<Rol> roles) {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());
    }

       
	@Override
	public Page<Usuario> obtenerTodosUsuariosPaginado(Pageable pageable) {
		return usuarioRepositorio.findAll(pageable);
	}
	
	@Override
    public Page<Usuario> buscarPorNombreApellidoEmail(String termino, Pageable pageable) {
        return usuarioRepositorio.buscarPorNombreApellidoEmail(termino, pageable);
    }

	@Override
	public Usuario obtenerUsuarioPorId(Integer id) {
		return usuarioRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
	}

	@Override
	public Usuario obtenerUsuarioPorIdConFavoritas(Integer id) {
        return usuarioRepositorio.findByIdWithFavoritas(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

}