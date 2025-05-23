package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.tfg.TopTierFlix.dto.ComentarioVideojuegoDTO;
import com.tfg.TopTierFlix.dto.VideojuegoCardDTO;
import com.tfg.TopTierFlix.dto.VideojuegoDetalleDTO;
import com.tfg.TopTierFlix.dto.VideojuegoListadoAdminDTO;
import com.tfg.TopTierFlix.modelo.ComentarioVideojuego;
import com.tfg.TopTierFlix.modelo.Videojuego;

public interface VideojuegoServicio {

	List<VideojuegoCardDTO> obtenerVideojuegoIncio();

	Page<VideojuegoCardDTO> obtenerTodasVideojuegosPaginado(Pageable pageable);

	VideojuegoDetalleDTO obtenerVideojuegoDetallePorId(Integer id);

	Page<VideojuegoListadoAdminDTO> obtenerTodasVideojuegosAdmin(Pageable pageable);

	Page<Videojuego> obtenerTodasPaginado(Pageable pageable);

	Videojuego obtenerVideojuegoPorId(Integer id);

	Videojuego guardarVideojuego(Videojuego videojuego);

	void eliminarVideojuego(Videojuego videojuego);

	Page<Videojuego> buscarVideojuegoPorTitulo(String termino, Pageable pageable);

	public boolean esFavorita(Integer videojuegoId, String userEmail);

	void agregarFavorito(Integer videojuegoId, String userEmail);

	void eliminarFavorito(Integer videojuegoId, String userEmail);

	List<Videojuego> obtenerVideojuegosFavoritasDelUsuario(String userEmail);

	ComentarioVideojuego guardarComentario(ComentarioVideojuego comentarioVideojuego);	
	
	List<ComentarioVideojuegoDTO> obtenerComentariosPorVideojuegoId(Integer videojuegoId);

	void eliminarComentarioPorId(Integer comentarioId);
}
