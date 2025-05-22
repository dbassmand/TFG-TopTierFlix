package com.tfg.TopTierFlix.modelo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "genero_musica") 
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "musicas") 
public class GeneroMusica {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero_musica") // Columna de ID específica
    private Integer id;

    @NotBlank(message = "El nombre del género no puede estar vacío")
    @Column(unique = true, nullable = false) 
    private String nombre;

    
    @ManyToMany(mappedBy = "generos")
    private List<Musica> musicas; 

    // Constructor sin ID (útil para crear nuevos géneros sin especificar el ID autoincremental)
    public GeneroMusica(String nombre) {
        this.nombre = nombre;
    }

    // Considera añadir un equals y hashCode basado en el ID para colecciones y JPA
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneroMusica)) return false;
        GeneroMusica that = (GeneroMusica) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31; // Constante o Objects.hash(id) si el id no es null
    }

}
