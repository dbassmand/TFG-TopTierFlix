package com.tfg.TopTierFlix.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.TopTierFlix.modelo.ComentarioSerie;
import com.tfg.TopTierFlix.modelo.ComentarioVideojuego;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.modelo.Videojuego;

public interface ComentarioVideojuegoRepositorio extends JpaRepository<ComentarioVideojuego, Integer> {


	
    List<ComentarioVideojuego> findByVideojuego(Videojuego videojuego);
    
    List<ComentarioVideojuego> findByUsuario(Usuario usuario);
   
    List<ComentarioVideojuego> findByVideojuegoAndUsuario(Videojuego videojuego, Usuario usuario);

}
