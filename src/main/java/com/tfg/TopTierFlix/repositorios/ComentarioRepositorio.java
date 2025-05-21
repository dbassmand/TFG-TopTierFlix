package com.tfg.TopTierFlix.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.TopTierFlix.modelo.Comentario;
import com.tfg.TopTierFlix.modelo.Pelicula;
import com.tfg.TopTierFlix.modelo.Usuario;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Integer> {

	List<Comentario> findByPelicula(Pelicula pelicula);
	
	List<Comentario> findByUsuario(Usuario usuario);
	
	List<Comentario> findByPeliculaAndUsuario(Pelicula pelicula, Usuario usuario);

}
