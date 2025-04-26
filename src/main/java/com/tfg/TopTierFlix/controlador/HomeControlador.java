package com.tfg.TopTierFlix.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tfg.TopTierFlix.modelo.Pelicula;
import com.tfg.TopTierFlix.repositorios.PeliculaRepositorio;

@Controller
@RequestMapping("")
public class HomeControlador {
	
	@Autowired
	private PeliculaRepositorio peliculaRepositorio;
	
	@GetMapping
	public ModelAndView verPaginaInicio() {
		List<Pelicula> ultimasPeliculas = peliculaRepositorio.findAll(PageRequest.of(0, 4, Sort.by("fechaEstreno").descending())).toList();
		return new ModelAndView("index").addObject("ultimasPeliculas", ultimasPeliculas);
	}

	//version mejorada del controlador para usar paginador
	@GetMapping("/peliculas")
	public ModelAndView listarPeliculas(@RequestParam(value = "page", defaultValue = "0") int page,
	                                    @PageableDefault(size = 8, sort = "fechaEstreno", direction = Sort.Direction.DESC) Pageable pageable) {
	    Page<Pelicula> peliculas = peliculaRepositorio.findAll(PageRequest.of(page, pageable.getPageSize(), pageable.getSort()));
	    return new ModelAndView("peliculas").addObject("peliculas", peliculas);
	}		

	@GetMapping("/peliculas/{id}")
	public ModelAndView mostrarDetallesDePelicula(@PathVariable Integer id) {
		Pelicula pelicula = peliculaRepositorio.getReferenceById(id);
		//Pelicula pelicula = peliculaRepositorio.getOne(id);
		return new ModelAndView("pelicula").addObject("pelicula",pelicula);
	}
}
