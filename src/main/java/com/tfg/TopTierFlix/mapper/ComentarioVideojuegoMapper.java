package com.tfg.TopTierFlix.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tfg.TopTierFlix.dto.ComentarioVideojuegoDTO;
import com.tfg.TopTierFlix.modelo.ComentarioVideojuego;

@Mapper(componentModel = "spring") 
public interface ComentarioVideojuegoMapper {

	@Mapping(source = "usuario.email", target = "usuarioEmail")
	ComentarioVideojuegoDTO toComentarioVideojuegoDTO(ComentarioVideojuego comentarioVideojuego);
}
