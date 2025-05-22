// src/main/java/com/tfg/TopTierFlix/modelo/ComentarioVideojuego.java
package com.tfg.TopTierFlix.modelo;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "comentario_videojuego")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "usuario", "videojuego" }) // Excluir relaciones para evitar StackOverflowError
public class ComentarioVideojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario_videojuego") 
    private Integer id;

    @NotBlank(message = "El comentario no puede estar vacío")
    @Column(length = 2000, nullable = false) 	
    private String comentario;

    //@NotNull(message = "La fecha de creación es obligatoria")
	@CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    //@NotNull(message = "El videojuego del comentario es obligatorio")
    @JoinColumn(name = "videojuego_id", nullable = false)
    private Videojuego videojuego; 

    @ManyToOne(fetch = FetchType.LAZY)
    //@NotNull(message = "El usuario del comentario es obligatorio")
    @JoinColumn(name = "usuario_email", referencedColumnName = "email", nullable = false)
    private Usuario usuario; // Relación con el usuario que hizo el comentario
            
}