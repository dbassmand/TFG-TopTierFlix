package com.tfg.TopTierFlix.servicio;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.tfg.TopTierFlix.modelo.Rol;
import com.tfg.TopTierFlix.modelo.Usuario;
import com.tfg.TopTierFlix.repositorios.RolRepositorio;
import com.tfg.TopTierFlix.repositorios.UsuarioRepositorio;

import jakarta.annotation.PostConstruct;

@Service
public class AdminInitializer {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${admin.email}") // Lee la variable de entorno ADMIN_EMAIL
    private String adminEmail;

    @Value("${admin.password}") // Lee la variable de entorno ADMIN_PASSWORD
    private String adminPassword;

    @PostConstruct
    @Transactional
    public void initializeAdmin() {
        Rol adminRol = rolRepositorio.findByNombre("ROLE_ADMIN");

        if (usuarioRepositorio.findByEmail(adminEmail) == null) {
            if (adminRol == null) {
                adminRol = new Rol("ROLE_ADMIN");
                rolRepositorio.save(adminRol);
                adminRol = rolRepositorio.findByNombre("ROLE_ADMIN"); // Volvemos a obtenerlo después de guardar
            }

            Usuario adminUser = new Usuario();
            adminUser.setNombre("Admin");
            adminUser.setApellido("Superuser");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRoles(Arrays.asList(adminRol));
            usuarioRepositorio.save(adminUser);
            System.out.println("Administrador inicial creado.");
        }
    }
}