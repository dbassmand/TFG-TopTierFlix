package com.tfg.TopTierFlix.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.tfg.TopTierFlix.dto.ComentarioSerieDTO;
import com.tfg.TopTierFlix.modelo.ComentarioSerie;

@Mapper(componentModel = "spring") 
public interface ComentarioSerieMapper {
	
	@Mapping(source = "usuario.email", target = "usuarioEmail")
    ComentarioSerieDTO toComentarioSerieDTO(ComentarioSerie comentarioSerie);

}
