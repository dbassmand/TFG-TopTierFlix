package com.tfg.TopTierFlix.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; // Cambiamos a @Controller
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; // Añadimos @ResponseBody

import com.tfg.TopTierFlix.servicio.AlmacenServicioImpl;

import java.io.IOException;

@Controller // Usamos @Controller para tener más control sobre la respuesta
@RequestMapping("/assets")
public class AssetsControlador {

    @Autowired
    private AlmacenServicioImpl servicio;

    @GetMapping("/{filename:.+}")
    @ResponseBody // Indicamos que el valor de retorno se escribe directamente en el cuerpo de la respuesta
    public ResponseEntity<Resource> obtenerRecurso(@PathVariable("filename") String filename) throws IOException {
        Resource resource = servicio.cargarComoRecurso(filename);

        if (resource.exists() && resource.isReadable()) {
            String contentType = MediaType.IMAGE_JPEG_VALUE; // Valor por defecto
            if (filename.toLowerCase().endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG_VALUE;
            } else if (filename.toLowerCase().endsWith(".gif")) {
                contentType = MediaType.IMAGE_GIF_VALUE;
            }
            // Añade más tipos de imagen según necesites

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.TopTierFlix.servicio.AlmacenServicioImpl;

@RestController
@RequestMapping("/assets")
public class AssetsControlador {
	
	@Autowired
	private AlmacenServicioImpl servicio;
	
	@GetMapping("/{filename:.+}")
	public Resource obtenerRecurso(@PathVariable("filename") String filename) {
		return servicio.cargarComoRecurso(filename);
	}	
}
*/