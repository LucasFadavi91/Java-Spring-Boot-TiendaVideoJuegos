package com.lfsdesign.tiendaVideoJuegos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lfsdesign.tiendaVideoJuegos.dao.DaoCliente;
import com.lfsdesign.tiendaVideoJuegos.models.Cliente;

@Service
public class ServicioCliente {
	
	private final DaoCliente daoCliente;
	
	@Autowired
	public ServicioCliente(@Qualifier("DaoCliente") DaoCliente daoCliente) {
		this.daoCliente = daoCliente;
	}
	
	public int altaCliente(Cliente cliente) {
		return daoCliente.insertarCliente(cliente.getDni(), cliente);
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
