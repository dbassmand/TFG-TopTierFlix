package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.data.domain.Sort;
import com.tfg.TopTierFlix.modelo.GeneroVideojuego;

public interface GeneroVideojuegoServicio {
	public List<GeneroVideojuego> obtenerTodosGeneros(Sort sort);	

}
