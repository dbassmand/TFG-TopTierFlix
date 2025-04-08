package com.tfg.TopTierFlix.servicio;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;

//interfaz de servicio para declarar los metodos que desarrollaran las clases que la implemente.
public interface AlmacenServicio {
	
	public void inciarAlmacenDeArchivos();
	
	public String almacenarArchivo(MultipartFile archivo);
	
	public Path cargarArchivo(String nombreArchivo);
	
	public Resource cargarComoRecurso (String nombreArchivo);
	
	public void eliminarArchivo(String nombreArchivo);

}
