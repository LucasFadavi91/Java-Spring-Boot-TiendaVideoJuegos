package com.lfsdesign.tiendaVideoJuegos.interfaces;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.lfsdesign.tiendaVideoJuegos.models.Juego;

public interface InterfazJuego {

    int insertarJuego(Juego juego);
    
    ArrayList<Juego> selectAllJuegos();
    
    Optional<Juego> selectJuegoById(UUID id);
    
    int borrarJuegoById(UUID id);
    
    int actualizarJuegoById(UUID id, Juego juegoActualizado);
}
