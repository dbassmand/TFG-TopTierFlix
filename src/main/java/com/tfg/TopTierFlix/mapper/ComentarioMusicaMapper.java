package com.tfg.TopTierFlix.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tfg.TopTierFlix.dto.ComentarioMusicaDTO;
import com.tfg.TopTierFlix.modelo.ComentarioMusica;

@Mapper(componentModel = "spring") 
public interface ComentarioMusicaMapper {
	
	@Mapping(source = "usuario.email", target = "usuarioEmail")
	ComentarioMusicaDTO toComentarioMusicaDTO(ComentarioMusica comentarioMusica);

}
