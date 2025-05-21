// src/main/java/com/tfg/TopTierFlix/modelo/Videojuego.java
package com.tfg.TopTierFlix.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile; // Necesario si usas el @Transient para la carga

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "usuariosFavoritos", "comentarios", "generos" }) 
public class Videojuego {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_videojuego")
	private Integer id;

	@NotBlank(message = "El título no puede estar vacío") 
    @Size(max = 255, message = "El título no puede exceder los 255 caracteres") 
	private String titulo;

	@NotBlank(message = "La descripción no puede estar vacía")
	@Column(length = 2000, nullable = false)
	private String descripcion;

	@NotNull(message = "La fecha de estreno es obligatoria") 
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaEstreno;

	@NotBlank(message = "El ID del trailer de YouTube es obligatorio") 
	private String youtubeTrailerId;

	@Column(length = 500) 
	private String rutaPortada;

	@NotEmpty(message = "Debe seleccionar al menos un género") 
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "videojuego_genero_videojuego", 
			joinColumns = @JoinColumn(name = "videojuego_id"), 
			inverseJoinColumns = @JoinColumn(name = "genero_videojuego_id")) 
	private List<GeneroVideojuego> generos; 

	@ManyToMany(mappedBy = "videojuegosFavoritas", fetch = FetchType.LAZY) 
	private List<Usuario> usuariosFavoritos;

	@Transient
	private MultipartFile portada;

	@OneToMany(mappedBy = "videojuego", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComentarioVideojuego> comentarios = new ArrayList<>();

   
	public Videojuego(Integer id, @NotBlank String titulo, @NotNull LocalDate fechaEstreno, String rutaPortada) {
		this.id = id;
		this.titulo = titulo;
		this.rutaPortada = rutaPortada;
		this.fechaEstreno = fechaEstreno;
	}

   
	public Videojuego(@NotBlank String titulo, @NotBlank String descripcion, @NotNull LocalDate fechaEstreno,
			@NotBlank String youtubeTrailerId, String rutaPortada, @NotEmpty List<GeneroVideojuego> generos) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaEstreno = fechaEstreno;
		this.youtubeTrailerId = youtubeTrailerId;
		this.rutaPortada = rutaPortada;
		this.generos = generos;
	}

   
    public Videojuego(@NotBlank String titulo, @NotBlank String descripcion, @NotNull LocalDate fechaEstreno,
            @NotBlank String youtubeTrailerId, String rutaPortada, @NotEmpty List<GeneroVideojuego> generos,
            MultipartFile portada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEstreno = fechaEstreno;
        this.youtubeTrailerId = youtubeTrailerId;
        this.rutaPortada = rutaPortada;
        this.generos = generos;
        this.portada = portada; // Este campo no se persiste
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Videojuego))
			return false;
		Videojuego videojuego = (Videojuego) o;
		return id != null && id.equals(videojuego.id);
	}

	@Override
	public int hashCode() {
		return 31;
	}
}