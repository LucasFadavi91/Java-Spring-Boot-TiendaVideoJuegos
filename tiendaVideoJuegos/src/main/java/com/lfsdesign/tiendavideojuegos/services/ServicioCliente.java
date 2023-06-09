package com.lfsdesign.tiendavideojuegos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lfsdesign.tiendavideojuegos.interfaces.InterfazCliente;
import com.lfsdesign.tiendavideojuegos.models.Cliente;

@Service
public class ServicioCliente {
	
	private final InterfazCliente daoCliente;
	
	@Autowired
	public ServicioCliente(@Qualifier("DaoCliente") InterfazCliente daoCliente) {
		this.daoCliente = daoCliente;
	}
	
	public int altaCliente(Cliente cliente) {
		return daoCliente.insertarCliente(cliente);
	}
	
	public List<Cliente> obtenerTodosLosClientes() {
		return daoCliente.selectAllClientes();
	}
	
	public Optional<Cliente> obtenerClientePorDni(String dni) {
		return daoCliente.selectClienteByDni(dni);
	}
	
	public int borrarCliente(String dni) {
		return daoCliente.borrarClienteByDni(dni);
	}
	
	public int actualizarCliente(String dni, Cliente nuevoCliente) {
		return daoCliente.actualizarClienteByDni(dni, nuevoCliente);
	}
	
}
