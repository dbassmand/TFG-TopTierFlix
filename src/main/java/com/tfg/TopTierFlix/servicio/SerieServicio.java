package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.tfg.TopTierFlix.dto.ComentarioSerieDTO;
import com.tfg.TopTierFlix.dto.SerieCardDTO;
import com.tfg.TopTierFlix.dto.SerieDetalleDTO;
import com.tfg.TopTierFlix.dto.SerieListadoAdminDTO;
import com.tfg.TopTierFlix.modelo.ComentarioSerie;
import com.tfg.TopTierFlix.modelo.Serie;

public interface SerieServicio {
	
	List<SerieCardDTO> obtenerSeriesIncio();
	
	Page<SerieCardDTO> obtenerTodasSeriesPaginado(Pageable pageable); 
	
	SerieDetalleDTO obtenerSerieDetallePorId(Integer id);
	
	Page<SerieListadoAdminDTO> obtenerTodasSeriesAdmin(Pageable pageable);
	
	Page<Serie> obtenerTodasPaginado (Pageable pageable);
	
	Serie obtenerSeriePorId(Integer id);
	
	Serie guardarSerie(Serie serie);
	
	void eliminarSerie(Serie serie);
	
	Page<Serie> buscarSeriePorTitulo(String termino, Pageable pageable);
	
	public boolean esFavorita(Integer serieId, String userEmail);
	
	void agregarFavorito(Integer serieId, String userEmail);
	
	void eliminarFavorito(Integer serieId, String userEmail);
	
	List<Serie> obtenerSeriesFavoritasDelUsuario(String userEmail);
	
	ComentarioSerie guardarComentario(ComentarioSerie comentarioSerie);
	
	List<ComentarioSerieDTO> obtenerComentariosPorSerieId(Integer serieId);
	
	void eliminarComentarioPorId(Integer comentarioId);

}
