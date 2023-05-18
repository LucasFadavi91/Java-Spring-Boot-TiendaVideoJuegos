package com.lfsdesign.tiendavideojuegos.interfaces;

import java.util.List;
import java.util.Optional;

import com.lfsdesign.tiendavideojuegos.models.Cliente;

public interface InterfazCliente {

	int insertarCliente(Cliente cliente);
	
	List<Cliente> selectAllClientes();
	
	Optional<Cliente> selectClienteByDni(String dni);
	
	int borrarClienteByDni(String dni);
	
	int actualizarClienteByDni(String dni, Cliente clienteActualizado);

}
