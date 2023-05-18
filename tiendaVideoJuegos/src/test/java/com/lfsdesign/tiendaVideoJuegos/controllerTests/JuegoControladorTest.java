package com.lfsdesign.tiendaVideoJuegos.controllerTests;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import java.util.Optional;
import java.util.UUID;

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
import com.lfsdesign.tiendaVideoJuegos.controllers.JuegoControlador;
import com.lfsdesign.tiendaVideoJuegos.dao.DaoJuego;
import com.lfsdesign.tiendaVideoJuegos.models.Juego;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioJuego;

@WebMvcTest(JuegoControlador.class)
@ExtendWith(MockitoExtension.class)
public class JuegoControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioJuego servicioJuego;

    @Mock
    private DaoJuego daoJuego;

    @InjectMocks
    private JuegoControlador juegoControlador;

    private Juego juego1;
    private Juego juego2;
    private ArrayList<Juego> juegos;

    @BeforeEach
    public void setUp() {
    	
    	UUID id1 = UUID.randomUUID();
    	UUID id2 = UUID.randomUUID();
        juego1 = new Juego(id1, "Juego Test 1", 19.99, 10);
        juego2 = new Juego(id2, "Juego Test 2", 29.99, 5);
        juegos = new ArrayList<>();
        juegos.add(juego1);
        juegos.add(juego2);
      
    }

	@Test
	public void testAddJuego() throws Exception {
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String juegoJson = objectMapper.writeValueAsString(juego1);

			mockMvc.perform(post("/controller/v1/juego").contentType(MediaType.APPLICATION_JSON).content(juegoJson))
					.andExpect(status().isOk());

			when(servicioJuego.altaJuego(any(Juego.class))).thenReturn(1);
		} catch (Exception e) {
			fail("Error al dar de alta el juego: " + e.getMessage());
		}
	}


    @Test
    public void testGetAllJuegos() throws Exception {
    	
	    try {	
			when(servicioJuego.obtenerTodosLosJuegos()).thenReturn(juegos);
			mockMvc.perform(get("/controller/v1/juego")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
					.andExpect(jsonPath("$[0].id", is(juego1.getId().toString())))
					.andExpect(jsonPath("$[0].nombre", is(juego1.getNombre())))
					.andExpect(jsonPath("$[0].precio", is(juego1.getPrecio())))
					.andExpect(jsonPath("$[0].stock", is(juego1.getStock())))
					.andExpect(jsonPath("$[1].id", is(juego2.getId().toString())))
					.andExpect(jsonPath("$[1].nombre", is(juego2.getNombre())))
					.andExpect(jsonPath("$[1].precio", is(juego2.getPrecio())))
					.andExpect(jsonPath("$[1].stock", is(juego2.getStock())));
	
				verify(servicioJuego, times(1)).obtenerTodosLosJuegos();
	    } catch (Exception e) {
			fail("Error al obtener los juegos: " + e.getMessage());
		}
    }
    
    @Test
    public void testDeleteJuegoById() throws Exception {
    	
    	try {
	        mockMvc.perform(delete("/controller/v1/juego/{id}", juego1.getId()))
	                .andExpect(status().isOk());
	        verify(servicioJuego, times(1)).borrarJuego(juego1.getId());
	    } catch (Exception e) {
	        fail("Error al dar de baja el juego: " + e.getMessage());
	    }
    }
    
	@Test
    public void testGetJuegoById() throws Exception {
		
    	try {
	        when(servicioJuego.obtenerJuegoPorId(juego1.getId())).thenReturn(Optional.of(juego1));
	        mockMvc.perform(get("/controller/v1/juego/" + juego1.getId()))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.id", is(juego1.getId().toString())));
	        verify(servicioJuego, times(1)).obtenerJuegoPorId(juego1.getId());
		} catch (Exception e) {
			fail("Error al obtener el juego: " + e.getMessage());
		}
    }

	@Test
	public void testActualizarJuegoById() throws Exception {
		
		try {	
			Juego juegoActualizado = new Juego(juego1.getId(), juego1.getNombre(), juego1.getPrecio(), juego1.getStock());
			ObjectMapper objectMapper = new ObjectMapper();
	
			when(servicioJuego.actualizarJuego(any(), any())).thenReturn(1);
			mockMvc.perform(put("/controller/v1/juego/{id}", juego1.getId()).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(juegoActualizado))).andExpect(status().isOk());
			verify(servicioJuego, times(1)).actualizarJuego(any(), any());
		} catch (Exception e) {
			fail("Error al actualizar el juego: " + e.getMessage());
		}
	}
    
}
