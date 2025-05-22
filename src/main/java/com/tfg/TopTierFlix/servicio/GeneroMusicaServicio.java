package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.tfg.TopTierFlix.modelo.GeneroMusica;


public interface GeneroMusicaServicio {
	public List<GeneroMusica> obtenerTodosGeneros(Sort sort);	

}
