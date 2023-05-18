package com.lfsdesign.tiendaVideoJuegos.models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Alquiler {

    protected UUID id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate fechaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")  
    protected LocalDate fechaFin;
    protected Juego juego;
    protected Cliente cliente;

	public Alquiler(@JsonProperty("id") UUID id, @JsonProperty("fechaInicio") LocalDate fechaInicio,
			@JsonProperty("fechaFin") LocalDate fechaFin, @JsonProperty("juego") Juego juego,
			@JsonProperty("cliente") Cliente cliente) {

		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.juego = juego;
		this.cliente = cliente;
	}

    public UUID getId() {
        return id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Juego getJuego() {
        return juego;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setId(UUID id) {
		this.id = id;
	}

    public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

    public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

    public void setJuego(Juego juego) {
		this.juego = juego;
	}

    public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alquiler)) return false;
        Alquiler alquiler = (Alquiler) o;
        return Objects.equals(id, alquiler.id) &&
               Objects.equals(fechaInicio, alquiler.fechaInicio) &&
               Objects.equals(fechaFin, alquiler.fechaFin) &&
               Objects.equals(juego, alquiler.juego) &&
               Objects.equals(cliente, alquiler.cliente);
    }
  
}
