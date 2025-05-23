
package com.tfg.TopTierFlix.controlador;

import com.tfg.TopTierFlix.dto.PeliculaCardDTO;
import com.tfg.TopTierFlix.dto.SerieCardDTO;
import com.tfg.TopTierFlix.dto.MusicaCardDTO;
import com.tfg.TopTierFlix.dto.VideojuegoCardDTO;
import com.tfg.TopTierFlix.modelo.Musica;
import com.tfg.TopTierFlix.modelo.Pelicula;
import com.tfg.TopTierFlix.modelo.Serie;
import com.tfg.TopTierFlix.modelo.Videojuego;
import com.tfg.TopTierFlix.servicio.MusicaServicio;
import com.tfg.TopTierFlix.servicio.PeliculaServicio;
import com.tfg.TopTierFlix.servicio.SerieServicio;
import com.tfg.TopTierFlix.servicio.VideojuegoServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("") 
public class FavoritosControlador {

    @Autowired
    private PeliculaServicio peliculaServicio;

    @Autowired
    private SerieServicio serieServicio;
    
    @Autowired
    private VideojuegoServicio videojuegoServicio;
    
    @Autowired
    private MusicaServicio musicaServicio;

    @GetMapping("/favoritas")
    public ModelAndView mostrarMisFavoritos(Principal principal) {
        if (principal == null) {
            return new ModelAndView("redirect:/login");
        }

        String userEmail = principal.getName();

        // 1. Obtener películas favoritas
        List<Pelicula> peliculasFavoritas = peliculaServicio.obtenerPeliculasFavoritasDelUsuario(userEmail);
        List<PeliculaCardDTO> peliculasFavoritasDTO = peliculasFavoritas.stream()
                .map(pelicula -> new PeliculaCardDTO(
                        pelicula.getId(),
                        pelicula.getTitulo(),
                        pelicula.getRutaPortada(),
                        pelicula.getFechaEstreno()
                ))
                .collect(Collectors.toList());

        // 2. Obtener series favoritas
        List<Serie> seriesFavoritas = serieServicio.obtenerSeriesFavoritasDelUsuario(userEmail);
        List<SerieCardDTO> seriesFavoritasDTO = seriesFavoritas.stream()
                .map(serie -> new SerieCardDTO( // Asegúrate de que SerieCardDTO tiene un constructor similar
                        serie.getId(),
                        serie.getTitulo(),
                        serie.getRutaPortada(),
                        serie.getFechaEstreno()
                ))
                .collect(Collectors.toList());

        
        
        List<Videojuego> videojuegosFavoritas = videojuegoServicio.obtenerVideojuegosFavoritasDelUsuario(userEmail);
        List<VideojuegoCardDTO> videojuegosFavoritasDTO = videojuegosFavoritas.stream()
                .map(videojuego -> new VideojuegoCardDTO( // Asegúrate de que SerieCardDTO tiene un constructor similar
                		videojuego.getId(),
                		videojuego.getTitulo(),
                		videojuego.getRutaPortada(),
                		videojuego.getFechaEstreno()
                ))
                .collect(Collectors.toList());
        
        List<Musica> musicasFavoritas = musicaServicio.obtenerMusicasFavoritasDelUsuario(userEmail);
        List<MusicaCardDTO> musicasFavoritasDTO = musicasFavoritas.stream()
                .map(musica -> new MusicaCardDTO( // Asegúrate de que SerieCardDTO tiene un constructor similar
                		musica.getId(),
                		musica.getTitulo(),
                		musica.getRutaPortada(),
                		musica.getFechaEstreno()
                ))
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("favoritas"); // Mantén el nombre de la vista
        modelAndView.addObject("peliculasFavoritas", peliculasFavoritasDTO);
        modelAndView.addObject("seriesFavoritas", seriesFavoritasDTO);
        modelAndView.addObject("videojuegosFavoritas", videojuegosFavoritasDTO);
        modelAndView.addObject("musicasFavoritas", musicasFavoritasDTO);
        

        return modelAndView;
    }
    
    

    
}