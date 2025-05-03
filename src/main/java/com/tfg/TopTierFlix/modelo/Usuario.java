package com.tfg.TopTierFlix.modelo;

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

@Entity
@Table(name="usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido")
	private String apellido;
	
	 @Column(unique = true)
	private String email;
	 
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)//carga completa, al solo haver 2 roles no afecta al rendimiento.
	@JoinTable(
	        name = "usuarios_roles",
	        joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
	        inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
	private Collection<Rol> roles;
	
	@ManyToMany(fetch = FetchType.LAZY) // Carga lazy para no cargar todas las favoritas al cargar el usuario
	@JoinTable(
	        name = "usuarios_favoritas",
	        joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
	        inverseJoinColumns = @JoinColumn(name = "pelicula_id", referencedColumnName = "id_pelicula")) 
	private List<Pelicula> peliculasFavoritas;
	
	//Constructor sin Id
	public Usuario(String nombre, String apellido, String email, String password, Collection<Rol> roles) {
		super();
		this.nombre=nombre;
		this.apellido=apellido;
		this.email=email;
		this.password=password;
		this.roles=roles;
	}

}
