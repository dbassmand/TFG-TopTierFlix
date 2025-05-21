// src/main/java/com/tfg/TopTierFlix/modelo/GeneroVideojuego.java
package com.tfg.TopTierFlix.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany; // Si quieres la relación bidireccional desde el género
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString; // Importar ToString

import java.util.List; // Importar List

@Entity
@Table(name = "genero_videojuego") // Nombre de tabla específico
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "videojuegos") // Excluir la relación para evitar StackOverflowError
public class GeneroVideojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero_videojuego") // Columna de ID específica
    private Integer id;

    @NotBlank(message = "El nombre del género no puede estar vacío")
    @Column(unique = true, nullable = false) // Nombre de género único y no nulo
    private String nombre;

    // Relación ManyToMany bidireccional con Videojuego (opcional para el GeneroVideojuego)
    // Mapeado por el campo 'generos' en la entidad Videojuego
    @ManyToMany(mappedBy = "generos")
    private List<Videojuego> videojuegos; // La colección de videojuegos que pertenecen a este género

    // Constructor sin ID (útil para crear nuevos géneros sin especificar el ID autoincremental)
    public GeneroVideojuego(String nombre) {
        this.nombre = nombre;
    }

    // Considera añadir un equals y hashCode basado en el ID para colecciones y JPA
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneroVideojuego)) return false;
        GeneroVideojuego that = (GeneroVideojuego) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31; // Constante o Objects.hash(id) si el id no es null
    }
}