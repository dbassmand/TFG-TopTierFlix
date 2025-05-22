package com.tfg.TopTierFlix.repositorios;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.TopTierFlix.modelo.Musica;

public interface MusicaRepositorio extends JpaRepository<Musica, Integer> {

	Page<Musica> findBytituloContainingIgnoreCase(String termino, Pageable pageable);

	List<Musica> findTop4ByOrderByFechaEstrenoDesc();

}
