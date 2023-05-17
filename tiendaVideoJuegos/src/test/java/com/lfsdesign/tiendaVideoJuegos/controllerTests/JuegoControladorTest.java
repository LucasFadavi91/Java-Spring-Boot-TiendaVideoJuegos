package com.lfsdesign.tiendaVideoJuegos.controllerTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;
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
import com.lfsdesign.tiendaVideoJuegos.controllers.JuegoControlador;
import com.lfsdesign.tiendaVideoJuegos.dao.DaoJuego;
import com.lfsdesign.tiendaVideoJuegos.models.Juego;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioJuego;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class JuegoControladorTest {

    @Mock
    private ServicioJuego servicioJuego;

    @Mock
    private DaoJuego daoJuego;
    
    @InjectMocks
    private JuegoControlador juegoControlador;
    
    private MockMvc mockMvc;

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

        mockMvc = MockMvcBuilders.standaloneSetup(juegoControlador).build();
        daoJuego = mock(DaoJuego.class);
    }

	@Test
	public void testAddJuego() throws Exception {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String juegoJson = objectMapper.writeValueAsString(juego1);

			mockMvc.perform(post("/controller/v1/juego").contentType(MediaType.APPLICATION_JSON).content(juegoJson))
					.andExpect(status().isOk());

			verify(servicioJuego, times(1)).altaJuego(juego1);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
    
    @Test
    public void testGetAllJuegos() throws Exception {
    	
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
    }
    
    @Test
    public void testDeleteJuegoById() throws Exception {
    	try {
	        mockMvc.perform(delete("/controller/v1/juego/{id}", juego1.getId()))
	                .andExpect(status().isOk());
	        verify(servicioJuego, times(1)).borrarJuego(juego1.getId());
	    } catch (Exception e) {
	        fail("Error al borrar el juego: " + e.getMessage());
	    }
    }

    @Test
    public void testActualizarJuegoById() throws Exception {
    	  	
	  Juego juegoActualizado = new Juego(juego1.getId(), juego1.getNombre(), juego1.getPrecio(), juego1.getStock());
      when(daoJuego.actualizarJuegoById(eq(juego1.getId()), eq(juegoActualizado))).thenReturn(1);
      int resultado = daoJuego.actualizarJuegoById(juego1.getId(), juegoActualizado);
      verify(daoJuego, times(1)).actualizarJuegoById(eq(juego1.getId()), eq(juegoActualizado));
      assertEquals(1, resultado);
    }
    
}
