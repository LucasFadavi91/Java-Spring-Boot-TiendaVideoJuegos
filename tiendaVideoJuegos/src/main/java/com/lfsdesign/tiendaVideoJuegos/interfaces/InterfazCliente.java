package com.lfsdesign.tiendaVideoJuegos.interfaces;

import java.util.List;
import java.util.Optional;

import com.lfsdesign.tiendaVideoJuegos.models.Cliente;

public interface InterfazCliente {

	int insertarCliente(String dni, Cliente cliente);
	
	List<Cliente> selectAllClientes();
	
	Optional<Cliente> selectClienteByDni(String dni);
	
	int borrarClienteByDni(String dni);
	
	int actualizarClienteByDni(String dni, Cliente clienteActualizado);

}
