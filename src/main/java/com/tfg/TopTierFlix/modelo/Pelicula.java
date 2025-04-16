package com.tfg.TopTierFlix.modelo;


import java.time.LocalDate;
import java.util.List;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Pelicula {
	
	@Getter
	@Setter
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) //se indica que este atricuto sea autoincremental
	@Column(name= "id_pelicula")//se indca la columna que hará referencia en la BBDD
	private Integer id;
	
	@Getter
	@Setter
	@NotBlank //Que el valor no sea null, ni vacío, ni solo espacios- Solo Strings
	private String titulo;
	
	@NotBlank
	@Getter
	@Setter
	@Column(length = 1000, nullable = false) //se añaden prpiedades para sincronizar con cambio manuales hechos desde MySQL Workbench
	private String sinopsis;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@Getter
	@Setter
	private LocalDate fechaEstreno;
	
	@NotBlank
	@Getter
	@Setter
	private String youtubeTrailerId;
	
	@Getter
	@Setter
	private String rutaPortada;
	
	 @NotEmpty
	 @ManyToMany (fetch = FetchType.LAZY)//Se establece una relación de mucho a muchos entre Peliculas y Generos. LAZY solo se cargará cuando se necesite.	 
	 @JoinTable(name = "genero_pelicula", //Se vinculan las claves primarias de la tabla intermedia "genero_pelicula" de la relación muchos a muchos
	 			joinColumns = @JoinColumn(name = "id_pelicula"),
	 			inverseJoinColumns = @JoinColumn(name="id_genero"))
	 @Getter
	 @Setter
	 private List<Genero> generos;
	 
	@Transient //esta notación hace que la variale sea temporal y no se almacene en la BBDD. Las portadas se almacenan en la carpeta assets
	@Getter
	@Setter
	private MultipartFile portada;

	public Pelicula() { //constructor vacio por defecto
		super();
	}

	//constructor completo
	public Pelicula(Integer id, @NotBlank String titulo, @NotBlank String sinopsis, @NotNull LocalDate fechaEstreno, //constructor completo
			@NotBlank String youtubeTrailerId, String rutaPortada, @NotEmpty List<Genero> generos,
			MultipartFile portada) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.fechaEstreno = fechaEstreno;
		this.youtubeTrailerId = youtubeTrailerId;
		this.rutaPortada = rutaPortada;
		this.generos = generos;
		this.portada = portada;
	}

	//constructor completo menos id
	public Pelicula(@NotBlank String titulo, @NotBlank String sinopsis, @NotNull LocalDate fechaEstreno, //constructor completo menos Id
			@NotBlank String youtubeTrailerId, String rutaPortada, @NotEmpty List<Genero> generos,
			MultipartFile portada) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.fechaEstreno = fechaEstreno;
		this.youtubeTrailerId = youtubeTrailerId;
		this.rutaPortada = rutaPortada;
		this.generos = generos;
		this.portada = portada;
	}			
}
