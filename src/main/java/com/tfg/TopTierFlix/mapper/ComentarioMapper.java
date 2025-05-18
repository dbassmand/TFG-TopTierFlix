package com.tfg.TopTierFlix.mapper;

import com.tfg.TopTierFlix.dto.ComentarioDTO;
import com.tfg.TopTierFlix.modelo.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") 
public interface ComentarioMapper {

	@Mapping(source = "usuario.email", target = "usuarioEmail")
    ComentarioDTO toComentarioDTO(Comentario comentario);

    // Si se necesitara mapear una lista de Comentarios a una lista de ComentarioDTOs
    // List<ComentarioDTO> toComentarioDTOList(List<Comentario> comentarios);
}