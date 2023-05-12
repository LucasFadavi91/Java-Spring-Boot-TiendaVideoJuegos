package com.lfsdesign.tiendaVideoJuegos.ServiceTests;
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

import com.lfsdesign.tiendaVideoJuegos.dao.DaoAlquiler;
import com.lfsdesign.tiendaVideoJuegos.models.Alquiler;
import com.lfsdesign.tiendaVideoJuegos.models.Cliente;
import com.lfsdesign.tiendaVideoJuegos.models.Juego;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioAlquiler;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ServicioAlquilerTest {

    @Mock
    private DaoAlquiler daoAlquiler;

    @InjectMocks
    private ServicioAlquiler servicioAlquiler;
    
    private Alquiler alquiler1;
    private Alquiler alquiler2;
    private Cliente cliente1;
    private Cliente cliente2;
    private Juego juego1;
    private Juego juego2;
    private ArrayList<Alquiler> alquileres;

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
    	alquileres = new ArrayList<>();
    	alquileres.add(alquiler1);
    	alquileres.add(alquiler2);
    	daoAlquiler = mock(DaoAlquiler.class);
    	servicioAlquiler = new ServicioAlquiler(daoAlquiler);
    }

    @Test
    public void altaAlquilerTest() {
    	
        Juego juego = new Juego(UUID.randomUUID(), "Juego", 19.99, 10);
        Cliente cliente = new Cliente("12345678A", "Cliente", "test@test.com", "Dirección");
        Alquiler alquiler = new Alquiler(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusDays(1), juego, cliente);
        when(daoAlquiler.insertarAlquiler(alquiler)).thenReturn(1);
        int result = servicioAlquiler.altaAlquiler(alquiler);
        assertEquals(1, result);
    }

    @Test
    public void obtenerTodosLosAlquileresTest() {
       
        when(daoAlquiler.selectAllAlquileres()).thenReturn(alquileres);
        
        ArrayList<Alquiler> result = servicioAlquiler.obtenerTodosLosAlquileres();
        
        assertThat(result, hasSize(alquileres.size()));
        assertThat(result.get(0), equalTo(alquileres.get(0)));
        assertThat(result.get(1), equalTo(alquileres.get(1)));
    }

    @Test
    public void obtenerAlquilerPorIdTest() {
    	
    	String dni = "12345678A";
    	UUID alquilerId = UUID.randomUUID();
        Juego juego = new Juego(UUID.randomUUID(), "Juego", 19.99, 10);
        Cliente cliente = new Cliente(dni, "Cliente", "test@test.com", "Dirección");
        Alquiler alquiler = new Alquiler(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusDays(1), juego, cliente);
        when(daoAlquiler.selectAlquilerById(alquilerId)).thenReturn(Optional.of(alquiler));
        Optional<Alquiler> result = servicioAlquiler.obtenerAlquilerPorId(alquilerId);
        assertTrue(result.isPresent());
        assertEquals(alquiler, result.get());
    }

    @Test
    public void borrarAlquilerPorIdTest() {
    	
        UUID id = UUID.randomUUID();
        when(daoAlquiler.borrarAlquilerById(id)).thenReturn(1);
        int result = servicioAlquiler.borrarAlquilerPorId(id);
        assertEquals(1, result);
    }

    @Test
    public void testActualizarAlquiler() {
    	
        UUID id = UUID.randomUUID();
        when(daoAlquiler.actualizarAlquilerById(eq(id), any(Alquiler.class))).thenReturn(1);
        int resultado = servicioAlquiler.actualizarAlquiler(id, alquiler1);
        assertEquals(1, resultado);
    }

    @Test
    public void testObtenerTodosLosAlquileresPorCliente() {
    	
        alquileres.add(alquiler1);
        alquileres.add(alquiler2);

        when(daoAlquiler.selectAllAlquileresByCliente(any(Cliente.class))).thenReturn(alquileres);

        List<Alquiler> resultado = servicioAlquiler.obtenerTodosLosAlquileresPorCliente(cliente1);

        assertEquals(alquileres, resultado);
    }
    
    @Test
    public void testObtenerTodosLosAlquileresPorJuego() {
    	
        LocalDate fechaInicio1 = LocalDate.of(2023, 5, 15);
        LocalDate fechaFin1 = LocalDate.of(2023, 5, 18);
        Juego juego1 = new Juego(UUID.randomUUID(), "Juego1", 10.99, 5);
        Cliente cliente1 = new Cliente("12345678A", "Cliente1", "test1@gmail.com", "Calle1, 1");

        LocalDate fechaInicio2 = LocalDate.of(2023, 5, 20);
        LocalDate fechaFin2 = LocalDate.of(2023, 5, 23);
        Juego juego2 = new Juego(UUID.randomUUID(), "Juego2", 15.99, 3);
        Cliente cliente2 = new Cliente("23456789B", "Cliente2", "test2@gmail.com", "Calle2, 2");

        List<Alquiler> alquileres1 = new ArrayList<>();
        alquileres1.add(new Alquiler(UUID.randomUUID(), fechaInicio1, fechaFin1, juego1, cliente1));
        alquileres1.add(new Alquiler(UUID.randomUUID(), fechaInicio2, fechaFin2, juego1, cliente2));
        when(daoAlquiler.selectAllAlquileresByJuego(juego1)).thenReturn(alquileres1);

        List<Alquiler> resultado1 = servicioAlquiler.obtenerTodosLosAlquileresPorJuego(juego1);
        assertEquals(2, resultado1.size());
        assertEquals(alquileres1, resultado1);

        List<Alquiler> alquileres2 = new ArrayList<>();
        alquileres2.add(new Alquiler(UUID.randomUUID(), fechaInicio2, fechaFin2, juego2, cliente2));
        when(daoAlquiler.selectAllAlquileresByJuego(juego2)).thenReturn(alquileres2);

        List<Alquiler> resultado2 = servicioAlquiler.obtenerTodosLosAlquileresPorJuego(juego2);
        assertEquals(1, resultado2.size());
        assertEquals(alquileres2, resultado2);
    }
    
    @Test
    public void testObtenerTodosLosAlquileresPorFechaInicio() {
    	
	    LocalDate fechaInicio1 = LocalDate.of(2023, 5, 15);
	    LocalDate fechaFin1 = LocalDate.of(2023, 5, 18);
	    Juego juego1 = new Juego(UUID.randomUUID(), "Juego1", 10.99, 5);
	    Cliente cliente1 = new Cliente("12345678A", "Cliente1", "test1@gmail.com", "Calle1, 1");
	
	    LocalDate fechaInicio2 = LocalDate.of(2023, 5, 20);
	    LocalDate fechaFin2 = LocalDate.of(2023, 5, 23);
	    Juego juego2 = new Juego(UUID.randomUUID(), "Juego 2", 15.99, 3);
	    Cliente cliente2 = new Cliente("23456789B", "Cliente 2", "test2@gmail.com", "Calle1, 2");
	
	    List<Alquiler> alquileres1 = new ArrayList<>();
	    alquileres1.add(new Alquiler(UUID.randomUUID(), fechaInicio1, fechaFin1, juego1, cliente1));
	    when(daoAlquiler.selectAllAlquileresByFechaInicio(fechaInicio1)).thenReturn(alquileres1);
	
	    List<Alquiler> alquileres2 = new ArrayList<>();
	    alquileres2.add(new Alquiler(UUID.randomUUID(), fechaInicio2, fechaFin2, juego2, cliente2));
	    when(daoAlquiler.selectAllAlquileresByFechaInicio(fechaInicio2)).thenReturn(alquileres2);
	
	    List<Alquiler> resultado1 = servicioAlquiler.obtenerTodosLosAlquileresPorFechaInicio(fechaInicio1);
	    assertEquals(resultado1.size(), 1);
	    assertEquals(resultado1.get(0).getJuego(), juego1);
	    assertEquals(resultado1.get(0).getCliente(), cliente1);
	
	    List<Alquiler> resultado2 = servicioAlquiler.obtenerTodosLosAlquileresPorFechaInicio(fechaInicio2);
	    assertEquals(resultado2.size(), 1);
	    assertEquals(resultado2.get(0).getJuego(), juego2);
	    assertEquals(resultado2.get(0).getCliente(), cliente2);
    }
    
    @Test
    public void testObtenerTodosLosAlquileresPorFechaFin() {
    	
        LocalDate fechaInicio1 = LocalDate.of(2023, 5, 15);
        LocalDate fechaFin1 = LocalDate.of(2023, 5, 18);
        Juego juego1 = new Juego(UUID.randomUUID(), "Juego1", 10.99, 5);
        Cliente cliente1 = new Cliente("12345678A", "Cliente1", "test1@gmail.com", "Calle1, 1");

        LocalDate fechaInicio2 = LocalDate.of(2023, 5, 20);
        LocalDate fechaFin2 = LocalDate.of(2023, 5, 23);
        Juego juego2 = new Juego(UUID.randomUUID(), "Juego 2", 15.99, 3);
        Cliente cliente2 = new Cliente("23456789B", "Cliente 2", "test2@gmail.com", "Calle2, 2");

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
