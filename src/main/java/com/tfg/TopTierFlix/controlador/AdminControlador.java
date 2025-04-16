package com.tfg.TopTierFlix.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tfg.TopTierFlix.modelo.Genero;
import com.tfg.TopTierFlix.modelo.Pelicula;
import com.tfg.TopTierFlix.repositorios.GeneroRepositorio;
import com.tfg.TopTierFlix.repositorios.PeliculaRepositorio;
import com.tfg.TopTierFlix.servicio.AlmacenServicioImpl;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
	
	@Autowired
	private PeliculaRepositorio peliculaRepositorio;
	
	@Autowired
	private GeneroRepositorio generoRepositorio;
	
	@Autowired
	private AlmacenServicioImpl servicio;
		
	@GetMapping("")
	public ModelAndView verPaginaDeInicio(@PageableDefault(sort="titulo", size=5)Pageable pageable) { //Spring Data Web -- valores por defecto: ordenar por el campo "titulo" y mostrar 5 elementos por página
		Page<Pelicula> peliculas = peliculaRepositorio.findAll(pageable); //Se crea objeto Page con peliculas aplicando paginación
		return new ModelAndView("index").addObject("peliculas", peliculas); //se devuelve objeto ModelAndView de nombre index
	}
	
	@GetMapping("/peliculas/nuevo")
	public ModelAndView mostrarFormularioDeNuevaPelicula() {
		List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
		return new ModelAndView("admin/nueva-pelicula")
				.addObject("pelicula", new Pelicula())
				.addObject("generos", generos);
	}
	
	@PostMapping("/peliculas/nuevo")
	public ModelAndView registrarPelicula(@Validated Pelicula pelicula, BindingResult bindingResult) {
		if(bindingResult.hasErrors() || pelicula.getPortada().isEmpty()) {
			if(pelicula.getPortada().isEmpty()) {
				bindingResult.rejectValue("portada", "MultipartNotEmpty");
			}
			
			List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
			return new ModelAndView("admin/nueva-pelicula")
					.addObject("pelicula", pelicula) //asigna pelicula ya existente
					.addObject("generos", generos);
		}
		
		String rutaPortada = servicio.almacenarArchivo(pelicula.getPortada());
		pelicula.setRutaPortada(rutaPortada);
		peliculaRepositorio.save(pelicula);
		return new ModelAndView("redirect:/admin");
	}
	
	
}
