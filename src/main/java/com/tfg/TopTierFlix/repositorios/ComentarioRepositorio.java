package com.tfg.TopTierFlix.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.TopTierFlix.modelo.Comentario;
import com.tfg.TopTierFlix.modelo.Pelicula;



public interface ComentarioRepositorio extends JpaRepository<Comentario, Integer>{
	
	 List<Comentario> findByPelicula(Pelicula pelicula);

}
