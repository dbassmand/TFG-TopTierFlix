/*se implementa la funcionalidad "Query Methods". Siguiendo ciertas convenciones de nomenclatura en los nombres de los métodos 
 * de un repositorio, Spring Data JPA automáticamente interpreta estos nombres y genera la consulta SQL correspondiente para 
 * interactuar con la base de datos.
 */

package com.tfg.TopTierFlix.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tfg.TopTierFlix.modelo.Pelicula;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Integer>{
	
	
 Page<Pelicula> findBytituloContainingIgnoreCase(String termino, Pageable pageable);

//Para obtener las 4 películas más recientes SIN filtro de término
 List<Pelicula> findTop4ByOrderByFechaEstrenoDesc();
  
}
