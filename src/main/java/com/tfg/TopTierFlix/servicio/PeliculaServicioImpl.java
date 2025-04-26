package com.tfg.TopTierFlix.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tfg.TopTierFlix.modelo.Pelicula;
import com.tfg.TopTierFlix.repositorios.PeliculaRepositorio;

@Service
public class PeliculaServicioImpl implements PeliculaServicio{
	
	@Autowired
	private PeliculaRepositorio peliculaRepositorio;
	
	@Override
	public Page<Pelicula> obtenerTodasPaginado(Pageable pageable){
		return peliculaRepositorio.findAll(pageable);
	}
	
	@Override
	public Pelicula obtenerPeliculaPorId(Integer id) {
		return peliculaRepositorio.findById(id).orElse(null);
	}
	
	@Override
	public Pelicula guardarPelicula(Pelicula pelicula) {
		return peliculaRepositorio.save(pelicula);
	}
	
	@Override
	public void eliminarPelicula(Pelicula pelicula) {
		peliculaRepositorio.delete(pelicula);
	}
	
	@Override
	public Page<Pelicula> buscarPeliculaPorTitulo(String termino, Pageable pageable){
		return peliculaRepositorio.findBytituloContainingIgnoreCase(termino, pageable);
	}	

}
