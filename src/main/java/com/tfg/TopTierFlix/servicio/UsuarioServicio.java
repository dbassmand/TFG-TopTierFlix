package com.tfg.TopTierFlix.servicio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tfg.TopTierFlix.dto.UsuarioRegistroDTO;
import com.tfg.TopTierFlix.modelo.Usuario;

public interface UsuarioServicio extends UserDetailsService{
	
	public Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	Page<Usuario> obtenerTodosUsuariosPaginado(Pageable pageable);
	
	Page<Usuario> buscarPorNombreApellidoEmail(String termino, Pageable pageable);
	
	Usuario obtenerUsuarioPorId(Integer id);
	
	Usuario obtenerUsuarioPorIdConFavoritas(Integer id);
	
	Optional<Usuario> obtenerUsuarioPorEmail(String email);
	
	void eliminarUsuario(Usuario usuario);

}