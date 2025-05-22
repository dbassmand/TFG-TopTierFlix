package com.tfg.TopTierFlix.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tfg.TopTierFlix.modelo.GeneroMusica;
import com.tfg.TopTierFlix.repositorios.GeneroMusicaRepositorio;

@Service
public class GeneroMusicaServicioImpl implements GeneroMusicaServicio{
	
	@Autowired
	GeneroMusicaRepositorio generoMusicaRepositorio;

	@Override
	public List<GeneroMusica> obtenerTodosGeneros(Sort sort) {

		return generoMusicaRepositorio.findAll(sort);
	}
}
