package com.tfg.TopTierFlix.repositorios;

import com.tfg.TopTierFlix.modelo.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email); // Cambiado el tipo de retorno a Optional<Usuario>, para manejar una posible ausencia de valor, mejor que con null.
    
       
    @Query("SELECT u FROM Usuario u WHERE " +
            "LOWER(u.nombre) LIKE %:termino% OR " +
            "LOWER(u.apellido) LIKE %:termino% OR " +
            "LOWER(u.email) LIKE %:termino%")
     Page<Usuario> buscarPorNombreApellidoEmail(@Param("termino") String termino, Pageable pageable);
 
    
 
    @Query("SELECT u FROM Usuario u JOIN FETCH u.peliculasFavoritas pf WHERE u.email = :email")
    Optional<Usuario> findByEmailWithFavoritas(@Param("email") String email);
    
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.peliculasFavoritas pf WHERE u.id = :id")
    Optional<Usuario> findByIdWithFavoritas(@Param("id") Integer id);
}