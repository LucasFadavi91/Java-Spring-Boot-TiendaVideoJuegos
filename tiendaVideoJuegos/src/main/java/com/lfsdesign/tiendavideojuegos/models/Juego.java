package com.lfsdesign.tiendavideojuegos.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Juego {

    protected UUID id;
    protected String nombre;
    protected double precio;
    protected int stock;

    public Juego(@JsonProperty("id") UUID id, @JsonProperty("nombre") String nombre, @JsonProperty("precio") double precio, @JsonProperty("stock") int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

	public void setId(UUID id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
