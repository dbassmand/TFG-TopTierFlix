package com.tfg.TopTierFlix.controlador;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.tfg.TopTierFlix.dto.PeliculaDetalleDTO;
import com.tfg.TopTierFlix.dto.PeliculaCardDTO;
import com.tfg.TopTierFlix.modelo.Pelicula;
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
    public ModelAndView mostrarDetallesDePelicula(@PathVariable Integer id, Principal principal) { //Principal en Spring Security es la identidad del usuario actualmente autenticado
        PeliculaDetalleDTO peliculaDetalleDTO = peliculaServicio.obtenerPeliculaDetallePorId(id);
        ModelAndView modelAndView = new ModelAndView("pelicula").addObject("pelicula", peliculaDetalleDTO);

        if (principal != null) {
            String userEmail = principal.getName();
            boolean esFavorita = peliculaServicio.esFavorita(id, userEmail);
            modelAndView.addObject("esFavorita", esFavorita);
        } else {
            modelAndView.addObject("esFavorita", false);
        }

        return modelAndView;
    }
	
	@PostMapping("/peliculas/{id}/favorito")
    public String toggleFavorito(@PathVariable Integer id, Principal principal) {
        if (principal != null) {
            String userEmail = principal.getName();
            if (peliculaServicio.esFavorita(id, userEmail)) {
                // Llama al método para eliminar de favoritos (aún no lo tienes en PeliculaServicio)
                // Necesitas añadir un método eliminarFavorito en PeliculaServicio y PeliculaServicioImpl
                peliculaServicio.eliminarFavorito(id, userEmail);
            } else {
                // Llama al método para agregar a favoritos (ya lo tienes en PeliculaServicio)
                peliculaServicio.agregarFavorito(id, userEmail);
            }
        }
        return "redirect:/peliculas/" + id;
    }
	
	@GetMapping("/favoritas")
    public ModelAndView mostrarFavoritas(Principal principal) {
        if (principal != null) {
            String userEmail = principal.getName();
            List<Pelicula> favoritas = peliculaServicio.obtenerPeliculasFavoritasDelUsuario(userEmail);

            // Opcional: Formatear la información para la vista si es necesario
            List<PeliculaCardDTO> favoritasDTO = favoritas.stream()
                    .map(pelicula -> new PeliculaCardDTO(
                    		
                            pelicula.getId(),
                            pelicula.getTitulo(),
                            pelicula.getRutaPortada(), // Asegúrate de tener la ruta de la portada en Pelicula
                            pelicula.getFechaEstreno()
                    ))
                    .collect(Collectors.toList());

            return new ModelAndView("favoritas").addObject("favoritas", favoritasDTO);
        } else {
            // Si el usuario no está logueado, puedes redirigirlo al login o mostrar un mensaje
            return new ModelAndView("redirect:/login"); // Ejemplo de redirección al login
        }
    }
}
