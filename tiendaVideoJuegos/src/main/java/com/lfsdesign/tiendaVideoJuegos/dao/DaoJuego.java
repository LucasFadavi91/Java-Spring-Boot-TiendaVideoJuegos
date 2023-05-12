package com.lfsdesign.tiendaVideoJuegos.dao;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lfsdesign.tiendaVideoJuegos.interfaces.InterfazJuego;
import com.lfsdesign.tiendaVideoJuegos.models.Juego;

@Repository("DaoJuego")
public class DaoJuego implements InterfazJuego {

	private ArrayList<Juego> juegos = new ArrayList<>();

	@Override
	public int insertarJuego(Juego juego) {
		juegos.add(new Juego(UUID.randomUUID(), juego.getNombre(), juego.getPrecio(), juego.getStock()));
		return 1;
	}

	@Override
	public ArrayList<Juego> selectAllJuegos() {
	    if (juegos == null) {
	        juegos = new ArrayList<Juego>();
	    }
	    return juegos;
	}

	@Override
	public Optional<Juego> selectJuegoById(UUID id) {
	    for (Juego juego : juegos) {
	        if (juego.getId().equals(id)) {
	            return Optional.of(juego);
	        }
	    }
	    return Optional.empty();
	}

	@Override
	public int borrarJuegoById(UUID id) {

		Optional<Juego> juegoSeleccionado = selectJuegoById(id);
		if (juegoSeleccionado.isEmpty()) {
			return 0;
		}
		juegos.remove(juegoSeleccionado.get());
		return 1;
	}

	@Override
	public int actualizarJuegoById(UUID id, Juego juegoActualizado) {
		
		return selectJuegoById(id).map(juego -> {
			int indiceDelJuego = juegos.indexOf(juego);
			if (indiceDelJuego >= 0) {
				juegos.set(indiceDelJuego, new Juego(juegoActualizado.getId(), juegoActualizado.getNombre(), juegoActualizado.getPrecio(),
						juegoActualizado.getStock()));
				return 1;
			}
			return 0;
		}).orElse(0);
	}
	
}
