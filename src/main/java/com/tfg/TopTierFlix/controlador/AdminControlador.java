package com.tfg.TopTierFlix.controlador;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tfg.TopTierFlix.modelo.Genero;
import com.tfg.TopTierFlix.modelo.Pelicula;
import com.tfg.TopTierFlix.servicio.AlmacenServicioImpl;
import com.tfg.TopTierFlix.servicio.GeneroServicioImpl;
import com.tfg.TopTierFlix.servicio.PeliculaServicio;


@Controller
@RequestMapping("/admin")
public class AdminControlador {
	
	@Autowired
	private PeliculaServicio peliculaServicio;
				
	@Autowired
	private GeneroServicioImpl generoServicio;
	
	@Autowired
	private AlmacenServicioImpl almacenServicio;
	

	
	@ModelAttribute("pelicula") //se vincula el formulario al objeto pelicula
	public Pelicula getPelicula(@RequestParam(required = false) Integer id) {
	    System.out.println("Preparando objeto pelicula para ID: " + id);
	    if (id != null) {
	        return peliculaServicio.obtenerPeliculaPorId(id);
	    }
	    return new Pelicula();
	}
		
	@GetMapping("")
	public ModelAndView verPaginaDeInicio(@PageableDefault(sort="titulo", size=5)Pageable pageable) { //Spring Data Web -- valores por defecto: ordenar por el campo "titulo" y mostrar 5 elementos por página
		Page<Pelicula> peliculas = peliculaServicio.obtenerTodasPaginado(pageable);                   //Se crea objeto Page con peliculas aplicando paginación
		return new ModelAndView("admin/index").addObject("peliculas", peliculas);                     //Se devuelve objeto ModelAndView de nombre index
	}
	
	@GetMapping("/buscar")
	public ModelAndView buscarPeliculas(@RequestParam(value = "termino", required = false) String termino,
										@PageableDefault(sort="titulo", size=5)Pageable pageable) {
		Page<Pelicula> resultados;
		if (termino != null && !termino.trim().isEmpty()) {
			resultados = peliculaServicio.buscarPeliculaPorTitulo(termino, pageable);
		}else {
			resultados = peliculaServicio.obtenerTodasPaginado(pageable);//me planteo que no muestre nada si no hay coincidencia de búsqueda
		}
		return new ModelAndView("admin/index")
				.addObject("peliculas",resultados)
				.addObject("terminoBusqueda",termino);
	}
	
	
	@GetMapping("/peliculas/nuevo")
	public ModelAndView mostrarFormularioDeNuevaPelicula() {
		List<Genero> generos = generoServicio.obtenerTodosGeneros(Sort.by("titulo"));
		return new ModelAndView("admin/nueva-pelicula")
				.addObject("pelicula", new Pelicula())
				.addObject("generos", generos);
	}
	
	@PostMapping("/peliculas/nuevo")
	public ModelAndView registrarPelicula(@Validated @ModelAttribute("pelicula") Pelicula pelicula, BindingResult bindingResult) {
		System.out.println("Se recibió una petición POST a /peliculas/nuevo");
		if(bindingResult.hasErrors() || pelicula.getPortada().isEmpty()) {
			System.out.println(bindingResult);
			if(pelicula.getPortada().isEmpty()) {
				bindingResult.rejectValue("portada", "MultipartNotEmpty");
			}
			
			List<Genero> generos = generoServicio.obtenerTodosGeneros(Sort.by("titulo"));//Se aplica criterio de ordenación
			return new ModelAndView("admin/nueva-pelicula")
					.addObject("pelicula", pelicula) //asigna pelicula ya existente
					.addObject("generos", generos);
		}
		
		String rutaPortada = almacenServicio.almacenarArchivo(pelicula.getPortada());
		pelicula.setRutaPortada(rutaPortada);
		peliculaServicio.guardarPelicula(pelicula);
		return new ModelAndView("redirect:/admin");
	}
	
	@GetMapping("/peliculas/{id}/editar")
	public ModelAndView mostrarFormilarioDeEditarPelicula(@PathVariable Integer id) {
		
		Pelicula pelicula = peliculaServicio.obtenerPeliculaPorId(id);
		List<Genero> generos = generoServicio.obtenerTodosGeneros(Sort.by("titulo"));
		
		return new ModelAndView("admin/editar-pelicula")
				.addObject("pelicula",pelicula)
				.addObject("generos",generos);
	}
	
	@PostMapping("/peliculas/{id}/editar")
	public ModelAndView actualizarPelicula(
	    @PathVariable Integer id,
	    @Validated @ModelAttribute("pelicula") Pelicula pelicula,
	    BindingResult bindingResult,
	    @RequestParam(value = "portada", required = false) MultipartFile portada) { 

	    System.out.println("Datos recibidos - Título: " + pelicula.getTitulo()); //Se añade control en consola para verificar si llegan las validaciones del formulario
	    
	    if (bindingResult.hasErrors()) {
	        System.out.println("Errores encontrados:");
	        bindingResult.getAllErrors().forEach(System.out::println);
	        
	        List<Genero> generos = generoServicio.obtenerTodosGeneros(Sort.by("titulo"));
	        //List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
	        return new ModelAndView("admin/editar-pelicula")
	                .addObject("pelicula", pelicula)
	                .addObject("generos", generos);
	    }
		
		
	    Pelicula peliculaDB = peliculaServicio.obtenerPeliculaPorId(id);
		peliculaDB.setTitulo(pelicula.getTitulo());
		peliculaDB.setSinopsis(pelicula.getSinopsis());
		peliculaDB.setFechaEstreno(pelicula.getFechaEstreno());
		peliculaDB.setYoutubeTrailerId(pelicula.getYoutubeTrailerId());
		peliculaDB.setGeneros(pelicula.getGeneros());
		
		if(!pelicula.getPortada().isEmpty()) {
			almacenServicio.eliminarArchivo(peliculaDB.getRutaPortada());
			String rutaPortada = almacenServicio.almacenarArchivo(pelicula.getPortada());
			peliculaDB.setRutaPortada(rutaPortada);
		}
		
		
		peliculaServicio.guardarPelicula(peliculaDB);
		return new ModelAndView("redirect:/admin");
	}
	
	@PostMapping("/peliculas/{id}/eliminar")
	public String eliminarPelicula(@PathVariable Integer id) {
		Pelicula pelicula = peliculaServicio.obtenerPeliculaPorId(id);
		peliculaServicio.eliminarPelicula(pelicula);
		almacenServicio.eliminarArchivo(pelicula.getRutaPortada());		
		return "redirect:/admin";
	}
	
	@Secured("ROLE_ADMIN") // Asegura que solo los usuarios con el rol ADMIN puedan acceder
	@PostMapping("/peliculas/{peliculaId}/comentarios/{comentarioId}/eliminar")
    public String eliminarComentario(@PathVariable Integer peliculaId,
                                     @PathVariable Integer comentarioId,
                                     RedirectAttributes redirectAttributes) {
        peliculaServicio.eliminarComentarioPorId(comentarioId);
        redirectAttributes.addFlashAttribute("mensaje", "Comentario eliminado correctamente.");
        return "redirect:/peliculas/" + peliculaId;
    }
}
