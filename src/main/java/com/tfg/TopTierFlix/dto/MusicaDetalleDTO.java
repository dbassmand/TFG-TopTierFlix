package com.tfg.TopTierFlix.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MusicaDetalleDTO {
	
	private Integer id;
	private String titulo;
	private String rutaPortada;
	private String fechaEstreno;
	private String descripcion;
	private String youtubeTrailerId;
	private List<GeneroMusicaDTO> generos;
	private List<ComentarioDTO> comentarios;

}
