package com.lfsdesign.tiendaVideoJuegos.controllerTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfsdesign.tiendaVideoJuegos.controllers.ClienteControlador;
import com.lfsdesign.tiendaVideoJuegos.dao.DaoCliente;
import com.lfsdesign.tiendaVideoJuegos.models.Cliente;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioCliente;

@WebMvcTest(ClienteControlador.class)
@ExtendWith(MockitoExtension.class)
public class ClienteControladorTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private ServicioCliente servicioCliente;
    
    @Mock
    private DaoCliente daoCliente;

    @InjectMocks
    private ClienteControlador clienteControlador;

    private Cliente cliente1;
    private Cliente cliente2;
    private List<Cliente> clientes;

    @BeforeEach
    public void setUp() {
    	
    	String dni1 = "12345678A";
        String dni2 = "45235325B";
    	cliente1 = new Cliente(dni1, "Cliente1", "test1@test.com", "Dirección1");
        cliente2 = new Cliente(dni2, "Cliente2", "test2@test.com", "Dirección2");
        clientes = new ArrayList<>();
        clientes.add(cliente1);
        clientes.add(cliente2);

    }

    @Test
    public void testAddCliente() throws Exception {
    	
    	try {	
			ObjectMapper objectMapper = new ObjectMapper();
			String clienteJson = objectMapper.writeValueAsString(cliente1);
	
			mockMvc.perform(post("/controller/v1/cliente").contentType(MediaType.APPLICATION_JSON).content(clienteJson))
					.andExpect(status().isOk());
	
			when(servicioCliente.altaCliente(any(Cliente.class))).thenReturn(1);
	    } catch (Exception e) {
	    	fail("Error al dar de alta el cliente: " + e.getMessage());
		}
    }
    
    @Test
    public void testGetAllClientes() throws Exception {
    	
	    try {
			when(servicioCliente.obtenerTodosLosClientes()).thenReturn(clientes);
			mockMvc.perform(get("/controller/v1/cliente")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
					.andExpect(jsonPath("$[0].dni", is(cliente1.getDni())))
					.andExpect(jsonPath("$[0].nombre", is(cliente1.getNombre())))
					.andExpect(jsonPath("$[1].dni", is(cliente2.getDni())))
					.andExpect(jsonPath("$[1].nombre", is(cliente2.getNombre())));
	
			verify(servicioCliente, times(1)).obtenerTodosLosClientes();
	    } catch (Exception e) {
	    	fail("Error al obtener los cliente: " + e.getMessage());
		}
    }

    @Test
    public void testGetClienteByDni() throws Exception {
    	
	    try {
	        when(servicioCliente.obtenerClientePorDni(cliente1.getDni())).thenReturn(java.util.Optional.of(cliente1));
	
			mockMvc.perform(get("/controller/v1/cliente/{dni}", cliente1.getDni())).andExpect(status().isOk())
					.andExpect(jsonPath("$.dni", is(cliente1.getDni())))
					.andExpect(jsonPath("$.nombre", is(cliente1.getNombre())));
	
			verify(servicioCliente, times(1)).obtenerClientePorDni(cliente1.getDni());
	    } catch (Exception e) {
	    	fail("Error al obtener el cliente: " + e.getMessage());
		}
    }
    
    @Test
    public void testDeleteClienteByDni() throws Exception {
    	
	    try {
	        mockMvc.perform(delete("/controller/v1/cliente/{dni}", cliente1.getDni()))
	                .andExpect(status().isOk());
	
	        verify(servicioCliente, times(1)).borrarCliente(cliente1.getDni());
	    } catch (Exception e) {
	    	fail("Error al dar de baja el cliente: " + e.getMessage());
		}
    }
    
	@Test
	public void testActualizarJuegoById() throws Exception {

		Cliente clienteActualizado = new Cliente(cliente1.getDni(), cliente1.getNombre(), cliente1.getEmail(),
				cliente1.getDireccion());
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			when(servicioCliente.actualizarCliente(any(), any())).thenReturn(1);
			mockMvc.perform(put("/controller/v1/cliente/{dni}", cliente1.getDni()).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(clienteActualizado))).andExpect(status().isOk());

			verify(servicioCliente, times(1)).actualizarCliente(any(), any());
		} catch (Exception e) {
			fail("Error al actualizar el cliente: " + e.getMessage());
		}
	}
    
}