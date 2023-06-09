package com.lfsdesign.tiendavideojuegos.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cliente {

	protected String dni;
	protected String nombre;
	protected String email;
	protected String direccion;
	
    public Cliente(@JsonProperty("dni") String dni, @JsonProperty("nombre") String nombre, @JsonProperty("email") String email, @JsonProperty("direccion") String direccion) {
    	
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDni(String dni) {
		this.dni = dni;
	}

    public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    public void setEmail(String email) {
		this.email = email;
	}

    public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
