package com.tfg.TopTierFlix;

import com.tfg.TopTierFlix.modelo.Pelicula;
import com.tfg.TopTierFlix.repositorios.PeliculaRepositorio;
import com.tfg.TopTierFlix.servicio.PeliculaServicioImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeliculaServiceImplTest {

    @Mock
    private PeliculaRepositorio peliculaRepositorio;

    @InjectMocks
    private PeliculaServicioImpl peliculaServicio;

    @Test
    void obtenerPeliculaPorId_peliculaExistente_devuelvePelicula() {
        // Arrange (preparar)
        Integer peliculaId = 1;
        Pelicula peliculaEsperada = new Pelicula();
        peliculaEsperada.setId(peliculaId);
        peliculaEsperada.setTitulo("Título de prueba");

        when(peliculaRepositorio.findById(peliculaId)).thenReturn(Optional.of(peliculaEsperada));

        // Se ejecuta
        Pelicula peliculaActual = peliculaServicio.obtenerPeliculaPorId(peliculaId);

        // Se verifica
        assertEquals(peliculaEsperada, peliculaActual);
    }

    @Test
    void obtenerPeliculaPorId_peliculaNoExistente_lanzaExcepcion() {
        // Se ejecuta
        Integer peliculaId = 9999;
        when(peliculaRepositorio.findById(peliculaId)).thenReturn(Optional.empty());

        // Se verifica
        assertThrows(IllegalArgumentException.class, () -> peliculaServicio.obtenerPeliculaPorId(peliculaId));
        assertEquals("Película no encontrada con ID: 99", assertThrows(IllegalArgumentException.class, () -> peliculaServicio.obtenerPeliculaPorId(peliculaId))
        		.getMessage());
    }
}