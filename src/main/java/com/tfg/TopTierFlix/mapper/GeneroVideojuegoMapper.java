package com.tfg.TopTierFlix.mapper;

import org.mapstruct.Mapper;


import com.tfg.TopTierFlix.dto.GeneroVideojuegoDTO;
import com.tfg.TopTierFlix.modelo.GeneroVideojuego;

@Mapper(componentModel = "spring")
public interface GeneroVideojuegoMapper {
	
	GeneroVideojuegoDTO toGeneroVideojuegoDTO(GeneroVideojuego generoVideojuego);
	GeneroVideojuego toGeneroVideojuego(GeneroVideojuegoDTO generoVideojuegoDTO);

}
