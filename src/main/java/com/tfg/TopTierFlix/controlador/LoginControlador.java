package com.tfg.TopTierFlix.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControlador {

    @GetMapping("/login")
    public String mostrarFormularioDeLogin() {
        return "login";
    }
}