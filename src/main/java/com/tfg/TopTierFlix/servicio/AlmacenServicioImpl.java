package com.tfg.TopTierFlix.servicio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import com.tfg.TopTierFlix.excepciones.*;
import jakarta.annotation.PostConstruct;


@Service
public class AlmacenServicioImpl implements AlmacenServicio{

	@Value("${storage.location}")//rescata el valor de assets del application.properties
	private String storageLocation;

	@PostConstruct //Este metodo se ejecuta cada vez que haya una nueva instancia de la clase
	@Override
	public void inciarAlmacenDeArchivos() {
		try {
			Files.createDirectories(Paths.get(storageLocation)); //se crea un directorio tomando la ruta de los assets
		} catch (IOException exception) {
			throw new AlmacenException("Error: No se a inicializado la ubicacion en el almacen de archivos");
		}
		
	}

	@Override
	public String almacenarArchivo(MultipartFile archivo) {
		String nombreArchivo = archivo.getOriginalFilename();
		if(archivo.isEmpty()) {
			throw new AlmacenException("No se puede almacenar un archivo vacio");
		}
		try {
			InputStream inputStream = archivo.getInputStream();
			Files.copy(inputStream,Paths.get(storageLocation).resolve(nombreArchivo),StandardCopyOption.REPLACE_EXISTING);//Si hay un archivo con el mismo nombre se sobreescribe
		} catch (IOException exception) {
			throw new AlmacenException("Error al guardar el archivo" + nombreArchivo, exception);
		}
		return nombreArchivo;
	}

	@Override
	public Path cargarArchivo(String nombreArchivo) {
		
		return Paths.get(storageLocation).resolve(nombreArchivo);
	}

	@Override	
	public Resource cargarComoRecurso(String nombreArchivo) {
	    try {
	        
	        Path archivo = cargarArchivo(nombreArchivo);// Obtiene la ruta completa del archivo usando el método cargarArchivo()	        
	        Resource recurso = new UrlResource(archivo.toUri());// Crea un objeto Resource a partir de la URI del archivo
	        
	        if (recurso.exists() || recurso.isReadable()) {// Verifica si el recurso existe y se puede leer
	            return recurso; // Devuelve el recurso si es válido
	        } else {	           
	            throw new FileNotFoundExeption("No se pudo encontrar el archivo: " + nombreArchivo); // Si no existe o no se puede leer, lanza una excepción personalizada
	        }
	    } catch (MalformedURLException exception) {	       
	        throw new FileNotFoundExeption("No se pudo encontrar el archivo: " + nombreArchivo + " - " + exception); // Si la URI del archivo no es válida, lanza una excepción personalizada
	    }
	}

	@Override
	public void eliminarArchivo(String nombreArchivo) {
		Path archivo = cargarArchivo(nombreArchivo);// misma implementación del metodo anterior
		try {
			FileSystemUtils.deleteRecursively(archivo);
		} catch (Exception exception) {
			System.out.println(exception); 
		}		
	}	
}
