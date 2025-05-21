package com.tfg.TopTierFlix.servicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tfg.TopTierFlix.dto.ComentarioVideojuegoDTO;
import com.tfg.TopTierFlix.dto.VideojuegoListadoAdminDTO;
import com.tfg.TopTierFlix.dto.VideojuegoCardDTO;
import com.tfg.TopTierFlix.dto.VideojuegoDetalleDTO;
import com.tfg.TopTierFlix.mapper.ComentarioVideojuegoMapper;
import com.tfg.TopTierFlix.mapper.VideojuegoMapper;
import com.tfg.TopTierFlix.modelo.ComentarioVideojuego;
import com.tfg.TopTierFlix.modelo.Videojuego;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.repositorios.ComentarioVideojuegoRepositorio;
import com.tfg.TopTierFlix.repositorios.UsuarioRepositorio;
import com.tfg.TopTierFlix.repositorios.VideojuegoRepositorio;
import jakarta.transaction.Transactional;

@Service
public class VideojuegoServicioImpl implements VideojuegoServicio{
	
	@Autowired
	private VideojuegoRepositorio videojuegoRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ComentarioVideojuegoRepositorio comentarioVideojuegoRepositorio;
	
	@Autowired
	private VideojuegoMapper videojuegoMapper;
	
	@Autowired
	private ComentarioVideojuegoMapper comentarioVideojuegoMapper;

	@Override
	public List<VideojuegoCardDTO> obtenerVideojuegoIncio() {
		List<Videojuego> ultimasVideojuego = videojuegoRepositorio.findTop4ByOrderByFechaEstrenoDesc();
		return ultimasVideojuego.stream()
				.map(videojuegoMapper::toVideojuegoCardDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Page<VideojuegoCardDTO> obtenerTodasVideojuegosPaginado(Pageable pageable) {
		Page<Videojuego> videojuegoPage = videojuegoRepositorio.findAll(pageable);
		return videojuegoPage.map(videojuegoMapper::toVideojuegoCardDTO);
	}

	@Override
	public VideojuegoDetalleDTO obtenerVideojuegoDetallePorId(Integer id) {
		return videojuegoRepositorio.findById(id)
				.map(videojuegoMapper::toVideojuegoDetalleDTO)
				.orElse(null);
	}

	@Override
	public Page<VideojuegoListadoAdminDTO> obtenerTodasVideojuegosAdmin(Pageable pageable) {
		Page<Videojuego> videojuegosPage = videojuegoRepositorio.findAll(pageable);
		return videojuegosPage.map(videojuegoMapper::toVideojuegoListadoAdminDTO);
	}

	@Override
	public Page<Videojuego> obtenerTodasPaginado(Pageable pageable) {
		return videojuegoRepositorio.findAll(pageable);		
	}

	@Override
	public Videojuego obtenerVideojuegoPorId(Integer id) {		
		return videojuegoRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Videojuego no encontrada con ID"+id));
	}

	@Override
	public Videojuego guardarVideojuego(Videojuego videojuego) {		
		return videojuegoRepositorio.save(videojuego);
	}

	@Override
	public void eliminarVideojuego(Videojuego videojuego) {
		videojuegoRepositorio.delete(videojuego);
	}

	@Override
	public Page<Videojuego> buscarVideojuegoPorTitulo(String termino, Pageable pageable) {		
		return videojuegoRepositorio.findBytituloContainingIgnoreCase(termino, pageable);
	}

	@Override
	public boolean esFavorita(Integer videojuegoId, String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
		Optional<Videojuego> videojuegoOptional = videojuegoRepositorio.findById(videojuegoId);
		
		return usuarioOptional.isPresent() && videojuegoOptional.isPresent()&&
				usuarioOptional.get().getVideojuegosFavoritas().contains(videojuegoOptional.get());
	}

	@Override
	@Transactional
	public void agregarFavorito(Integer videojuegoId, String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
		Optional<Videojuego> videojuegoOptional = videojuegoRepositorio.findById(videojuegoId);
		
		if(usuarioOptional.isPresent()&& videojuegoOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			Videojuego videojuego = videojuegoOptional.get();
			usuario.getVideojuegosFavoritas().add(videojuego);
			usuarioRepositorio.save(usuario);
		}		
	}

	@Override
	 @Transactional
	public void eliminarFavorito(Integer videojuegoId, String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
		Optional<Videojuego> videojuegoOptional = videojuegoRepositorio.findById(videojuegoId);
		
		if(usuarioOptional.isPresent()&& videojuegoOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			Videojuego videojuego = videojuegoOptional.get();
			usuario.getVideojuegosFavoritas().remove(videojuego);
			usuarioRepositorio.save(usuario);
		}		
	}

	@Override
	public List<Videojuego> obtenerVideojuegosFavoritasDelUsuario(String userEmail) {
		Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmailWithFavoritas(userEmail);
		return usuarioOptional.map(usuario -> usuario.getVideojuegosFavoritas()).orElse(List.of());
	}

	@Override
	public ComentarioVideojuego guardarComentario(ComentarioVideojuego comentarioVideojuego) {		
		return comentarioVideojuegoRepositorio.save(comentarioVideojuego);
	}

	@Override
	public List<ComentarioVideojuegoDTO> obtenerComentariosPorVideojuegoId(Integer videojuegoId) {
		Optional<Videojuego> videojuegoOptional = videojuegoRepositorio.findById(videojuegoId);
		if(videojuegoOptional.isPresent()) {
			List<ComentarioVideojuego> comentarios = comentarioVideojuegoRepositorio.findByVideojuego(videojuegoOptional.get());
			return comentarios.stream().map(comentarioVideojuegoMapper::toComentarioVideojuegoDTO).collect(Collectors.toList());
		}
		return List.of();
	}

	@Override
	public void eliminarComentarioPorId(Integer comentarioId) {
		comentarioVideojuegoRepositorio.deleteById(comentarioId);
		
	}

}
