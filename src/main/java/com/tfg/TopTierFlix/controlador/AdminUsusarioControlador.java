package com.tfg.TopTierFlix.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.servicio.UsuarioServicioImpl;


@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsusarioControlador {
	
	@Autowired
	private UsuarioServicioImpl usuarioServicio;
	
	@ModelAttribute("usuario")
	public Usuario getUsuario(@RequestParam(required = false) Integer id) {
		System.out.println("Preparando pobjeto usuario para ID"+id);
		if(id != null) {
			return usuarioServicio.obtenerUsuarioPorId(id);
		}
		return new Usuario();
	}
	
	@GetMapping("")
	public ModelAndView verListadoUsuarios(@PageableDefault(sort="nombre",size=15)Pageable pageable) {
	 Page<Usuario> usuarios = usuarioServicio.obtenerTodosUsuariosPaginado(pageable);
	 return new ModelAndView("admin/usuarios/lista-usuarios").addObject("usuarios",usuarios);
	}
	
	@GetMapping("/buscar")
	public ModelAndView buscarUsuarios(@RequestParam(value = "termino", required = false) String termino,
			@PageableDefault(sort="nombre", size=15)Pageable pageable) {
		Page<Usuario> resultados;
		if (termino != null && !termino.trim().isEmpty()) {
			resultados = usuarioServicio.buscarPorNombreApellidoEmail(termino, pageable);		
		}else {
			return new ModelAndView("redirect:/admin/usuarios");
			//resultados = usuarioServicio.obtenerTodosUsuariosPaginado(pageable);
		}
		return new ModelAndView("admin/usuarios/lista-usuarios")
				.addObject("usuarios",resultados)
				.addObject("terminoBusqueda",termino);
	}
		
	@GetMapping("/{id}/detalles")
	public ModelAndView mostrarDetalleDeUsuario(@PathVariable Integer id) {
		Usuario usuario = usuarioServicio.obtenerUsuarioPorIdConFavoritas(id);
		ModelAndView modelAndView = new ModelAndView("admin/usuarios/detalle-usuario")
				.addObject("usuario",usuario);
		return modelAndView;
	}
	
	@PostMapping("/{id}/eliminar")
	public String eliminarUsuario(@PathVariable Integer id) {
		Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
		usuarioServicio.eliminarUsuario(usuario);
		return "redirect:/admin/usuarios";
	}

}
