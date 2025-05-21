package com.tfg.TopTierFlix.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.TopTierFlix.modelo.ComentarioSerie;
import com.tfg.TopTierFlix.modelo.Serie;
import com.tfg.TopTierFlix.modelo.Usuario;


	public interface ComentarioSerieRepositorio extends JpaRepository<ComentarioSerie, Integer> {


		// Método para encontrar comentarios por una Serie específica
	    List<ComentarioSerie> findBySerie(Serie serie);

	    // Opcional: Método para encontrar comentarios por un Usuario específico 
	    List<ComentarioSerie> findByUsuario(Usuario usuario);

	    // Opcional: Método para encontrar comentarios por una Serie y un Usuario 
	    List<ComentarioSerie> findBySerieAndUsuario(Serie serie, Usuario usuario);
	}
