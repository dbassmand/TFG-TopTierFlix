package com.tfg.TopTierFlix.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideojuegoListadoAdminDTO {
	private Integer id;
	private String nombre;	
	private String fechaEstreno;
	private String sinopsis;	
	private List<GeneroVideojuegoDTO> generos;

}
