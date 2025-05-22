package com.tfg.TopTierFlix.mapper;

import org.mapstruct.Mapper;

import com.tfg.TopTierFlix.dto.MusicaCardDTO;
import com.tfg.TopTierFlix.dto.MusicaDetalleDTO;
import com.tfg.TopTierFlix.dto.MusicaListadoAdminDTO;
import com.tfg.TopTierFlix.modelo.Musica;

@Mapper(componentModel = "spring", uses = MusicaMapper.class)
public interface MusicaMapper {
	
	MusicaCardDTO toMusicaCardDTO(Musica musica);

	MusicaDetalleDTO toMusicaDetalleDTO(Musica musica);

	MusicaListadoAdminDTO toMusicaListadoAdminDTO(Musica musica);

}
