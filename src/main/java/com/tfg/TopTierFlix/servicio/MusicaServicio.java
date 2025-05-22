package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.tfg.TopTierFlix.dto.ComentarioMusicaDTO;
import com.tfg.TopTierFlix.dto.MusicaCardDTO;
import com.tfg.TopTierFlix.dto.MusicaDetalleDTO;
import com.tfg.TopTierFlix.dto.MusicaListadoAdminDTO;
import com.tfg.TopTierFlix.modelo.ComentarioMusica;
import com.tfg.TopTierFlix.modelo.Musica;


public interface MusicaServicio {
	
	//List<MusicaCardDTO> obtenerMusicaIncio();

	Page<MusicaCardDTO> obtenerTodasMusicasPaginado(Pageable pageable);

	MusicaDetalleDTO obtenerMusicaDetallePorId(Integer id);

	Page<MusicaListadoAdminDTO> obtenerTodasMusicasAdmin(Pageable pageable);

	Page<Musica> obtenerTodasPaginado(Pageable pageable);

	Musica obtenerMusicaPorId(Integer id);

	Musica guardarMusica(Musica musica);

	void eliminarMusica(Musica musica);

	Page<Musica> buscarMusicaPorTitulo(String termino, Pageable pageable);

	public boolean esFavorita(Integer musicaId, String userEmail);

	void agregarFavorito(Integer musicaId, String userEmail);

	void eliminarFavorito(Integer musicaId, String userEmail);

	List<Musica> obtenerMusicasFavoritasDelUsuario(String userEmail);

	ComentarioMusica guardarComentario(ComentarioMusica comentarioMusica);

	List<ComentarioMusicaDTO> obtenerComentariosPorMusicaId(Integer musicaId);

	void eliminarComentarioPorId(Integer comentarioId);

}
