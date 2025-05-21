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

@Entity
@Table(name = "comentario_videojuego")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "usuario", "videojuego" }) // Excluir relaciones para evitar StackOverflowError
public class ComentarioVideojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El comentario no puede estar vacío")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comentario;

    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @NotNull(message = "El usuario del comentario es obligatorio")
    private Usuario usuario; // Relación con el usuario que hizo el comentario

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_videojuego", nullable = false)
    @NotNull(message = "El videojuego del comentario es obligatorio")
    private Videojuego videojuego; // <-- ¡ESTE ES EL CAMPO 'videojuego' QUE ESPERA Videojuego.java!

    // Constructor para facilitar la creación de comentarios (sin ID y fecha que se generan automáticamente)
    public ComentarioVideojuego(String comentario, Usuario usuario, Videojuego videojuego) {
        this.comentario = comentario;
        this.usuario = usuario;
        this.videojuego = videojuego;
        this.fechaCreacion = LocalDateTime.now(); // Establecer la fecha actual por defecto
    }
}