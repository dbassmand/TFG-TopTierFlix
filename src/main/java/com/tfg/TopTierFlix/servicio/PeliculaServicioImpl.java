package com.tfg.TopTierFlix.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tfg.TopTierFlix.dto.PeliculaDetalleDTO;
import com.tfg.TopTierFlix.dto.PeliculaCardDTO;
import com.tfg.TopTierFlix.dto.PeliculaListadoAdminDTO;
import com.tfg.TopTierFlix.mapper.PeliculaMapper;
import com.tfg.TopTierFlix.modelo.Pelicula;
import com.tfg.TopTierFlix.repositorios.PeliculaRepositorio;

@Service
public class PeliculaServicioImpl implements PeliculaServicio{
	
	@Autowired
	private PeliculaRepositorio peliculaRepositorio;
	
	@Autowired
	private PeliculaMapper peliculaMapper;
	
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

	@Override
	public PeliculaDetalleDTO obtenerPeliculaDetallePorId(Integer id) {
		return peliculaRepositorio.findById(id)
				.map(peliculaMapper::toPeliculaDetalleDTO)
				.orElse(null);
	}

	@Override
	public List<PeliculaCardDTO> obtenerPeliculasInicio() {
		List<Pelicula> ultimasPeliculas = peliculaRepositorio.findTop4ByOrderByFechaEstrenoDesc();
		return ultimasPeliculas.stream()
				.map(peliculaMapper::toPeliculaCardDTO)
				.collect(Collectors.toList());
	}
	
	@Override
    public Page<PeliculaCardDTO> obtenerTodasPeliculasPaginado(Pageable pageable) {
        Page<Pelicula> peliculasPage = peliculaRepositorio.findAll(pageable);
        return peliculasPage.map(peliculaMapper::toPeliculaCardDTO);
    }

	@Override
	public Page<PeliculaListadoAdminDTO> obtenerTodasPeliculasAdmin(Pageable pageable) {
		 Page<Pelicula> peliculasPage = peliculaRepositorio.findAll(pageable);
	        return peliculasPage.map(peliculaMapper::toPeliculaListadoAdminDTO);
	}	
}
