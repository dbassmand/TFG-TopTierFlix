package com.tfg.TopTierFlix.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.TopTierFlix.modelo.Serie;



public interface SerieRepositorio extends JpaRepository<Serie, Integer>{

	Page<Serie> findBytituloContainingIgnoreCase(String termino, Pageable pageable);

	//Para obtener las 4 películas más recientes SIN filtro de término
	 List<Serie> findTop4ByOrderByFechaEstrenoDesc();
	
}
