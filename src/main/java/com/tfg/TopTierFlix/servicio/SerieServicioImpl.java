package com.tfg.TopTierFlix.servicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.tfg.TopTierFlix.dto.ComentarioSerieDTO;
import com.tfg.TopTierFlix.dto.SerieCardDTO;
import com.tfg.TopTierFlix.dto.SerieDetalleDTO;
import com.tfg.TopTierFlix.dto.SerieListadoAdminDTO;
import com.tfg.TopTierFlix.mapper.ComentarioSerieMapper;
import com.tfg.TopTierFlix.mapper.SerieMapper;
import com.tfg.TopTierFlix.modelo.ComentarioSerie;
import com.tfg.TopTierFlix.modelo.Serie;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.repositorios.ComentarioSerieRepositorio;
import com.tfg.TopTierFlix.repositorios.SerieRepositorio;
import com.tfg.TopTierFlix.repositorios.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service
public class SerieServicioImpl implements SerieServicio{
	
	@Autowired
	private SerieRepositorio serieRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ComentarioSerieRepositorio comentarioSerieRepositorio;
	
	@Autowired
	private SerieMapper serieMapper;
	
	@Autowired
	private ComentarioSerieMapper comentarioSerieMapper;

	@Override
	public List<SerieCardDTO> obtenerSeriesIncio() {
		List<Serie> ultimasSeries = serieRepositorio.findTop4ByOrderByFechaEstrenoDesc();
		return ultimasSeries.stream()
				.map(serieMapper::toSerieCardDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Page<SerieCardDTO> obtenerTodasSeriesPaginado(Pageable pageable) {
		Page<Serie> seriesPage = serieRepositorio.findAll(pageable);
		return seriesPage.map(serieMapper::toSerieCardDTO);
	}

	@Override
	public SerieDetalleDTO obtenerSerieDetallePorId(Integer id) {		
		return serieRepositorio.findById(id)
				.map(serieMapper::toSerieDetalleDTO)
				.orElse(null);
	}

	@Override
	public Page<SerieListadoAdminDTO> obtenerTodasSeriesAdmin(Pageable pageable) {
		Page<Serie> seriesPage = serieRepositorio.findAll(pageable);
		return seriesPage.map(serieMapper::toSerieListadoAdminDTO);
	}

	@Override
	public Page<Serie> obtenerTodasPaginado(Pageable pageable) {
		return serieRepositorio.findAll(pageable);		
	}

	@Override
	public Serie obtenerSeriePorId(Integer id) {		
		return serieRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Serie no encontrada con ID"+id));
	}

	@Override
	public Serie guardarSerie(Serie serie) {		
		return serieRepositorio.save(serie);
	}

	@Override
	public void eliminarSerie(Serie serie) {
		serieRepositorio.delete(serie);
	}

	@Override
	public Page<Serie> buscarSeriePorTitulo(String termino, Pageable pageable) {		
		return serieRepositorio.findBytituloContainingIgnoreCase(termino, pageable);
	}

	@Override
	public boolean esFavorita(Integer serieId, String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
		Optional<Serie> serieOptional = serieRepositorio.findById(serieId);
		
		return usuarioOptional.isPresent() && serieOptional.isPresent()&&
				usuarioOptional.get().getSeriesFavoritas().contains(serieOptional.get());
	}

	@Override
	@Transactional
	public void agregarFavorito(Integer serieId, String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
		Optional<Serie> serieOptional = serieRepositorio.findById(serieId);
		
		if(usuarioOptional.isPresent()&& serieOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			Serie serie = serieOptional.get();
			usuario.getSeriesFavoritas().add(serie);
			usuarioRepositorio.save(usuario);
		}		
	}

	@Override
	 @Transactional
	public void eliminarFavorito(Integer serieId, String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
		Optional<Serie> serieOptional = serieRepositorio.findById(serieId);
		
		if(usuarioOptional.isPresent()&& serieOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			Serie serie = serieOptional.get();
			usuario.getSeriesFavoritas().remove(serie);
			usuarioRepositorio.save(usuario);
		}		
	}

	@Override
	public List<Serie> obtenerSeriesFavoritasDelUsuario(String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmailWithFavoritas(userEmail);
		return usuarioOptional.map(usuario -> usuario.getSeriesFavoritas()).orElse(List.of());
	}

	@Override
	public ComentarioSerie guardarComentario(ComentarioSerie comentarioSerie) {		
		return comentarioSerieRepositorio.save(comentarioSerie);
	}

	@Override
	public List<ComentarioSerieDTO> obtenerComentariosPorSerieId(Integer serieId) {
		Optional<Serie> serieOptional = serieRepositorio.findById(serieId);
		if(serieOptional.isPresent()) {
			List<ComentarioSerie> comentarios = comentarioSerieRepositorio.findBySerie(serieOptional.get());
			return comentarios.stream().map(comentarioSerieMapper::toComentarioSerieDTO).collect(Collectors.toList());
		}
		return List.of();
	}

	@Override
	public void eliminarComentarioPorId(Integer comentarioId) {
		comentarioSerieRepositorio.deleteById(comentarioId);
		
	}

}
