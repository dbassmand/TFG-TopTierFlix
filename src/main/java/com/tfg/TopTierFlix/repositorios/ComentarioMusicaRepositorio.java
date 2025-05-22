package com.tfg.TopTierFlix.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.TopTierFlix.modelo.ComentarioMusica;
import com.tfg.TopTierFlix.modelo.Musica;
import com.tfg.TopTierFlix.modelo.Usuario;


public interface ComentarioMusicaRepositorio extends JpaRepository<ComentarioMusica, Integer> {
	
    List<ComentarioMusica> findByMusica(Musica musica);
    
    List<ComentarioMusica> findByUsuario(Usuario usuario);
   
    List<ComentarioMusica> findByMusicaAndUsuario(Musica musica, Usuario usuario);
}
