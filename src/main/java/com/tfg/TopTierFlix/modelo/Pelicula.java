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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "peliculasFavoritas") //evita error StackOverflow
public class Pelicula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // se indica que este atricuto sea autoincremental
	@Column(name = "id_pelicula") // se indca la columna que hará referencia en la BBDD
	private Integer id;

	@NotBlank // Que el valor no sea null, ni vacío, ni solo espacios- Solo Strings
	private String titulo;

	@NotBlank
	@Column(length = 1000, nullable = false) // MySQL Workbench, hibernate asigna por defecto varchar (255)										
	private String sinopsis;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaEstreno;

	@NotBlank
	private String youtubeTrailerId;

	private String rutaPortada;

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY) // Se establece una relación de mucho a muchos entre Peliculas y Generos. LAZY
										// solo se cargará cuando se necesite.
	@JoinTable(name = "genero_pelicula", // Se vinculan las claves primarias de la tabla intermedia "genero_pelicula" de
											// la relación muchos a muchos
			joinColumns = @JoinColumn(name = "id_pelicula"), inverseJoinColumns = @JoinColumn(name = "id_genero"))
	private List<Genero> generos;

	@ManyToMany(mappedBy = "peliculasFavoritas", fetch = FetchType.LAZY)
	private List<Usuario> usuariosFavoritos;

	@Transient // esta notación hace que la variale sea temporal y no se almacene en la BBDD.
				// Las portadas se almacenan en la carpeta assets
	private MultipartFile portada;

	// Constructor para Mapper peliculaCardDTOtoPelicula
	public Pelicula(Integer id, @NotBlank String titulo, @NotNull LocalDate fechaEstreno, String rutaPortada,
			MultipartFile portada) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.rutaPortada = rutaPortada;
		this.fechaEstreno = fechaEstreno;
	}

	// constructor completo menos id
	public Pelicula(@NotBlank String titulo, @NotBlank String sinopsis, @NotNull LocalDate fechaEstreno, // constructor
																											// completo
																											// menos Id
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pelicula)) return false;
        Pelicula pelicula = (Pelicula) o;
        return id != null && id.equals(pelicula.id);
    }

    @Override
    public int hashCode() {
        return 31; // Un número constante si solo usas el ID, o puedes usar Objects.hash(id) si id no es null
    }
}
