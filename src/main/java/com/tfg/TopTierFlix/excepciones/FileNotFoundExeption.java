package com.tfg.TopTierFlix.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//Clase de excepcion personalizada. Crea un json con el detaller del error y lanza codigo HTTP 404
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FileNotFoundExeption extends RuntimeException{
		
	private static final long serialVersionUID = 1L;

	public FileNotFoundExeption(String mensaje) {
		super(mensaje);
	}
	
	public FileNotFoundExeption (String mensaje, Throwable excepcion) {
		super(mensaje, excepcion);
	}
}
