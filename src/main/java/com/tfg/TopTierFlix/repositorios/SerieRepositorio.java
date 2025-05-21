package com.tfg.TopTierFlix.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.TopTierFlix.modelo.Serie;



public interface SerieRepositorio extends JpaRepository<Serie, Integer>{

	Page<Serie> findBytituloContainingIgnoreCase(String termino, Pageable pageable);

	
	 List<Serie> findTop4ByOrderByFechaEstrenoDesc();
	
}
