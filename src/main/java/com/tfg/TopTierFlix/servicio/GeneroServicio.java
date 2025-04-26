package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.tfg.TopTierFlix.modelo.Genero;

public interface GeneroServicio {
	
	public List<Genero> obtenerTodosGeneros(Sort sort);			

}
