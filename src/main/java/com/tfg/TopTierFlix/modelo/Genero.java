package com.tfg.TopTierFlix.modelo;
//import com.tfg.TopTierFlix.dto.GeneroDTO;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"peliculas", "series"})
public class Genero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTOINCREMENT
	@Column(name="id_genero")			
	private Integer id;
	
	//Es necesario crear relacion bidireccional al tener relaciones de dos tablas distintas sobre Genero
	@ManyToMany(mappedBy = "generos")
    private List<Pelicula> peliculas;

    @ManyToMany(mappedBy = "generos") 
        private List<Serie> series;
	
    private String titulo; //no son necesarias anotaciones Spring ya que estos datos se insertan en la BBDD por DML.
			
	public Genero (String titulo) {
		this.titulo=titulo;
	}
	
	public Genero(Integer id) {
		this.id=id;
	}
}
