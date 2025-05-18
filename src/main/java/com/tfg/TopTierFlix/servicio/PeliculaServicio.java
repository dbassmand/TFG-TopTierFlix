package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tfg.TopTierFlix.dto.PeliculaDetalleDTO;
import com.tfg.TopTierFlix.dto.ComentarioDTO;
import com.tfg.TopTierFlix.dto.PeliculaCardDTO;
import com.tfg.TopTierFlix.dto.PeliculaListadoAdminDTO;
import com.tfg.TopTierFlix.modelo.Comentario;
import com.tfg.TopTierFlix.modelo.Pelicula;

public interface PeliculaServicio {

	List<PeliculaCardDTO> obtenerPeliculasInicio();

	Page<PeliculaCardDTO> obtenerTodasPeliculasPaginado(Pageable pageable);

	PeliculaDetalleDTO obtenerPeliculaDetallePorId(Integer id);

	Page<PeliculaListadoAdminDTO> obtenerTodasPeliculasAdmin(Pageable pageable);

	Page<Pelicula> obtenerTodasPaginado(Pageable pageable);

	Pelicula obtenerPeliculaPorId(Integer id);

	Pelicula guardarPelicula(Pelicula pelicula);

	void eliminarPelicula(Pelicula pelicula);

	Page<Pelicula> buscarPeliculaPorTitulo(String termino, Pageable pageable);

	public boolean esFavorita(Integer peliculaId, String userEmail);

	void agregarFavorito(Integer peliculaId, String userEmail);

	void eliminarFavorito(Integer peliculaId, String userEmail); 
	
	List<Pelicula> obtenerPeliculasFavoritasDelUsuario(String userEmail);
	
	Comentario guardarComentario(Comentario comentario);
	
	List<ComentarioDTO> obtenerComentariosPorPeliculaId(Integer peliculaId);
	
	 void eliminarComentarioPorId(Integer comentarioId);

}
