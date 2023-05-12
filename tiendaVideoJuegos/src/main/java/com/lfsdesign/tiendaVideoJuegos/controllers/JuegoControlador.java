package com.lfsdesign.tiendaVideoJuegos.controllers;

import java.util.ArrayList;
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

import com.lfsdesign.tiendaVideoJuegos.models.Juego;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioJuego;

@RestController
@RequestMapping("controller/v1/juego")
public class JuegoControlador {

	private final ServicioJuego servicioJuego;

	@Autowired
	public JuegoControlador(ServicioJuego servicioJuego) {
		this.servicioJuego = servicioJuego;
	}

	@PostMapping()
	public void addJuego(@RequestBody Juego juego) {
		juego.setId(UUID.randomUUID());
		servicioJuego.altaJuego(juego);
	}

	@GetMapping
	public ArrayList<Juego> getAllJuegos() {
		return servicioJuego.obtenerTodosLosJuegos();
	}

	@GetMapping(path = "/{id}")
	public Juego getJuegoById(@PathVariable("id") UUID id) {
		return servicioJuego.obtenerJuegoPorId(id).orElse(null);
	}

	@DeleteMapping(path = "/{id}")
	public void deleteJuegoById(@PathVariable("id") UUID id) {
		servicioJuego.borrarJuego(id);
	}

	@PutMapping(path = "/{id}")
	public void actualizarJuegoById(@PathVariable("id") UUID id, @RequestBody Juego juegoActualizado) {
		servicioJuego.actualizarJuego(id, juegoActualizado);
	}
}
