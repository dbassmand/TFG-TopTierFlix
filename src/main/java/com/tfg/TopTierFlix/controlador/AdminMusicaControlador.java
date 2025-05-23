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
import com.tfg.TopTierFlix.modelo.GeneroMusica;
import com.tfg.TopTierFlix.modelo.Musica;
import com.tfg.TopTierFlix.servicio.AlmacenServicioImpl;
import com.tfg.TopTierFlix.servicio.GeneroMusicaServicioImpl;
import com.tfg.TopTierFlix.servicio.MusicaServicio;


@Controller
@RequestMapping("/admin/musicas")
public class AdminMusicaControlador {
	
	@Autowired
	private MusicaServicio musicaServicio;

	@Autowired
	private GeneroMusicaServicioImpl generoMusicaServicio;

	@Autowired
	private AlmacenServicioImpl almacenServicio;

	@ModelAttribute("musica")
	public Musica getMusica(@RequestParam(required = false) Integer id) {
		if (id != null) {
			return musicaServicio.obtenerMusicaPorId(id);
		}
		return new Musica();
	}

	@GetMapping("")
	public ModelAndView listarMusicaAdmin(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
		Page<Musica> musica = musicaServicio.obtenerTodasPaginado(pageable);
		return new ModelAndView("admin/musicas/index").addObject("musicas", musica);
	}

	@GetMapping("/buscar") 
	public ModelAndView buscarMusicaAdmin(@RequestParam(value = "termino", required = false) String termino,
			@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
		Page<Musica> resultados;
		if (termino != null && !termino.trim().isEmpty()) {
			resultados = musicaServicio.buscarMusicaPorTitulo(termino, pageable);
		} else {
			resultados = musicaServicio.obtenerTodasPaginado(pageable);
		}
		return new ModelAndView("admin/musicas/index").addObject("musicas", resultados)
				.addObject("terminoBusqueda", termino);
	}

	@GetMapping("/nuevo") 
	public ModelAndView mostrarFormularioDeNuevaMusica() {
		List<GeneroMusica> generosMusica = generoMusicaServicio.obtenerTodosGeneros(Sort.by("nombre")); 
		return new ModelAndView("admin/musicas/nueva-musica")
				.addObject("musica", new Musica())
				.addObject("generosMusica", generosMusica); 
	}

	@PostMapping("/nuevo") 
	public ModelAndView registrarMusica(@Validated @ModelAttribute("musica") Musica musica,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors() || musica.getPortada().isEmpty()) {
			System.out.println(bindingResult);
			if (musica.getPortada().isEmpty()) {
				bindingResult.rejectValue("portada", "MultipartNotEmpty");
			}

			List<GeneroMusica> generosMusica = generoMusicaServicio.obtenerTodosGeneros(Sort.by("nombre")); 
			return new ModelAndView("admin/musicas/nueva-musica").addObject("musica", musica)
					.addObject("generosMusica", generosMusica); 
		}

		String rutaPortada = almacenServicio.almacenarArchivo(musica.getPortada());
		musica.setRutaPortada(rutaPortada);
		musicaServicio.guardarMusica(musica);
		return new ModelAndView("redirect:/admin/musicas");
	}

	@GetMapping("/{id}/editar") 
	public ModelAndView mostrarFormularioDeEditarmusica(@PathVariable Integer id) {

		Musica musica = musicaServicio.obtenerMusicaPorId(id);		
		List<GeneroMusica> generosMusica = generoMusicaServicio.obtenerTodosGeneros(Sort.by("nombre"));
		return new ModelAndView("admin/musicas/editar-musica")
				.addObject("musica", musica)
				.addObject("generosMusica", generosMusica); 
	}

	@PostMapping("/{id}/editar") 
	public ModelAndView actualizarMusica(@PathVariable Integer id,
			@Validated @ModelAttribute("musica") Musica musica, BindingResult bindingResult,
			@RequestParam(value = "portada", required = false) MultipartFile portada) {

		System.out.println("Datos recibidos - Título: " + musica.getTitulo());

		if (bindingResult.hasErrors()) {
			System.out.println("Errores encontrados:");
			bindingResult.getAllErrors().forEach(System.out::println);
			
			List<GeneroMusica> generosMusica = generoMusicaServicio.obtenerTodosGeneros(Sort.by("nombre"));
			return new ModelAndView("admin/musicas/editar-musica").addObject("musica", musica)
					.addObject("generosMusica", generosMusica); // 
		}

		Musica musicaDB = musicaServicio.obtenerMusicaPorId(id);
		musicaDB.setTitulo(musica.getTitulo());
		musicaDB.setDescripcion(musica.getDescripcion());
		musicaDB.setFechaEstreno(musica.getFechaEstreno());
		musicaDB.setYoutubeTrailerId(musica.getYoutubeTrailerId());
		musicaDB.setGeneros(musica.getGeneros());

		if (!musica.getPortada().isEmpty()) {
			almacenServicio.eliminarArchivo(musicaDB.getRutaPortada());
			String rutaPortada = almacenServicio.almacenarArchivo(musica.getPortada());
			musicaDB.setRutaPortada(rutaPortada);
		}

		musicaServicio.guardarMusica(musicaDB);
		return new ModelAndView("redirect:/admin/musicas");
	}

	@PostMapping("/{id}/eliminar") 
	public String eliminarMusica(@PathVariable Integer id) {
		Musica musica = musicaServicio.obtenerMusicaPorId(id);
		musicaServicio.eliminarMusica(musica);
		almacenServicio.eliminarArchivo(musica.getRutaPortada());
		return "redirect:/admin/musicas";
	}
	
	@PostMapping("/{musicaId}/comentarios/{comentarioId}/eliminar")
	public String eliminarComentario(@PathVariable Integer musicaId, @PathVariable Integer comentarioId,
			RedirectAttributes redirectAttributes) {
		musicaServicio.eliminarComentarioPorId(comentarioId);
		redirectAttributes.addFlashAttribute("mensaje", "Comentario eliminado correctamente.");
		return "redirect:/musicas/" + musicaId;
	}

}
