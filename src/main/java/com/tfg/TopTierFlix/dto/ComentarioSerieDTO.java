package com.tfg.TopTierFlix.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ComentarioSerieDTO {
	
	private Integer id;
    private String comentario;
    private String usuarioEmail; 
    private LocalDateTime fechaCreacion;
}
