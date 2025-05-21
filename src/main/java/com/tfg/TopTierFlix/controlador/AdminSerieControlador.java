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

import com.tfg.TopTierFlix.modelo.Genero;
import com.tfg.TopTierFlix.modelo.Serie; // Importar Serie
import com.tfg.TopTierFlix.servicio.AlmacenServicioImpl;
import com.tfg.TopTierFlix.servicio.GeneroServicioImpl;
import com.tfg.TopTierFlix.servicio.SerieServicio; // Importar SerieServicio

@Controller
@RequestMapping("/admin/series")
public class AdminSerieControlador {

    @Autowired
    private SerieServicio serieServicio;

    @Autowired
    private GeneroServicioImpl generoServicio;

    @Autowired
    private AlmacenServicioImpl almacenServicio;

    @ModelAttribute("serie")
    public Serie getSerie(@RequestParam(required = false) Integer id) {
        if (id != null) {
            return serieServicio.obtenerSeriePorId(id);
        }
        return new Serie();
    }

    @GetMapping("") // Mapea a /admin/series
    public ModelAndView listarSeriesAdmin(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        Page<Serie> series = serieServicio.obtenerTodasPaginado(pageable);
        return new ModelAndView("admin/series/index").addObject("series", series);
    }

    @GetMapping("/buscar") // Mapea a /admin/series/buscar
    public ModelAndView buscarSeriesAdmin(@RequestParam(value = "termino", required = false) String termino,
            @PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        Page<Serie> resultados;
        if (termino != null && !termino.trim().isEmpty()) {
            resultados = serieServicio.buscarSeriePorTitulo(termino, pageable);
        } else {
            resultados = serieServicio.obtenerTodasPaginado(pageable);
        }
        return new ModelAndView("admin/series/index").addObject("series", resultados).addObject("terminoBusqueda",
                termino);
    }

    @GetMapping("/nuevo") // Mapea a /admin/series/nuevo
    public ModelAndView mostrarFormularioDeNuevaSerie() {
        List<Genero> generos = generoServicio.obtenerTodosGeneros(Sort.by("titulo"));
        return new ModelAndView("admin/series/nueva-serie")
        		.addObject("serie", new Serie())
        		.addObject("generos",generos);
    }

    @PostMapping("/nuevo") // Mapea a /admin/series/nuevo (POST)
    public ModelAndView registrarSerie(@Validated @ModelAttribute("serie") Serie serie, // Renombrado
            BindingResult bindingResult) {
        if (bindingResult.hasErrors() || serie.getPortada().isEmpty()) {
            System.out.println(bindingResult);
            if (serie.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada", "MultipartNotEmpty");
            }

            List<Genero> generos = generoServicio.obtenerTodosGeneros(Sort.by("titulo"));
            return new ModelAndView("admin/series/nueva-serie").addObject("serie", serie)
                    .addObject("generos", generos);
        }

        String rutaPortada = almacenServicio.almacenarArchivo(serie.getPortada());
        serie.setRutaPortada(rutaPortada);
        serieServicio.guardarSerie(serie);
        return new ModelAndView("redirect:/admin/series"); // Redirección corregida
    }

    @GetMapping("/{id}/editar") // Mapea a /admin/series/{id}/editar
    public ModelAndView mostrarFormularioDeEditarSerie(@PathVariable Integer id) {

        Serie serie = serieServicio.obtenerSeriePorId(id);
        List<Genero> generos = generoServicio.obtenerTodosGeneros(Sort.by("titulo"));

        return new ModelAndView("admin/series/editar-serie").addObject("serie", serie).addObject("generos", generos);
    }

    @PostMapping("/{id}/editar") // Mapea a /admin/series/{id}/editar (POST)
    public ModelAndView actualizarSerie(
        @PathVariable Integer id,
        @Validated @ModelAttribute("serie") Serie serie,
        BindingResult bindingResult,
        @RequestParam(value = "portada", required = false) MultipartFile portada) {

        System.out.println("Datos recibidos - Título: " + serie.getTitulo());

        if (bindingResult.hasErrors()) {
            System.out.println("Errores encontrados:");
            bindingResult.getAllErrors().forEach(System.out::println);

            List<Genero> generos = generoServicio.obtenerTodosGeneros(Sort.by("titulo"));
            return new ModelAndView("admin/series/editar-serie")
                    .addObject("serie", serie)
                    .addObject("generos", generos);
        }

        Serie serieDB = serieServicio.obtenerSeriePorId(id);
        serieDB.setTitulo(serie.getTitulo());
        serieDB.setSinopsis(serie.getSinopsis());
        serieDB.setFechaEstreno(serie.getFechaEstreno());
        serieDB.setYoutubeTrailerId(serie.getYoutubeTrailerId());
        serieDB.setGeneros(serie.getGeneros());

        if(!serie.getPortada().isEmpty()) {
            almacenServicio.eliminarArchivo(serieDB.getRutaPortada());
            String rutaPortada = almacenServicio.almacenarArchivo(serie.getPortada());
            serieDB.setRutaPortada(rutaPortada);
        }

        serieServicio.guardarSerie(serieDB);
        return new ModelAndView("redirect:/admin/series"); // Redirección corregida
    }

    @PostMapping("/{id}/eliminar") // Mapea a /admin/series/{id}/eliminar
    public String eliminarSerie(@PathVariable Integer id) {
        Serie serie = serieServicio.obtenerSeriePorId(id);
        serieServicio.eliminarSerie(serie);
        almacenServicio.eliminarArchivo(serie.getRutaPortada());
        return "redirect:/admin/series"; // Redirección corregida
    }

    // Mapea a /admin/series/{serieId}/comentarios/{comentarioId}/eliminar
    @PostMapping("/{serieId}/comentarios/{comentarioId}/eliminar")
    public String eliminarComentario(@PathVariable Integer serieId,
                                     @PathVariable Integer comentarioId,
                                     RedirectAttributes redirectAttributes) {
        serieServicio.eliminarComentarioPorId(comentarioId);
        redirectAttributes.addFlashAttribute("mensaje", "Comentario eliminado correctamente.");
        return "redirect:/series/" + serieId; // Redirige a la vista pública de la serie
    }
}