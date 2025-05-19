package com.tfg.TopTierFlix.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SerieListadoAdminDTO {
	private Integer id;
	private String titulo;	
	private String fechaEstreno;
	private String sinopsis;	
	private List<GeneroDTO> generos;

}
