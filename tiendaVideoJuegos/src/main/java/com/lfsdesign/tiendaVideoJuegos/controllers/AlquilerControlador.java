package com.lfsdesign.tiendaVideoJuegos.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lfsdesign.tiendaVideoJuegos.models.Alquiler;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioAlquiler;

@RestController
@RequestMapping("controller/v1/alquiler")
public class AlquilerControlador {

	private final ServicioAlquiler servicioAlquiler;

	@Autowired
	public AlquilerControlador(ServicioAlquiler servicioAlquiler) {
		this.servicioAlquiler = servicioAlquiler;
	}

	@PostMapping()
	public int addAlquiler(@RequestBody Alquiler alquiler) {
		return servicioAlquiler.altaAlquiler(alquiler);
	}

	@GetMapping
	public List<Alquiler> getAllAlquileres() {
		return servicioAlquiler.obtenerTodosLosAlquileres();
	}

	@GetMapping(path = "/{id}")
	public Alquiler getAlquilerById(@PathVariable("id") UUID id) {
		return servicioAlquiler.obtenerAlquilerPorId(id).orElse(null);
	}

	@DeleteMapping(path = "/{id}")
	public void deleteAlquilerById(@PathVariable("id") UUID id) {
		servicioAlquiler.borrarAlquilerPorId(id);
	}

	@PutMapping(path = "/{id}")
	public void actualizarAlquilerById(@PathVariable("id") UUID id, @RequestBody Alquiler alquilerActualizado) {
		servicioAlquiler.actualizarAlquiler(id, alquilerActualizado);
	}
	
}
