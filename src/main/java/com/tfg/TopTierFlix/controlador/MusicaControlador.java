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
import com.tfg.TopTierFlix.dto.MusicaCardDTO;
import com.tfg.TopTierFlix.dto.MusicaDetalleDTO;
import com.tfg.TopTierFlix.modelo.Comentario;
import com.tfg.TopTierFlix.modelo.ComentarioMusica;
import com.tfg.TopTierFlix.modelo.Musica;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.servicio.MusicaServicio;
import com.tfg.TopTierFlix.servicio.UsuarioServicio;


@Controller
@RequestMapping("/musicas")
public class MusicaControlador {
	
	@Autowired
	private MusicaServicio musicaServicio;
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@GetMapping
    public ModelAndView listarMusica(@RequestParam(value="page", defaultValue = "0")int page,
			@PageableDefault(size = 8, sort = "fechaEstreno", direction = Sort.Direction.DESC) Pageable pageable) {
    	Page<MusicaCardDTO> musicaPageDTO = musicaServicio.obtenerTodasMusicasPaginado(pageable);
    	return new ModelAndView("musicas/musicas").addObject("musicas",musicaPageDTO);
    }

    @GetMapping("/buscar")
    public ModelAndView buscarMusicaUsuarios(@RequestParam(value = "termino", required = false) String termino,
											@PageableDefault(sort="fechaEstreno", size=8, direction = Sort.Direction.DESC)Pageable pageable) {
    	Page<Musica> resultados;
    	if(termino !=null && !termino.trim().isEmpty()) {
    		resultados = musicaServicio.buscarMusicaPorTitulo(termino, pageable);
    	}else {
    		resultados = musicaServicio.obtenerTodasPaginado(pageable);
    	}
    	return new ModelAndView("musicas/musicas")
    			.addObject("musicas",resultados)
    			.addObject("terminoBusqueda",termino);
    }
    
    @GetMapping("/{id}")
    public ModelAndView mostrarDetallesDeMusica(@PathVariable Integer id, Principal principal) {
    	MusicaDetalleDTO musicaDetalleDTO = musicaServicio.obtenerMusicaDetallePorId(id);
    	ModelAndView modelAnView = new ModelAndView("musicas/musicas")
    			.addObject("musica",musicaDetalleDTO)
    			.addObject("comentarios", musicaServicio.obtenerComentariosPorMusicaId(id))
    			.addObject("nuevoComentario", new Comentario());
    	if (principal !=null) {
    		String userEmail = principal.getName();
    		boolean esFavorita = musicaServicio.esFavorita(id, userEmail);
    		modelAnView.addObject("esFavorita", esFavorita);
    	}else {
    		modelAnView.addObject("esFavorita", false);
    	}
    	return modelAnView;
    }
    
    @PostMapping("/{musicaId}/comentar")
    public String guardarNuevoComentario(@PathVariable Integer musicaId,
    									 @ModelAttribute("nuevoComentario")ComentarioMusica comentariomusica, 
    									 Principal principal, 
    									 Model model) {
    	String usuarioEmail = principal.getName();
    	Optional<Usuario> usuarioOptional = usuarioServicio.obtenerUsuarioPorEmail(usuarioEmail);
    	
    	Musica musica = musicaServicio.obtenerMusicaPorId(musicaId);
    	
    	if(usuarioOptional.isPresent()) {
    		Usuario usuario = usuarioOptional.get();
    		
    		comentariomusica.setMusica(musica);
    		comentariomusica.setUsuario(usuario);
    		
    		musicaServicio.guardarComentario(comentariomusica);
    		return "redirect:/musicas/"+musicaId;
    	}else {
    		model.addAttribute("error", "No se pudo guardar el comentario. El usuario no existe.");
    	    return "error/error-generico";
    	}
    }
    
    @PostMapping("/{id}/favorito")
    public String toggleFavorito(@PathVariable Integer id, Principal principal) {
    	if(principal !=null) {
    		String userEmail = principal.getName();
    		if(musicaServicio.esFavorita(id, userEmail)) {
    			musicaServicio.eliminarFavorito(id, userEmail);
    		}else {
    			musicaServicio.agregarFavorito(id, userEmail);
    		}    		
    	}
    	return "redirect:/musicas/"+ id;
    }

}
