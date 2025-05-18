package com.tfg.TopTierFlix.servicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tfg.TopTierFlix.dto.PeliculaDetalleDTO;
import com.tfg.TopTierFlix.dto.ComentarioDTO;
import com.tfg.TopTierFlix.dto.PeliculaCardDTO;
import com.tfg.TopTierFlix.dto.PeliculaListadoAdminDTO;
import com.tfg.TopTierFlix.mapper.ComentarioMapper;
import com.tfg.TopTierFlix.mapper.PeliculaMapper;
import com.tfg.TopTierFlix.modelo.Comentario;
import com.tfg.TopTierFlix.modelo.Pelicula;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.repositorios.ComentarioRepositorio;
import com.tfg.TopTierFlix.repositorios.PeliculaRepositorio;
import com.tfg.TopTierFlix.repositorios.UsuarioRepositorio;

@Service
public class PeliculaServicioImpl implements PeliculaServicio{
	
	@Autowired
	private PeliculaRepositorio peliculaRepositorio;
	
	@Autowired
    private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	
	@Autowired
	private PeliculaMapper peliculaMapper;
	
	@Autowired
	private ComentarioMapper comentarioMapper;
	
	@Override
	public Page<Pelicula> obtenerTodasPaginado(Pageable pageable){
		return peliculaRepositorio.findAll(pageable);
	}
	
	//Se crea Test Unitario para el siguiente metodo
	@Override
	public Pelicula obtenerPeliculaPorId(Integer id) {
		return peliculaRepositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Película no encontrada con ID: " + id));
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
	
	@Override
    public boolean esFavorita(Integer peliculaId, String userEmail) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
        Optional<Pelicula> peliculaOptional = peliculaRepositorio.findById(peliculaId);

        return usuarioOptional.isPresent() && peliculaOptional.isPresent() &&
               usuarioOptional.get().getPeliculasFavoritas().contains(peliculaOptional.get());
    }
	
	@Override
    @Transactional
    public void agregarFavorito(Integer peliculaId, String userEmail) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
        Optional<Pelicula> peliculaOptional = peliculaRepositorio.findById(peliculaId);

        if (usuarioOptional.isPresent() && peliculaOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Pelicula pelicula = peliculaOptional.get();
            usuario.getPeliculasFavoritas().add(pelicula);
            usuarioRepositorio.save(usuario);
        }
    }

    @Override
    @Transactional
    public void eliminarFavorito(Integer peliculaId, String userEmail) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(userEmail);
        Optional<Pelicula> peliculaOptional = peliculaRepositorio.findById(peliculaId);

        if (usuarioOptional.isPresent() && peliculaOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Pelicula pelicula = peliculaOptional.get();
            usuario.getPeliculasFavoritas().remove(pelicula);
            usuarioRepositorio.save(usuario);
        }
    }
    
    @Override
    public List<Pelicula> obtenerPeliculasFavoritasDelUsuario(String userEmail) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmailWithFavoritas(userEmail);
        return usuarioOptional.map(usuario -> usuario.getPeliculasFavoritas()).orElse(List.of());
    }

	@Override
	public Comentario guardarComentario(Comentario comentario) {
		return comentarioRepositorio.save(comentario);
	}

	@Override
	public List<ComentarioDTO> obtenerComentariosPorPeliculaId(Integer peliculaId) {
		Optional<Pelicula> peliculaOptional = peliculaRepositorio.findById(peliculaId);
		if(peliculaOptional.isPresent()) {
				List<Comentario> comentarios = comentarioRepositorio.findByPelicula(peliculaOptional.get());
				return comentarios.stream().map(comentarioMapper::toComentarioDTO).collect(Collectors.toList());
		}
		return List.of();
	}

	@Override
    public void eliminarComentarioPorId(Integer comentarioId) {
        comentarioRepositorio.deleteById(comentarioId);
    }
}
