package com.tfg.TopTierFlix.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaCardDTO {
	
	private Integer id;
	private String titulo;
	private String rutaPortada;
	private LocalDate fechaEstreno;
}
