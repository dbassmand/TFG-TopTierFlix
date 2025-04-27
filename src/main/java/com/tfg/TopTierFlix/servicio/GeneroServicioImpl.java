package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tfg.TopTierFlix.modelo.Genero;
import com.tfg.TopTierFlix.repositorios.GeneroRepositorio;

@Service
public class GeneroServicioImpl implements GeneroServicio{
	
	@Autowired
	GeneroRepositorio generoRepositorio;

	@Override
	public List<Genero> obtenerTodosGeneros(Sort sort) {

		return generoRepositorio.findAll(sort);
	}

}
