package com.tfg.TopTierFlix.mapper;

import org.mapstruct.Mapper;


import com.tfg.TopTierFlix.dto.VideojuegoCardDTO;
import com.tfg.TopTierFlix.dto.VideojuegoDetalleDTO;
import com.tfg.TopTierFlix.dto.VideojuegoListadoAdminDTO;
import com.tfg.TopTierFlix.modelo.Videojuego;

@Mapper(componentModel = "spring", uses = VideojuegoMapper.class)
public interface VideojuegoMapper {

	VideojuegoCardDTO toVideojuegoCardDTO(Videojuego videojuego);

	VideojuegoDetalleDTO toVideojuegoDetalleDTO(Videojuego videojuego);

	VideojuegoListadoAdminDTO toVideojuegoListadoAdminDTO(Videojuego videojuego);
}
