package com.tfg.TopTierFlix.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRegistroDTO {
	
	private Integer id;
	
	@NotEmpty(message = "El nombre no puede estar vacío.")
	private String nombre;
	
	@NotEmpty(message = "El apellido no puede estar vacío.")
	private String apellido;
	
	@NotEmpty(message = "El email no puede estar vacío.")
    @Email(message = "El formato del email no es válido.")
	private String email;
	
	@NotEmpty(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
	private String password;
	
	
	public UsuarioRegistroDTO(String nombre, String apellido, String email, String password) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
	}

	public UsuarioRegistroDTO(String email) {
		super();
		this.email = email;
	}		
}
