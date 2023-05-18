package com.lfsdesign.tiendavideojuegos.interfaces;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lfsdesign.tiendavideojuegos.models.Alquiler;
import com.lfsdesign.tiendavideojuegos.models.Cliente;
import com.lfsdesign.tiendavideojuegos.models.Juego;

public interface InterfazAlquiler {

    int insertarAlquiler(Alquiler alquiler);

    ArrayList<Alquiler> selectAllAlquileres();

    Optional<Alquiler> selectAlquilerById(UUID id);

    int borrarAlquilerById(UUID id);

    int actualizarAlquilerById(UUID id, Alquiler alquilerActualizado);

    List<Alquiler> selectAllAlquileresByCliente(Cliente cliente);

    List<Alquiler> selectAllAlquileresByJuego(Juego juego);

    List<Alquiler> selectAllAlquileresByFechaInicio(LocalDate fechaInicio);

    List<Alquiler> selectAllAlquileresByFechaFin(LocalDate fechaFin);

}
