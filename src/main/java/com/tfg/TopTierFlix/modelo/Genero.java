package com.tfg.TopTierFlix.modelo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Genero {
	
	@Id
	@Column(name="id_genero")
	//Anotaciones lombok para generar Getters y Setters
	@Getter
	@Setter
	private Integer id;
	
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
