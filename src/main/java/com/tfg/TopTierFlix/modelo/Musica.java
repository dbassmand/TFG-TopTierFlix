package com.tfg.TopTierFlix.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

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
public class Musica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_musica")
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
	@JoinTable(name = "musica_genero_musica", 
			joinColumns = @JoinColumn(name = "musica_id"), 
			inverseJoinColumns = @JoinColumn(name = "genero_musica_id")) 
	private List<GeneroMusica> generos; 

	@ManyToMany(mappedBy = "musicasFavoritas", fetch = FetchType.LAZY) 
	private List<Usuario> usuariosFavoritos;

	@Transient
	private MultipartFile portada;

	@OneToMany(mappedBy = "musica", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComentarioMusica> comentarios = new ArrayList<>();
}
