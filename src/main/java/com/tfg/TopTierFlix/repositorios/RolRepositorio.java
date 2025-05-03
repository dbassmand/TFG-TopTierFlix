package com.tfg.TopTierFlix.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.TopTierFlix.modelo.Rol;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Integer> { 

    Rol findByNombre(String nombre);
}