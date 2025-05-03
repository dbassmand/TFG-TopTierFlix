package com.tfg.TopTierFlix.mapper;

import org.mapstruct.Mapper;

import com.tfg.TopTierFlix.dto.PeliculaDetalleDTO;
import com.tfg.TopTierFlix.dto.PeliculaCardDTO;
import com.tfg.TopTierFlix.dto.PeliculaListadoAdminDTO;
import com.tfg.TopTierFlix.modelo.Pelicula;


/*
 * con @Mapper se le indica a la dependencia MapStruct que genere automáticamente
 * una implementación de esta interfaz. La implementacion contendrá la lógica
 * necesaria para realizar la conversión entre las clases que se especifican.
 * Magic!!!!
 */
@Mapper(componentModel = "spring", uses = GeneroMapper.class)
public interface PeliculaMapper {
	
	PeliculaCardDTO toPeliculaCardDTO (Pelicula pelicula);
	
	PeliculaDetalleDTO toPeliculaDetalleDTO(Pelicula pelicula);
	
	PeliculaListadoAdminDTO toPeliculaListadoAdminDTO (Pelicula pelicula);
	
	/*Se mapea en sentido inverso por si acaso, pero realmente no se usa

	Pelicula peliculaCardDTOtoPelicula (PeliculaCardDTO peliculaIncioDTO);
	
	Pelicula peliculaDetalleDTOtoPelicula (PeliculaDetalleDTO peliculaDetalleDTO);
	
	Pelicula peliculaListadoAdminDTOtoPelicula (PeliculaListadoAdminDTO peliculaListadoAdminDTO);
	*/
}
