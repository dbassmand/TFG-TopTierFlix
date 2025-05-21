package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tfg.TopTierFlix.modelo.GeneroVideojuego;
import com.tfg.TopTierFlix.repositorios.GeneroVideojuegoRepositorio;

@Service
public class GeneroVideojuegoServicioImpl implements GeneroVideojuegoServicio{
	
	@Autowired
	GeneroVideojuegoRepositorio generoVideojuegoRepositorio;

	@Override
	public List<GeneroVideojuego> obtenerTodosGeneros(Sort sort) {

		return generoVideojuegoRepositorio.findAll(sort);
	}

}
