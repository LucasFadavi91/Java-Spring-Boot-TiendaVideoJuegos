package com.lfsdesign.tiendavideojuegos.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lfsdesign.tiendavideojuegos.models.Cliente;
import com.lfsdesign.tiendavideojuegos.interfaces.InterfazCliente;

@Repository("DaoCliente")
public class DaoCliente implements InterfazCliente {

	private final List<Cliente> clientes = new ArrayList<>();

	@Override
	public int insertarCliente(Cliente cliente) {
		clientes.add(new Cliente(cliente.getDni(), cliente.getNombre(), cliente.getEmail(), cliente.getDireccion()));
		return 1;
	}

	@Override
	public List<Cliente> selectAllClientes() {
		return clientes;
	}

	@Override
	public Optional<Cliente> selectClienteByDni(String dni) {
		return clientes.stream().filter(cliente -> cliente.getDni().equals(dni)).findFirst();
	}

	@Override
	public int borrarClienteByDni(String dni) {
		
		Optional<Cliente> clienteSeleccionado = selectClienteByDni(dni);
		if (clienteSeleccionado.isEmpty()) {
			return 0;
		}
		clientes.remove(clienteSeleccionado.get());
		return 1;
	}

	@Override
	public int actualizarClienteByDni(String dni, Cliente clienteActualizado) {
		
		return selectClienteByDni(dni).map(cliente -> {
			int indiceDelCliente = clientes.indexOf(cliente);
			if (indiceDelCliente >= 0) {
				clientes.set(indiceDelCliente, new Cliente(dni, clienteActualizado.getNombre(),
						clienteActualizado.getEmail(), clienteActualizado.getDni()));
				return 1;
			}
			return 0;
		}).orElse(0);
	}
}
