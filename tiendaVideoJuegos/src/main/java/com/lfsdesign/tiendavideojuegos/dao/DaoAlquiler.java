package com.lfsdesign.tiendavideojuegos.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lfsdesign.tiendavideojuegos.interfaces.InterfazAlquiler;
import com.lfsdesign.tiendavideojuegos.models.Alquiler;
import com.lfsdesign.tiendavideojuegos.models.Cliente;
import com.lfsdesign.tiendavideojuegos.models.Juego;

@Repository("DaoAlquiler")
public class DaoAlquiler implements InterfazAlquiler {

    private ArrayList<Alquiler> alquileres = new ArrayList<>();

    @Override
    public int insertarAlquiler(Alquiler alquiler) {
        alquileres.add(new Alquiler(UUID.randomUUID(), alquiler.getFechaInicio(), alquiler.getFechaFin(),
                alquiler.getJuego(), alquiler.getCliente()));
        return 1;
    }

    @Override
    public ArrayList<Alquiler> selectAllAlquileres() {
        if (alquileres == null) {
        	alquileres = new ArrayList<>();
	    }
	    return alquileres;
	}

    @Override
    public Optional<Alquiler> selectAlquilerById(UUID id) {
        return alquileres.stream().filter(alquiler -> alquiler.getId().equals(id)).findFirst();
    }

    @Override
    public int borrarAlquilerById(UUID id) {
        Optional<Alquiler> alquilerSeleccionado = selectAlquilerById(id);
        if (alquilerSeleccionado.isEmpty()) {
            return 0;
        }
        alquileres.remove(alquilerSeleccionado.get());
        return 1;
    }

    @Override
    public int actualizarAlquilerById(UUID id, Alquiler alquilerActualizado) {
    	
        return selectAlquilerById(id).map(alquiler -> {
            int indiceDelAlquiler = alquileres.indexOf(alquiler);
            if (indiceDelAlquiler >= 0) {
                alquileres.set(indiceDelAlquiler, new Alquiler(id, alquilerActualizado.getFechaInicio(),
                        alquilerActualizado.getFechaFin(), alquilerActualizado.getJuego(),
                        alquilerActualizado.getCliente()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }

    @Override
    public List<Alquiler> selectAllAlquileresByCliente(Cliente cliente) {
    	
        List<Alquiler> alquileresCliente = new ArrayList<>();
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente().equals(cliente)) {
                alquileresCliente.add(alquiler);
            }
        }
        return alquileresCliente;
    }

    @Override
    public List<Alquiler> selectAllAlquileresByJuego(Juego juego) {
    	
        List<Alquiler> alquileresJuego = new ArrayList<>();
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getJuego().equals(juego)) {
                alquileresJuego.add(alquiler);
            }
        }
        return alquileresJuego;
    }
    
    public List<Alquiler> selectAllAlquileresByFechaInicio(LocalDate fechaInicio) {
        return alquileres.stream()
            .filter(alquiler -> alquiler.getFechaInicio().compareTo(fechaInicio) >= 0)
            .toList();
    }

    public List<Alquiler> selectAllAlquileresByFechaFin(LocalDate fechaFin) {
        return alquileres.stream()
            .filter(alquiler -> alquiler.getFechaFin().compareTo(fechaFin) <= 0)
            .toList();
    }
    
}