package com.lfsdesign.tiendavideojuegos.ServiceTests;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lfsdesign.tiendavideojuegos.dao.DaoAlquiler;
import com.lfsdesign.tiendavideojuegos.models.Alquiler;
import com.lfsdesign.tiendavideojuegos.models.Cliente;
import com.lfsdesign.tiendavideojuegos.models.Juego;
import com.lfsdesign.tiendavideojuegos.services.ServicioAlquiler;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ServicioAlquilerTest {

    @Mock
    private DaoAlquiler daoAlquiler;

    @InjectMocks
    private ServicioAlquiler servicioAlquiler;
    
    private Alquiler alquiler1;
    private Alquiler alquiler2;
    private Alquiler alquiler3;
    private Alquiler alquiler4;
    private Cliente cliente1;
    private Cliente cliente2;
    private Juego juego1;
    private Juego juego2;
    private ArrayList<Alquiler> alquileres;
    private ArrayList<Alquiler> alquileres2;

    @BeforeEach
    public void setUp() {
    	
    	String dni1 = "12345678A";
        String dni2 = "45235325B";
    	cliente1 = new Cliente(dni1, "Cliente1", "test1@test.com", "Dirección1");
        cliente2 = new Cliente(dni2, "Cliente2", "test2@test.com", "Dirección2");
        juego1 = new Juego(UUID.randomUUID(), "Juego Test 1", 19.99, 10);
        juego2 = new Juego(UUID.randomUUID(), "Juego Test 2", 29.99, 5);
    	alquiler1 = new Alquiler(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusDays(1), juego1, cliente1);
    	alquiler2 = new Alquiler(UUID.randomUUID(), LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), juego2, cliente2);
    	alquiler3 = new Alquiler(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusDays(1), juego2, cliente2);
    	alquiler4 = new Alquiler(UUID.randomUUID(), LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), juego1, cliente1);
    	alquileres = new ArrayList<>();
    	alquileres.add(alquiler1);
    	alquileres.add(alquiler2);
    	alquileres2 = new ArrayList<>();
     	alquileres2.add(alquiler3);
    	alquileres2.add(alquiler4);
    	daoAlquiler = mock(DaoAlquiler.class);
    	servicioAlquiler = new ServicioAlquiler(daoAlquiler);
    }

    @Test
    void altaAlquilerTest() {
    	
        when(daoAlquiler.insertarAlquiler(alquiler2)).thenReturn(1);
        int result = servicioAlquiler.altaAlquiler(alquiler2);
        assertEquals(1, result);
    }

    @Test
    void obtenerTodosLosAlquileresTest() {
       
        when(daoAlquiler.selectAllAlquileres()).thenReturn(alquileres);
        List<Alquiler> result = servicioAlquiler.obtenerTodosLosAlquileres();
        assertThat(result, hasSize(alquileres.size()));
        assertThat(result.get(0), equalTo(alquileres.get(0)));
        assertThat(result.get(1), equalTo(alquileres.get(1)));
    }

    @Test
    void obtenerAlquilerPorIdTest() {
    	
        when(daoAlquiler.selectAlquilerById(alquiler2.getId())).thenReturn(Optional.of(alquiler2));
        Optional<Alquiler> result = servicioAlquiler.obtenerAlquilerPorId(alquiler2.getId());
        assertTrue(result.isPresent());
        assertEquals(alquiler2, result.get());
    }

    @Test
    void borrarAlquilerPorIdTest() {
    	
        when(daoAlquiler.borrarAlquilerById(alquiler2.getId())).thenReturn(1);
        int result = servicioAlquiler.borrarAlquilerPorId(alquiler2.getId());
        assertEquals(1, result);
    }

    @Test
    void testActualizarAlquiler() {
    	
        UUID id = UUID.randomUUID();
        when(daoAlquiler.actualizarAlquilerById(eq(id), any(Alquiler.class))).thenReturn(1);
        int resultado = servicioAlquiler.actualizarAlquiler(id, alquiler1);
        assertEquals(1, resultado);
    }

    @Test
    void testObtenerTodosLosAlquileresPorCliente() {
    	
        when(daoAlquiler.selectAllAlquileresByCliente(any(Cliente.class))).thenReturn(alquileres);
        List<Alquiler> resultado = servicioAlquiler.obtenerTodosLosAlquileresPorCliente(cliente1);
        assertEquals(alquileres, resultado);
    }
    
    @Test
    void testObtenerTodosLosAlquileresPorJuego() {
    	
     
        when(daoAlquiler.selectAllAlquileresByJuego(juego1)).thenReturn(alquileres);
        List<Alquiler> resultado1 = servicioAlquiler.obtenerTodosLosAlquileresPorJuego(juego1);
        assertEquals(2, resultado1.size());
        assertEquals(alquileres, resultado1);

        when(daoAlquiler.selectAllAlquileresByJuego(juego2)).thenReturn(alquileres2);
        List<Alquiler> resultado2 = servicioAlquiler.obtenerTodosLosAlquileresPorJuego(juego2);
        assertEquals(2, resultado2.size());
        assertEquals(alquileres2, resultado2);
    }
    
    @Test
    void testObtenerTodosLosAlquileresPorFechaInicio() {
    	
	    LocalDate fechaInicio1 = LocalDate.of(2023, 5, 15);
	    LocalDate fechaFin1 = LocalDate.of(2023, 5, 18);
	    Juego juego1 = new Juego(UUID.randomUUID(), "Juego1", 10.99, 5);
	    Cliente cliente1 = new Cliente("12345678A", "Cliente1", "test1@gmail.com", "Calle1, 1");
	
	    LocalDate fechaInicio2 = LocalDate.of(2023, 5, 20);
	    LocalDate fechaFin2 = LocalDate.of(2023, 5, 23);
	    Juego juego2 = new Juego(UUID.randomUUID(), "Juego2", 15.99, 3);
	    Cliente cliente2 = new Cliente("23456789B", "Cliente2", "test2@gmail.com", "Calle1, 2");
	
	    List<Alquiler> alquileres1 = new ArrayList<>();
	    alquileres1.add(new Alquiler(UUID.randomUUID(), fechaInicio1, fechaFin1, juego1, cliente1));
	    when(daoAlquiler.selectAllAlquileresByFechaInicio(fechaInicio1)).thenReturn(alquileres1);
	
	    List<Alquiler> alquileres2 = new ArrayList<>();
	    alquileres2.add(new Alquiler(UUID.randomUUID(), fechaInicio2, fechaFin2, juego2, cliente2));
	    when(daoAlquiler.selectAllAlquileresByFechaInicio(fechaInicio2)).thenReturn(alquileres2);
	
	    List<Alquiler> resultado1 = servicioAlquiler.obtenerTodosLosAlquileresPorFechaInicio(fechaInicio1);
	    assertEquals(1, resultado1.size());
	    assertEquals(resultado1.get(0).getJuego(), juego1);
	    assertEquals(resultado1.get(0).getCliente(), cliente1);
	
	    List<Alquiler> resultado2 = servicioAlquiler.obtenerTodosLosAlquileresPorFechaInicio(fechaInicio2);
	    assertEquals(1, resultado2.size());
	    assertEquals(resultado2.get(0).getJuego(), juego2);
	    assertEquals(resultado2.get(0).getCliente(), cliente2);
    }
    
    @Test
    void testObtenerTodosLosAlquileresPorFechaFin() {
    	
        LocalDate fechaInicio1 = LocalDate.of(2023, 5, 15);
        LocalDate fechaFin1 = LocalDate.of(2023, 5, 18);
        Juego juego1 = new Juego(UUID.randomUUID(), "Juego1", 10.99, 5);
        Cliente cliente1 = new Cliente("12345678A", "Cliente1", "test1@gmail.com", "Calle1, 1");

        LocalDate fechaInicio2 = LocalDate.of(2023, 5, 20);
        LocalDate fechaFin2 = LocalDate.of(2023, 5, 23);
        Juego juego2 = new Juego(UUID.randomUUID(), "Juego2", 15.99, 3);
        Cliente cliente2 = new Cliente("23456789B", "Cliente2", "test2@gmail.com", "Calle2, 2");

        List<Alquiler> alquileres = new ArrayList<>();
        alquileres.add(new Alquiler(UUID.randomUUID(), fechaInicio1, fechaFin1, juego1, cliente1));
        alquileres.add(new Alquiler(UUID.randomUUID(), fechaInicio2, fechaFin2, juego2, cliente2));

        when(daoAlquiler.selectAllAlquileresByFechaFin(fechaFin1)).thenReturn(Collections.singletonList(alquileres.get(0)));

        List<Alquiler> resultado = servicioAlquiler.obtenerTodosLosAlquileresPorFechaFin(fechaFin1);

        assertEquals(1, resultado.size());
        assertEquals(alquileres.get(0), resultado.get(0));
        verify(daoAlquiler, times(1)).selectAllAlquileresByFechaFin(fechaFin1);
    }
}
