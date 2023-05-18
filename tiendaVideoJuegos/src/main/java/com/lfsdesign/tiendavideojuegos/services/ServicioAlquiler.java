package com.lfsdesign.tiendavideojuegos.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lfsdesign.tiendavideojuegos.interfaces.InterfazAlquiler;
import com.lfsdesign.tiendavideojuegos.models.Alquiler;
import com.lfsdesign.tiendavideojuegos.models.Cliente;
import com.lfsdesign.tiendavideojuegos.models.Juego;

@Service
public class ServicioAlquiler {

    private final InterfazAlquiler daoAlquiler;

    @Autowired
    public ServicioAlquiler(@Qualifier("DaoAlquiler") InterfazAlquiler daoAlquiler) {
        this.daoAlquiler = daoAlquiler;
    }

    public int altaAlquiler(Alquiler alquiler) {
        return daoAlquiler.insertarAlquiler(alquiler);
    }

    public List<Alquiler> obtenerTodosLosAlquileres() {
        return daoAlquiler.selectAllAlquileres();
    }

    public Optional<Alquiler> obtenerAlquilerPorId(UUID id) {
        return daoAlquiler.selectAlquilerById(id);
    }

    public int borrarAlquilerPorId(UUID id) {
        return daoAlquiler.borrarAlquilerById(id);
    }

    public int actualizarAlquiler(UUID id, Alquiler alquilerActualizado) {
        return daoAlquiler.actualizarAlquilerById(id, alquilerActualizado);
    }

    public List<Alquiler> obtenerTodosLosAlquileresPorCliente(Cliente cliente) {
        return daoAlquiler.selectAllAlquileresByCliente(cliente);
    }

    public List<Alquiler> obtenerTodosLosAlquileresPorJuego(Juego juego) {
        return daoAlquiler.selectAllAlquileresByJuego(juego);
    }

    public List<Alquiler> obtenerTodosLosAlquileresPorFechaInicio(LocalDate fechaInicio) {
        return daoAlquiler.selectAllAlquileresByFechaInicio(fechaInicio);
    }

    public List<Alquiler> obtenerTodosLosAlquileresPorFechaFin(LocalDate fechaFin) {
        return daoAlquiler.selectAllAlquileresByFechaFin(fechaFin);
    }

}
