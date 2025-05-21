package com.tfg.TopTierFlix.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.TopTierFlix.modelo.Videojuego;

public interface VideojuegoRepositorio extends JpaRepository<Videojuego, Integer> {

	Page<Videojuego> findBytituloContainingIgnoreCase(String termino, Pageable pageable);

	List<Videojuego> findTop4ByOrderByFechaEstrenoDesc();

}
