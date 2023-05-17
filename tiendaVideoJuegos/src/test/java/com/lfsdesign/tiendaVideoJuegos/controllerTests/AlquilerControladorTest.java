package com.lfsdesign.tiendaVideoJuegos.controllerTests;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

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
import com.lfsdesign.tiendaVideoJuegos.controllers.AlquilerControlador;
import com.lfsdesign.tiendaVideoJuegos.dao.DaoAlquiler;
import com.lfsdesign.tiendaVideoJuegos.models.Alquiler;
import com.lfsdesign.tiendaVideoJuegos.models.Cliente;
import com.lfsdesign.tiendaVideoJuegos.models.Juego;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioAlquiler;



@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class AlquilerControladorTest {

    @Mock
    private ServicioAlquiler servicioAlquiler;
    
    @Mock
    private DaoAlquiler daoAlquiler;

    @InjectMocks
    private AlquilerControlador alquilerControlador;

    private MockMvc mockMvc;
    
    private Alquiler alquiler1;
    private Alquiler alquiler2;
    private Alquiler alquiler3;
    private Cliente cliente1;
    private Cliente cliente2;
    private Juego juego1;
    private Juego juego2;
    private Juego juego3;
    private ArrayList<Alquiler> alquileres;
    
    @BeforeEach
    public void setUp() {
    
    	String dni1 = "12345678A";
        String dni2 = "45235325B";
    	cliente1 = new Cliente(dni1, "Cliente1", "test1@test.com", "Dirección1");
        cliente2 = new Cliente(dni2, "Cliente2", "test2@test.com", "Dirección2");
        juego1 = new Juego(UUID.randomUUID(), "Juego Test 1", 19.99, 10);
        juego2 = new Juego(UUID.randomUUID(), "Juego Test 2", 29.99, 5);
        juego3 = new Juego(UUID.randomUUID(), "Juego Test ", 30, 2);
    	alquiler1 = new Alquiler(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusDays(1), juego1, cliente1);
    	alquiler2 = new Alquiler(UUID.randomUUID(), LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), juego2, cliente2);
    	alquiler3 = new Alquiler(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusDays(1), juego3, cliente1);
    	alquileres = new ArrayList<>();
    	alquileres.add(alquiler1);
    	alquileres.add(alquiler2);
    	alquileres.add(alquiler3);
        mockMvc = MockMvcBuilders.standaloneSetup(alquilerControlador).build();
        daoAlquiler = mock(DaoAlquiler.class);
    }

    @Test
	public void testAddAlquiler() throws Exception {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String alquilerJson = objectMapper.writeValueAsString(alquiler1);

			when(servicioAlquiler.altaAlquiler(any(Alquiler.class))).thenReturn(1);
			mockMvc.perform(
					post("/controller/v1/alquiler").contentType(MediaType.APPLICATION_JSON).content(alquilerJson))
					.andExpect(status().isOk()).andExpect(content().string("1"));
			verify(servicioAlquiler, times(1)).altaAlquiler(alquiler1);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

    @Test
    public void testGetAllAlquileres() throws Exception {
    	
        when(servicioAlquiler.obtenerTodosLosAlquileres()).thenReturn(alquileres);
        mockMvc.perform(get("/controller/v1/alquiler"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].id", is(alquiler1.getId().toString())))
            .andExpect(jsonPath("$[0].cliente.dni", is(cliente1.getDni())))
            .andExpect(jsonPath("$[0].juego.id", is(juego1.getId().toString())))
            .andExpect(jsonPath("$[1].id", is(alquiler2.getId().toString())))
            .andExpect(jsonPath("$[1].cliente.dni", is(cliente2.getDni())))
            .andExpect(jsonPath("$[1].juego.id", is(juego2.getId().toString())))
            .andExpect(jsonPath("$[2].id", is(alquiler3.getId().toString())))
            .andExpect(jsonPath("$[2].cliente.dni", is(cliente1.getDni())))
            .andExpect(jsonPath("$[2].juego.id", is(juego3.getId().toString())));

        verify(servicioAlquiler, times(1)).obtenerTodosLosAlquileres();
    }

	@Test
    public void testGetAlquilerById() throws Exception {
    	
        when(servicioAlquiler.obtenerAlquilerPorId(alquiler2.getId())).thenReturn(Optional.of(alquiler2));
        mockMvc.perform(get("/controller/v1/alquiler/" + alquiler2.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(alquiler2.getId().toString())));
        verify(servicioAlquiler, times(1)).obtenerAlquilerPorId(alquiler2.getId());
    }

    @Test
    public void testDeleteAlquilerById() {
        try {
        	 mockMvc.perform(delete("/controller/v1/alquiler/{id}", alquiler2.getId()))
             .andExpect(status().isOk());
            verify(servicioAlquiler, times(1)).borrarAlquilerPorId(alquiler2.getId());
        } catch (Exception e) {
            fail("Error al borrar el alquiler: " + e.getMessage());
        }
    }
        
    @Test
    public void testActualizarAlquilerById() {

        Alquiler alquilerActualizado = new Alquiler(alquiler1.getId(), alquiler1.getFechaInicio(),
                alquiler1.getFechaFin(), juego1, cliente1);
        when(daoAlquiler.actualizarAlquilerById(eq(alquiler1.getId()), eq(alquilerActualizado))).thenReturn(1);
        int resultado = daoAlquiler.actualizarAlquilerById(alquiler1.getId(), alquilerActualizado);
        verify(daoAlquiler, times(1)).actualizarAlquilerById(eq(alquiler1.getId()), eq(alquilerActualizado));
        assertEquals(1, resultado);
    }
            
}
