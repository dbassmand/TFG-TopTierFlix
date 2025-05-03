package com.tfg.TopTierFlix.servicio;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tfg.TopTierFlix.dto.UsuarioRegistroDTO;
import com.tfg.TopTierFlix.modelo.Usuario;

public interface UsuarioServicio extends UserDetailsService{
	
	public Usuario guardar(UsuarioRegistroDTO registroDTO);

}
