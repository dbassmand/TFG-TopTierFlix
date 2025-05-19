package com.tfg.TopTierFlix.mapper;

import org.mapstruct.Mapper;

import com.tfg.TopTierFlix.dto.SerieCardDTO;
import com.tfg.TopTierFlix.dto.SerieDetalleDTO;
import com.tfg.TopTierFlix.dto.SerieListadoAdminDTO;
import com.tfg.TopTierFlix.modelo.Serie;

@Mapper(componentModel = "spring", uses = GeneroMapper.class)
public interface SerieMapper {

	SerieCardDTO toSerieCardDTO (Serie serie);
	
	SerieDetalleDTO toSerieDetalleDTO(Serie serie);
	
	SerieListadoAdminDTO toSerieListadoAdminDTO (Serie serie);
}
