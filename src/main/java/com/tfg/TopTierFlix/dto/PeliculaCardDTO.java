package com.tfg.TopTierFlix.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeliculaCardDTO {
	
	private Integer id;
	private String titulo;
	private String rutaPortada;
	private LocalDate fechaEstreno;
}
