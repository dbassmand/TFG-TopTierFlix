package com.tfg.TopTierFlix.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.tfg.TopTierFlix.dto.PeliculaDetalleDTO;
import com.tfg.TopTierFlix.dto.PeliculaCardDTO;
import com.tfg.TopTierFlix.modelo.Pelicula;
//import com.tfg.TopTierFlix.repositorios.PeliculaRepositorio;
import com.tfg.TopTierFlix.servicio.PeliculaServicio;

@Controller
@RequestMapping("")
public class HomeControlador {
	
	
	
	@Autowired
	private PeliculaServicio peliculaServicio;
	
	@GetMapping
	public ModelAndView verPaginaInicio() {
		
        List<PeliculaCardDTO> ultimasPeliculasInicioDTO = peliculaServicio.obtenerPeliculasInicio();		
		return new ModelAndView("index").addObject("ultimasPeliculas", ultimasPeliculasInicioDTO);
	}
	
	@GetMapping("/peliculas")
	public ModelAndView listarPeliculas(@RequestParam(value = "page", defaultValue = "0") int page,
			@PageableDefault(size = 8, sort = "fechaEstreno", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<PeliculaCardDTO> peliculasPageDTO = peliculaServicio.obtenerTodasPeliculasPaginado(pageable);
		return new ModelAndView("peliculas").addObject("peliculas", peliculasPageDTO);
	}
	
	@GetMapping("/peliculas/buscar")
	public ModelAndView buscarPeliculasUsuario(@RequestParam(value = "termino", required = false) String termino,
										@PageableDefault(sort="fechaEstreno", size=8, direction = Sort.Direction.DESC)Pageable pageable) {
		Page<Pelicula> resultados;
		if (termino != null && !termino.trim().isEmpty()) {
			resultados = peliculaServicio.buscarPeliculaPorTitulo(termino, pageable);
		}else {
			
			resultados = peliculaServicio.obtenerTodasPaginado(pageable);
		}
		return new ModelAndView("peliculas")
				.addObject("peliculas",resultados)
				.addObject("terminoBusqueda",termino);
	}

	@GetMapping("/peliculas/{id}")
	public ModelAndView mostrarDetallesDePelicula(@PathVariable Integer id) {
		PeliculaDetalleDTO peliculaDetalleDTO = peliculaServicio.obtenerPeliculaDetallePorId(id);		
		return new ModelAndView("pelicula").addObject("pelicula",peliculaDetalleDTO);
	}
}
