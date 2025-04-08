package com.tfg.TopTierFlix.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.TopTierFlix.modelo.Pelicula;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Integer>{

}
