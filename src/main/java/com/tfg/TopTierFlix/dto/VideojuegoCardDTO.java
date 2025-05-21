package com.tfg.TopTierFlix.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoCardDTO {

	private Integer id;
	private String nombre;
	private String rutaPortada;
	private LocalDate fechaEstreno;
}
