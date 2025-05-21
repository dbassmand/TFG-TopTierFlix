package com.tfg.TopTierFlix.controlador;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.tfg.TopTierFlix.dto.VideojuegoCardDTO;
import com.tfg.TopTierFlix.dto.VideojuegoDetalleDTO;
import com.tfg.TopTierFlix.modelo.Comentario;
import com.tfg.TopTierFlix.modelo.ComentarioVideojuego;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.modelo.Videojuego;
import com.tfg.TopTierFlix.servicio.UsuarioServicio;
import com.tfg.TopTierFlix.servicio.VideojuegoServicio;

@Controller
@RequestMapping("/videojuegos")
public class VideojuegoControlador {

	@Autowired
	private VideojuegoServicio videojuegoServicio;
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@GetMapping
    public ModelAndView listarvideojuegos(@RequestParam(value="page", defaultValue = "0")int page,
			@PageableDefault(size = 8, sort = "fechaEstreno", direction = Sort.Direction.DESC) Pageable pageable) {
    	Page<VideojuegoCardDTO> videojuegoPageDTO = videojuegoServicio.obtenerTodasVideojuegosPaginado(pageable);
    	return new ModelAndView("videojuegos/videojuegos").addObject("videojuegos",videojuegoPageDTO);
    }

    @GetMapping("/buscar")
    public ModelAndView buscarVideojuegosUsuarios(@RequestParam(value = "termino", required = false) String termino,
											@PageableDefault(sort="fechaEstreno", size=8, direction = Sort.Direction.DESC)Pageable pageable) {
    	Page<Videojuego> resultados;
    	if(termino !=null && !termino.trim().isEmpty()) {
    		resultados = videojuegoServicio.buscarVideojuegoPorTitulo(termino, pageable);
    	}else {
    		resultados = videojuegoServicio.obtenerTodasPaginado(pageable);
    	}
    	return new ModelAndView("videojuegos/videojuegos")
    			.addObject("videojuegos",resultados)
    			.addObject("terminoBusqueda",termino);
    }
    
    @GetMapping("/{id}")
    public ModelAndView mostrarDetallesDevideojuego(@PathVariable Integer id, Principal principal) {
    	VideojuegoDetalleDTO videojuegoDetalleDTO = videojuegoServicio.obtenerVideojuegoDetallePorId(id);
    	ModelAndView modelAnView = new ModelAndView("videojuegos/videojuego")
    			.addObject("videojuego",videojuegoDetalleDTO)
    			.addObject("comentarios", videojuegoServicio.obtenerComentariosPorVideojuegoId(id))
    			.addObject("nuevoComentario", new Comentario());
    	if (principal !=null) {
    		String userEmail = principal.getName();
    		boolean esFavorita = videojuegoServicio.esFavorita(id, userEmail);
    		modelAnView.addObject("esFavorita", esFavorita);
    	}else {
    		modelAnView.addObject("esFavorita", false);
    	}
    	return modelAnView;
    }
    
    @PostMapping("/{videojuegoId}/comentar")
    public String guardarNuevoComentario(@PathVariable Integer videojuegoId,
    									 @ModelAttribute("nuevoComentario")ComentarioVideojuego comentariovideojuego, 
    									 Principal principal, 
    									 Model model) {
    	String usuarioEmail = principal.getName();
    	Optional<Usuario> usuarioOptional = usuarioServicio.obtenerUsuarioPorEmail(usuarioEmail);
    	
    	Videojuego videojuego = videojuegoServicio.obtenerVideojuegoPorId(videojuegoId);
    	
    	if(usuarioOptional.isPresent()) {
    		Usuario usuario = usuarioOptional.get();
    		
    		comentariovideojuego.setVideojuego(videojuego);
    		comentariovideojuego.setUsuario(usuario);
    		
    		videojuegoServicio.guardarComentario(comentariovideojuego);
    		return "redirect:/videojuegos/"+videojuegoId;
    	}else {
    		model.addAttribute("error", "No se pudo guardar el comentario. El usuario no existe.");
    	    return "error/error-generico";
    	}
    }
    
    @PostMapping("/{id}/favorito")
    public String toggleFavorito(@PathVariable Integer id, Principal principal) {
    	if(principal !=null) {
    		String userEmail = principal.getName();
    		if(videojuegoServicio.esFavorita(id, userEmail)) {
    		videojuegoServicio.eliminarFavorito(id, userEmail);
    		}else {
    			videojuegoServicio.agregarFavorito(id, userEmail);
    		}    		
    	}
    	return "redirect:/videojuegos/"+ id;
    }
	
}
