package com.tfg.TopTierFlix.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeliculaDetalleDTO {
	private Integer id;
	private String titulo;
	private String rutaPortada;
	private String fechaEstreno;
	private String sinopsis;
	private String youtubeTrailerId;
	private List<GeneroDTO> generos;
	private List<ComentarioDTO> comentarios;
}
