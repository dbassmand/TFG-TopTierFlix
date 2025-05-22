package com.tfg.TopTierFlix.servicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tfg.TopTierFlix.dto.ComentarioMusicaDTO;

import com.tfg.TopTierFlix.dto.MusicaCardDTO;
import com.tfg.TopTierFlix.dto.MusicaDetalleDTO;
import com.tfg.TopTierFlix.dto.MusicaListadoAdminDTO;
import com.tfg.TopTierFlix.mapper.ComentarioMusicaMapper;
import com.tfg.TopTierFlix.mapper.MusicaMapper;
import com.tfg.TopTierFlix.modelo.ComentarioMusica;
import com.tfg.TopTierFlix.modelo.Musica;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.repositorios.ComentarioMusicaRepositorio;
import com.tfg.TopTierFlix.repositorios.MusicaRepositorio;
import com.tfg.TopTierFlix.repositorios.UsuarioRepositorio;


import jakarta.transaction.Transactional;

@Service
public class MusicaServicioImpl implements MusicaServicio{
	
	@Autowired
	private MusicaRepositorio musicaRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ComentarioMusicaRepositorio comentarioMusicaRepositorio;
	
	@Autowired
	private MusicaMapper musicaMapper;
	
	@Autowired
	private ComentarioMusicaMapper comentarioMusicaMapper;

	/*
	@Override
	public List<VideojuegoCardDTO> obtenerVideojuegoIncio() {
		List<Videojuego> ultimasVideojuego = videojuegoRepositorio.findTop4ByOrderByFechaEstrenoDesc();
		return ultimasVideojuego.stream()
				.map(videojuegoMapper::toVideojuegoCardDTO)
				.collect(Collectors.toList());
	}
	 * */

	@Override
	public Page<MusicaCardDTO> obtenerTodasMusicasPaginado(Pageable pageable) {
		Page<Musica> musicaPage = musicaRepositorio.findAll(pageable);
		return musicaPage.map(musicaMapper::toMusicaCardDTO);
	}

	@Override
	public MusicaDetalleDTO obtenerMusicaDetallePorId(Integer id) {
		return musicaRepositorio.findById(id)
				.map(musicaMapper::toMusicaDetalleDTO)
				.orElse(null);
	}

	@Override
	public Page<MusicaListadoAdminDTO> obtenerTodasMusicasAdmin(Pageable pageable) {
		Page<Musica> musicaPage = musicaRepositorio.findAll(pageable);
		return musicaPage.map(musicaMapper::toMusicaListadoAdminDTO);
	}

	@Override
	public Page<Musica> obtenerTodasPaginado(Pageable pageable) {
		return musicaRepositorio.findAll(pageable);		
	}

	@Override
	public Musica obtenerMusicaPorId(Integer id) {		
		return musicaRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Musica no encontrada con ID"+id));
	}

	@Override
	public Musica guardarMusica(Musica musica) {		
		return musicaRepositorio.save(musica);
	}

	@Override
	public void eliminarMusica(Musica musica) {
		musicaRepositorio.delete(musica);
	}

	@Override
	public Page<Musica> buscarMusicaPorTitulo(String termino, Pageable pageable) {		
		return musicaRepositorio.findBytituloContainingIgnoreCase(termino, pageable);
	}

	@Override
	public boolean esFavorita(Integer musicaId, String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
		Optional<Musica> musicaOptional = musicaRepositorio.findById(musicaId);
		
		return usuarioOptional.isPresent() && musicaOptional.isPresent()&&
				usuarioOptional.get().getMusicasFavoritas().contains(musicaOptional.get());
	}

	@Override
	@Transactional
	public void agregarFavorito(Integer musicaId, String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
		Optional<Musica> musicaOptional = musicaRepositorio.findById(musicaId);
		
		if(usuarioOptional.isPresent()&& musicaOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			Musica musica = musicaOptional.get();
			usuario.getMusicasFavoritas().add(musica);
			usuarioRepositorio.save(usuario);
		}		
	}

	@Override
	 @Transactional
	public void eliminarFavorito(Integer musicaId, String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
		Optional<Musica> musicaOptional = musicaRepositorio.findById(musicaId);
		
		if(usuarioOptional.isPresent()&& musicaOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			Musica musica = musicaOptional.get();
			usuario.getMusicasFavoritas().remove(musica);
			usuarioRepositorio.save(usuario);
		}		
	}

	@Override
	public List<Musica> obtenerMusicasFavoritasDelUsuario(String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmailWithFavoritas(userEmail);
		return usuarioOptional.map(usuario -> usuario.getMusicasFavoritas()).orElse(List.of());
	}

	@Override
	public ComentarioMusica guardarComentario(ComentarioMusica comentarioMusica) {		
		return comentarioMusicaRepositorio.save(comentarioMusica);
	}

	@Override
	public List<ComentarioMusicaDTO> obtenerComentariosPorMusicaId(Integer musicaId) {
		Optional<Musica> musicaOptional = musicaRepositorio.findById(musicaId);
		if(musicaOptional.isPresent()) {
			List<ComentarioMusica> comentarios = comentarioMusicaRepositorio.findByMusica(musicaOptional.get());
			return comentarios.stream().map(comentarioMusicaMapper::toComentarioMusicaDTO).collect(Collectors.toList());
		}
		return List.of();
	}

	@Override
	public void eliminarComentarioPorId(Integer comentarioId) {
		comentarioMusicaRepositorio.deleteById(comentarioId);
		
	}

}
