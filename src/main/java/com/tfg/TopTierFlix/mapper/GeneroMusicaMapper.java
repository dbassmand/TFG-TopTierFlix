package com.tfg.TopTierFlix.mapper;

import org.mapstruct.Mapper;

import com.tfg.TopTierFlix.dto.GeneroMusicaDTO;
import com.tfg.TopTierFlix.modelo.GeneroMusica;


@Mapper(componentModel = "spring")
public interface GeneroMusicaMapper {
	
	GeneroMusicaDTO toGeneroMusicaDTO(GeneroMusica generoMusica);
	GeneroMusica toGeneroMusica(GeneroMusicaDTO generoMusicaDTO);

}
