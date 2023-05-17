package com.lfsdesign.tiendaVideoJuegos.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lfsdesign.tiendaVideoJuegos.interfaces.InterfazJuego;
import com.lfsdesign.tiendaVideoJuegos.models.Juego;

@Service
public class ServicioJuego {

    private final InterfazJuego daoJuego;

    @Autowired
    public ServicioJuego(@Qualifier("DaoJuego") InterfazJuego daoJuego) {
        this.daoJuego = daoJuego;
    }

    public int altaJuego(Juego juego) {
        return daoJuego.insertarJuego(juego);
    }

    public ArrayList<Juego> obtenerTodosLosJuegos() {
        return daoJuego.selectAllJuegos();
    }

    public Optional<Juego> obtenerJuegoPorId(UUID id) {
        Optional<Juego> juego = daoJuego.selectJuegoById(id);
        if (juego.isPresent()) {
            return juego;
        } else {
            return Optional.empty();
        }
    }

    public int borrarJuego(UUID id) {
        return daoJuego.borrarJuegoById(id);
    }

    public int actualizarJuego(UUID id, Juego juegoActualizado) {
        return daoJuego.actualizarJuegoById(id, juegoActualizado);
    }

}
