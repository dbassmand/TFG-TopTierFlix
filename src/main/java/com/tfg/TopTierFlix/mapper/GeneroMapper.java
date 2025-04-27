package com.tfg.TopTierFlix.mapper;


import org.mapstruct.Mapper;

import com.tfg.TopTierFlix.dto.GeneroDTO;
import com.tfg.TopTierFlix.modelo.Genero;

@Mapper(componentModel = "spring")
public interface GeneroMapper {

	GeneroDTO toGeneroDTO(Genero genero);
	Genero toGenero(GeneroDTO generoDTO);
}
