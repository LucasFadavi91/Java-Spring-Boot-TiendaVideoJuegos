package com.lfsdesign.tiendaVideoJuegos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lfsdesign.tiendaVideoJuegos.models.Cliente;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioCliente;

@RestController
@RequestMapping("controller/v1/cliente")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ClienteControlador {

	private final ServicioCliente servicioCliente;

	@Autowired
	public ClienteControlador(ServicioCliente servicioCliente) {
		this.servicioCliente = servicioCliente;
	}

	@PostMapping()
	public void addCliente(@RequestBody Cliente cliente) {
		servicioCliente.altaCliente(cliente);
	}

	@GetMapping
	public List<Cliente> getAllClientes() {
		return servicioCliente.obtenerTodosLosClientes();
	}

	@GetMapping(path = "/{dni}")
	public Cliente getClienteByDni(@PathVariable("dni") String dni) {
		return servicioCliente.obtenerClientePorDni(dni).orElse(null);
	}

	@DeleteMapping(path = "/{dni}")
	public void deleteClienteByDni(@PathVariable("dni") String dni) {
		servicioCliente.borrarCliente(dni);
	}

	@PutMapping(path = "/{dni}")
	public void actualizarClienteByDni(@PathVariable("dni") String dni, @RequestBody Cliente clienteActualizado) {
		servicioCliente.actualizarCliente(dni, clienteActualizado);
	}

}
