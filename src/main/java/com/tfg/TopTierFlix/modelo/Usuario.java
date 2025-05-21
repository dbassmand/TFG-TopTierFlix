package com.tfg.TopTierFlix.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.CascadeType; 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany; 
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
@ToString(exclude = { "peliculasFavoritas", "seriesFavoritas", "videojuegosFavoritas","comentariosPeliculas", "comentariosSeries", "comentariosVideojuegos" }) // Actualizado
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
	private List<Pelicula> peliculasFavoritas = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_serie_favorita",
			joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "serie_id", referencedColumnName = "id_serie"))
	private List<Serie> seriesFavoritas = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_videojuego_favorita",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "videojuego_id", referencedColumnName = "id_videojuego"))
    private List<Videojuego> videojuegosFavoritas = new ArrayList<>(); 
   
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comentario> comentariosPeliculas = new ArrayList<>();
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ComentarioSerie> comentariosSeries = new ArrayList<>();
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ComentarioVideojuego> comentariosVideojuegos = new ArrayList<>();


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
		return 31;
	}


	public void addSerieFavorita(Serie serie) {
		if (!this.seriesFavoritas.contains(serie)) {
			this.seriesFavoritas.add(serie);
			if (serie.getUsuariosFavoritos() != null) {
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

	public void addPeliculaFavorita(Pelicula pelicula) {
		if (!this.peliculasFavoritas.contains(pelicula)) {
			this.peliculasFavoritas.add(pelicula);
			if (pelicula.getUsuariosFavoritos() != null) {
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

	 public void addVideojuegoFavorito(Videojuego videojuego) {
	        if (!this.videojuegosFavoritas.contains(videojuego)) {
	            this.videojuegosFavoritas.add(videojuego);
	            if (videojuego.getUsuariosFavoritos() != null) {
	                videojuego.getUsuariosFavoritos().add(this);
	            }
	        }
	    }

	    public void removeVideojuegoFavorito(Videojuego videojuego) {
	        this.videojuegosFavoritas.remove(videojuego);
	        if (videojuego.getUsuariosFavoritos() != null) {
	            videojuego.getUsuariosFavoritos().remove(this);
	        }
	    }

	    public boolean isVideojuegoFavorito(Videojuego videojuego) {
	        return this.videojuegosFavoritas.contains(videojuego);
	    }
    // ***************************************************************
    // NUEVOS MÉTODOS DE AYUDA PARA COMENTARIOS (RECOMENDADOS)
    // ***************************************************************

    // Para Comentarios de Películas
    public void addComentarioPelicula(Comentario comentario) {
        if (comentario != null && !this.comentariosPeliculas.contains(comentario)) {
            this.comentariosPeliculas.add(comentario);
            comentario.setUsuario(this); // Asegura la bidireccionalidad
        }
    }

    public void removeComentarioPelicula(Comentario comentario) {
        if (comentario != null) {
            this.comentariosPeliculas.remove(comentario);
            comentario.setUsuario(null); // Desvincula el comentario de este usuario
        }
    }

    // Para Comentarios de Series
    public void addComentarioSerie(ComentarioSerie comentarioSerie) {
        if (comentarioSerie != null && !this.comentariosSeries.contains(comentarioSerie)) {
            this.comentariosSeries.add(comentarioSerie);
            comentarioSerie.setUsuario(this); // Asegura la bidireccionalidad
        }
    }

    public void removeComentarioSerie(ComentarioSerie comentarioSerie) {
        if (comentarioSerie != null) {
            this.comentariosSeries.remove(comentarioSerie);
            comentarioSerie.setUsuario(null); // Desvincula el comentario de este usuario
        }
    }
    
    // --- NUEVOS MÉTODOS DE AYUDA PARA COMENTARIOS DE VIDEOJUEGOS ---
    public void addComentarioVideojuego(ComentarioVideojuego comentarioVideojuego) {
        if (comentarioVideojuego != null && !this.comentariosVideojuegos.contains(comentarioVideojuego)) {
            this.comentariosVideojuegos.add(comentarioVideojuego);
            comentarioVideojuego.setUsuario(this); // Asegura la bidireccionalidad
        }
    }

    public void removeComentarioVideojuego(ComentarioVideojuego comentarioVideojuego) {
        if (comentarioVideojuego != null) {
            this.comentariosVideojuegos.remove(comentarioVideojuego);
            comentarioVideojuego.setUsuario(null); // Desvincula el comentario de este usuario
        }
    }
}