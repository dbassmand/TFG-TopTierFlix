package com.tfg.TopTierFlix.modelo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Genero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTOINCREMENT
	@Column(name="id_genero")		
	@Getter
	@Setter
	private Integer id;
	
	@Getter
	@Setter
	private String titulo;
	
	public Genero(Integer id, String titulo) {
		super();
		this.id=id;
		this.titulo=titulo;
	}

	public Genero() {
		super();
	}
	
	public Genero (String titulo) {
		this.titulo=titulo;
	}
	
	public Genero(Integer id) {
		this.id=id;
	}
}
