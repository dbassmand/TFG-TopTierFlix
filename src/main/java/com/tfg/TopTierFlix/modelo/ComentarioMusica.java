package com.tfg.TopTierFlix.modelo;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "comentario_musica")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "usuario", "musica" })
public class ComentarioMusica {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_comentario_musica") 
	    private Integer id;

	    @NotBlank(message = "El comentario no puede estar vacío")
	    @Column(length = 2000, nullable = false) 	
	    private String comentario;
	    
		@CreationTimestamp
	    @Column(name = "fecha_creacion", updatable = false)
	    private LocalDateTime fechaCreacion;

	    @ManyToOne(fetch = FetchType.LAZY)	   
	    @JoinColumn(name = "musica_id", nullable = false)
	    private Musica musica; 

	    @ManyToOne(fetch = FetchType.LAZY)
	    //@NotNull(message = "El usuario del comentario es obligatorio")
	    @JoinColumn(name = "usuario_email", referencedColumnName = "email", nullable = false)
	    private Usuario usuario; // Relación con el usuario que hizo el comentario
}
