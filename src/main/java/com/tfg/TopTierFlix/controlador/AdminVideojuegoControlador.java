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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tfg.TopTierFlix.modelo.GeneroVideojuego;
import com.tfg.TopTierFlix.modelo.Videojuego;
import com.tfg.TopTierFlix.servicio.AlmacenServicioImpl;
import com.tfg.TopTierFlix.servicio.GeneroVideojuegoServicioImpl;
import com.tfg.TopTierFlix.servicio.VideojuegoServicio;

@Controller
@RequestMapping("/admin/videojuegos")
public class AdminVideojuegoControlador {

	@Autowired
	private VideojuegoServicio videojuegoServicio;

	@Autowired
	private GeneroVideojuegoServicioImpl generoVideojuegoServicio;

	@Autowired
	private AlmacenServicioImpl almacenServicio;

	@ModelAttribute("videojuego")
	public Videojuego getVideojuego(@RequestParam(required = false) Integer id) {
		if (id != null) {
			return videojuegoServicio.obtenerVideojuegoPorId(id);
		}
		return new Videojuego();
	}

	@GetMapping("") // Mapea a /admin/videojuegos
	public ModelAndView listarVideojuegosAdmin(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
		Page<Videojuego> videojuegos = videojuegoServicio.obtenerTodasPaginado(pageable);
		return new ModelAndView("admin/videojuegos/index").addObject("videojuegos", videojuegos);
	}

	@GetMapping("/buscar") // Mapea a /admin/videojuegos/buscar
	public ModelAndView buscarVideojuegosAdmin(@RequestParam(value = "termino", required = false) String termino,
			@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
		Page<Videojuego> resultados;
		if (termino != null && !termino.trim().isEmpty()) {
			resultados = videojuegoServicio.buscarVideojuegoPorTitulo(termino, pageable);
		} else {
			resultados = videojuegoServicio.obtenerTodasPaginado(pageable);
		}
		return new ModelAndView("admin/videojuegos/index").addObject("videojuegos", resultados)
				.addObject("terminoBusqueda", termino);
	}

	@GetMapping("/nuevo") 
	public ModelAndView mostrarFormularioDeNuevaVideojuego() {
		List<GeneroVideojuego> generosVideojuego = generoVideojuegoServicio.obtenerTodosGeneros(Sort.by("nombre")); // CAMBIO DE NOMBRE DE VARIABLE PARA CLARIDAD
		return new ModelAndView("admin/videojuegos/nueva-videojuego")
				.addObject("videojuego", new Videojuego())
				.addObject("generosVideojuego", generosVideojuego);
	}

	@PostMapping("/nuevo") 
	public ModelAndView registrarVideojuego(@Validated @ModelAttribute("videojuego") Videojuego videojuego,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors() || videojuego.getPortada().isEmpty()) {
			System.out.println(bindingResult);
			if (videojuego.getPortada().isEmpty()) {
				bindingResult.rejectValue("portada", "MultipartNotEmpty");
			}

			List<GeneroVideojuego> generosVideojuego = generoVideojuegoServicio.obtenerTodosGeneros(Sort.by("nombre")); // CAMBIO DE NOMBRE DE VARIABLE PARA CLARIDAD
			return new ModelAndView("admin/videojuegos/nueva-videojuego").addObject("videojuego", videojuego)
					.addObject("generosVideojuego", generosVideojuego); // <--- ¡UNIFICADO A "generosVideojuego"!
		}

		String rutaPortada = almacenServicio.almacenarArchivo(videojuego.getPortada());
		videojuego.setRutaPortada(rutaPortada);
		videojuegoServicio.guardarVideojuego(videojuego);
		return new ModelAndView("redirect:/admin/videojuegos");
	}

	@GetMapping("/{id}/editar") // Mapea a /admin/videojuegos/{id}/editar
	public ModelAndView mostrarFormularioDeEditarvideojuego(@PathVariable Integer id) {

		Videojuego videojuego = videojuegoServicio.obtenerVideojuegoPorId(id);
		// CAMBIO AQUÍ: Nombre del atributo del modelo para la lista de géneros
		List<GeneroVideojuego> generosVideojuego = generoVideojuegoServicio.obtenerTodosGeneros(Sort.by("nombre"));

		return new ModelAndView("admin/videojuegos/editar-videojuego")
				.addObject("videojuego", videojuego)
				.addObject("generosVideojuego", generosVideojuego); // <--- ¡UNIFICADO A "generosVideojuego"!
	}

	@PostMapping("/{id}/editar") // Mapea a /admin/videojuegos/{id}/editar (POST)
	public ModelAndView actualizarVideojuego(@PathVariable Integer id,
			@Validated @ModelAttribute("videojuego") Videojuego videojuego, BindingResult bindingResult,
			@RequestParam(value = "portada", required = false) MultipartFile portada) {

		System.out.println("Datos recibidos - Título: " + videojuego.getTitulo());

		if (bindingResult.hasErrors()) {
			System.out.println("Errores encontrados:");
			bindingResult.getAllErrors().forEach(System.out::println);
			
			List<GeneroVideojuego> generosVideojuego = generoVideojuegoServicio.obtenerTodosGeneros(Sort.by("nombre"));
			return new ModelAndView("admin/videojuegos/editar-videojuego").addObject("videojuego", videojuego)
					.addObject("generosVideojuego", generosVideojuego); // <--- ¡UNIFICADO A "generosVideojuego"!
		}

		Videojuego videojuegoDB = videojuegoServicio.obtenerVideojuegoPorId(id);
		videojuegoDB.setTitulo(videojuego.getTitulo());
		videojuegoDB.setDescripcion(videojuego.getDescripcion());
		videojuegoDB.setFechaEstreno(videojuego.getFechaEstreno());
		videojuegoDB.setYoutubeTrailerId(videojuego.getYoutubeTrailerId());
		videojuegoDB.setGeneros(videojuego.getGeneros());

		if (!videojuego.getPortada().isEmpty()) {
			almacenServicio.eliminarArchivo(videojuegoDB.getRutaPortada());
			String rutaPortada = almacenServicio.almacenarArchivo(videojuego.getPortada());
			videojuegoDB.setRutaPortada(rutaPortada);
		}

		videojuegoServicio.guardarVideojuego(videojuegoDB);
		return new ModelAndView("redirect:/admin/videojuegos");
	}

	@PostMapping("/{id}/eliminar") // Mapea a /admin/videojuegos/{id}/eliminar
	public String eliminarVideojuego(@PathVariable Integer id) {
		Videojuego videojuego = videojuegoServicio.obtenerVideojuegoPorId(id);
		videojuegoServicio.eliminarVideojuego(videojuego);
		almacenServicio.eliminarArchivo(videojuego.getRutaPortada());
		return "redirect:/admin/videojuegos";
	}

	// Mapea a /admin/videojuegos/{videojuegoId}/comentarios/{comentarioId}/eliminar
	@PostMapping("/{videojuegoId}/comentarios/{comentarioId}/eliminar")
	public String eliminarComentario(@PathVariable Integer videojuegoId, @PathVariable Integer comentarioId,
			RedirectAttributes redirectAttributes) {
		videojuegoServicio.eliminarComentarioPorId(comentarioId);
		redirectAttributes.addFlashAttribute("mensaje", "Comentario eliminado correctamente.");
		return "redirect:/videojuegos/" + videojuegoId;
	}
}