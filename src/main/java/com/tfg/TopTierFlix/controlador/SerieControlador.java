package com.tfg.TopTierFlix.controlador;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.tfg.TopTierFlix.dto.SerieCardDTO;
import com.tfg.TopTierFlix.dto.SerieDetalleDTO;
import com.tfg.TopTierFlix.modelo.Comentario;
import com.tfg.TopTierFlix.modelo.Serie;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.servicio.SerieServicio;
import com.tfg.TopTierFlix.servicio.UsuarioServicio;

@Controller
@RequestMapping("/series")
public class SerieControlador {
	

    @Autowired
    private SerieServicio serieServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping
    public ModelAndView verPaginaInicio() {
    	
    	List<SerieCardDTO> uiltimasSeriesInicioDTO = serieServicio.obtenerSeriesIncio();
    	return new ModelAndView("series/series").addObject("ultimasSeries", uiltimasSeriesInicioDTO);
    }
    
    @GetMapping("/series")
    public ModelAndView listarSeries(@RequestParam(value="page", defaultValue = "0")int page,
			@PageableDefault(size = 8, sort = "fechaEstreno", direction = Sort.Direction.DESC) Pageable pageable) {
    	Page<SerieCardDTO> seriePageDTO = serieServicio.obtenerTodasSeriesPaginado(pageable);
    	return new ModelAndView("series/series").addObject("series",seriePageDTO);
    }

    @GetMapping("/buscar")
    public ModelAndView buscarSeriesUsuarios(@RequestParam(value = "termino", required = false) String termino,
											@PageableDefault(sort="fechaEstreno", size=8, direction = Sort.Direction.DESC)Pageable pageable) {
    	Page<Serie> resultados;
    	if(termino !=null && !termino.trim().isEmpty()) {
    		resultados = serieServicio.buscarSeriePorTitulo(termino, pageable);
    	}else {
    		resultados = serieServicio.obtenerTodasPaginado(pageable);
    	}
    	return new ModelAndView("series/series")
    			.addObject("series",resultados)
    			.addObject("terminoBusqueda",termino);
    }
    
    @GetMapping("/{id}")
    public ModelAndView mostrarDetallesDeSerie(@PathVariable Integer id, Principal principal) {
    	SerieDetalleDTO serieDetalleDTO = serieServicio.obtenerSerieDetallePorId(id);
    	ModelAndView modelAnView = new ModelAndView("series/serie")
    			.addObject("serie",serieDetalleDTO)
    			.addObject("comentarios", serieServicio.obtenerComentariosPorSerieId(id))
    			.addObject("nuevoComentario", new Comentario());
    	if (principal !=null) {
    		String userEmail = principal.getName();
    		boolean esFavorita = serieServicio.esFavorita(id, userEmail);
    		modelAnView.addObject("esFavorita", esFavorita);
    	}else {
    		modelAnView.addObject("esFavorita", false);
    	}
    	return modelAnView;
    }
    
    @PostMapping("/{serieId}/comentar")
    public String guardarNuevoComentario(@PathVariable Integer serieId,
    									 @ModelAttribute("nuevoComentario")Comentario comentario, 
    									 Principal principal, 
    									 Model model) {
    	String usuarioEmail = principal.getName();
    	Optional<Usuario> usuarioOptional = usuarioServicio.obtenerUsuarioPorEmail(usuarioEmail);
    	
    	Serie serie = serieServicio.obtenerSeriePorId(serieId);
    	
    	if(usuarioOptional.isPresent()) {
    		Usuario usuario = usuarioOptional.get();
    		
    		comentario.setSerie(serie);
    		comentario.setUsuario(usuario);
    		
    		serieServicio.guardarComentario(comentario);
    		return "redirect:/series/"+serieId;
    	}else {
    		model.addAttribute("error", "No se pudo guardar el comentario. El usuario no existe.");
    	    return "error/error-generico";
    	}
    }
    
    @PostMapping("/{id}/favorito")
    public String toggleFavorito(@PathVariable Integer id, Principal principal) {
    	if(principal !=null) {
    		String userEmail = principal.getName();
    		if(serieServicio.esFavorita(id, userEmail)) {
    		serieServicio.eliminarFavorito(id, userEmail);
    		}else {
    			serieServicio.agregarFavorito(id, userEmail);
    		}    		
    	}
    	return "redirect:/series/"+ id;
    }
    
    @GetMapping("/favoritas")
    public ModelAndView mostrarFavoritas(Principal principal) {
    	if(principal !=null) {
    		String userEmail = principal.getName();
    		List<Serie> favoritas = serieServicio.obtenerSeriesFavoritasDelUsuario(userEmail);
    		
    		List<SerieCardDTO> favoritasDTO = favoritas.stream()
    				.map(serie -> new SerieCardDTO(
    						
    					serie.getId(),
    					serie.getTitulo(),
    					serie.getRutaPortada(),
    					serie.getFechaEstreno()
    				))
    				.collect(Collectors.toList());
    		return new ModelAndView("series/favoritas").addObject("favoritas", favoritasDTO);
    	}else {
    		return new ModelAndView("redirect:/login");
    	}
    }
}
