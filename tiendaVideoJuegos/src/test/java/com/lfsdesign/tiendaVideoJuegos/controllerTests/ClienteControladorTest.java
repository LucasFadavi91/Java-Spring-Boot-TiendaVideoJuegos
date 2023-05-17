package com.lfsdesign.tiendaVideoJuegos.controllerTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfsdesign.tiendaVideoJuegos.controllers.ClienteControlador;
import com.lfsdesign.tiendaVideoJuegos.dao.DaoCliente;
import com.lfsdesign.tiendaVideoJuegos.models.Cliente;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioCliente;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class ClienteControladorTest {

    @Mock
    private ServicioCliente servicioCliente;
    
    @Mock
    private DaoCliente daoCliente;

    @InjectMocks
    private ClienteControlador clienteControlador;

    private MockMvc mockMvc;

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

        mockMvc = MockMvcBuilders.standaloneSetup(clienteControlador).build();
        daoCliente = mock(DaoCliente.class);
    }

    @Test
    public void testAddCliente() throws Exception {
    	
    	try {	
			ObjectMapper objectMapper = new ObjectMapper();
			String clienteJson = objectMapper.writeValueAsString(cliente1);
	
			mockMvc.perform(post("/controller/v1/cliente").contentType(MediaType.APPLICATION_JSON).content(clienteJson))
					.andExpect(status().isOk());
	
			verify(servicioCliente, times(1)).altaCliente(cliente1);
	    } catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
    }
    
    @Test
    public void testGetAllClientes() throws Exception {
		when(servicioCliente.obtenerTodosLosClientes()).thenReturn(clientes);

		mockMvc.perform(get("/controller/v1/cliente")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].dni", is(cliente1.getDni())))
				.andExpect(jsonPath("$[0].nombre", is(cliente1.getNombre())))
				.andExpect(jsonPath("$[1].dni", is(cliente2.getDni())))
				.andExpect(jsonPath("$[1].nombre", is(cliente2.getNombre())));

		verify(servicioCliente, times(1)).obtenerTodosLosClientes();
    }

    @Test
    public void testGetClienteByDni() throws Exception {
    	
        when(servicioCliente.obtenerClientePorDni(cliente1.getDni())).thenReturn(java.util.Optional.of(cliente1));

		mockMvc.perform(get("/controller/v1/cliente/{dni}", cliente1.getDni())).andExpect(status().isOk())
				.andExpect(jsonPath("$.dni", is(cliente1.getDni())))
				.andExpect(jsonPath("$.nombre", is(cliente1.getNombre())));

		verify(servicioCliente, times(1)).obtenerClientePorDni(cliente1.getDni());
    }
    
    @Test
    public void testDeleteClienteByDni() throws Exception {
     
        mockMvc.perform(delete("/controller/v1/cliente/{dni}", cliente1.getDni()))
                .andExpect(status().isOk());

        verify(servicioCliente, times(1)).borrarCliente(cliente1.getDni());
    }
    
	@Test
	public void testActualizarClienteByDni() throws Exception {

		Cliente clienteActualizado = new Cliente(cliente1.getDni(), cliente1.getNombre(), cliente1.getEmail(),
				cliente1.getDireccion());
		when(daoCliente.actualizarClienteByDni(eq(cliente1.getDni()), eq(clienteActualizado))).thenReturn(1);
		int resultado = daoCliente.actualizarClienteByDni(cliente1.getDni(), clienteActualizado);
		verify(daoCliente, times(1)).actualizarClienteByDni(eq(cliente1.getDni()), eq(clienteActualizado));
		assertEquals(1, resultado);
	}
    
}