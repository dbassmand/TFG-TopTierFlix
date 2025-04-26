package com.tfg.TopTierFlix.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tfg.TopTierFlix.modelo.Pelicula;

public interface PeliculaServicio {

	
	Page<Pelicula> obtenerTodasPaginado(Pageable pageable);
	
	Pelicula obtenerPeliculaPorId(Integer id);
	
	Pelicula guardarPelicula(Pelicula pelicula);
	
	void eliminarPelicula(Pelicula pelicula);
	
	Page<Pelicula>buscarPeliculaPorTitulo(String termino, Pageable pageable);
	
}
