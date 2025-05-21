package com.tfg.TopTierFlix.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "peliculasFavoritas", "seriesFavoritas" })
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;

	@Column(unique = true)
	private String email;

	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
	private Collection<Rol> roles;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_pelicula_favorita", 
			joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pelicula_id", referencedColumnName = "id_pelicula"))
	private List<Pelicula> peliculasFavoritas = new ArrayList<>(); // Inicializa para evitar NullPointerException

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_serie_favorita", 
			joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "serie_id", referencedColumnName = "id_serie"))
	private List<Serie> seriesFavoritas = new ArrayList<>(); // Inicializa para evitar NullPointerException

	// Constructor sin Id
	public Usuario(String nombre, String apellido, String email, String password, Collection<Rol> roles) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Usuario))
			return false;
		Usuario usuario = (Usuario) o;
		return id != null && id.equals(usuario.id);
	}

	@Override
	public int hashCode() {
		return 31; // Un número constante si solo usas el ID, o puedes usar Objects.hash(id) si id
					// no es null
	}

	// ************ MUY IMPORTANTE: Añade los métodos de ayuda (o asegúrate de que
	// existen) ************
	// Para Series
	public void addSerieFavorita(Serie serie) {
		if (!this.seriesFavoritas.contains(serie)) {
			this.seriesFavoritas.add(serie);
			if (serie.getUsuariosFavoritos() != null) { // Asegura que la colección no sea null
				serie.getUsuariosFavoritos().add(this);
			}
		}
	}

	public void removeSerieFavorita(Serie serie) {
		this.seriesFavoritas.remove(serie);
		if (serie.getUsuariosFavoritos() != null) {
			serie.getUsuariosFavoritos().remove(this);
		}
	}

	public boolean isSerieFavorita(Serie serie) {
		return this.seriesFavoritas.contains(serie);
	}

	// Para Películas
	public void addPeliculaFavorita(Pelicula pelicula) {
		if (!this.peliculasFavoritas.contains(pelicula)) {
			this.peliculasFavoritas.add(pelicula);
			if (pelicula.getUsuariosFavoritos() != null) { // Asegura que la colección no sea null
				pelicula.getUsuariosFavoritos().add(this);
			}
		}
	}

	public void removePeliculaFavorita(Pelicula pelicula) {
		this.peliculasFavoritas.remove(pelicula);
		if (pelicula.getUsuariosFavoritos() != null) {
			pelicula.getUsuariosFavoritos().remove(this);
		}
	}

	public boolean isPeliculaFavorita(Pelicula pelicula) {
		return this.peliculasFavoritas.contains(pelicula);
	}
}
