package com.lfsdesign.tiendavideojuegos.ServiceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lfsdesign.tiendavideojuegos.dao.DaoJuego;
import com.lfsdesign.tiendavideojuegos.models.Juego;
import com.lfsdesign.tiendavideojuegos.services.ServicioJuego;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ServicioJuegoTest {

    @Mock
    private DaoJuego daoJuego;

    @InjectMocks
    private ServicioJuego servicioJuego;

    private Juego juego1;
    private Juego juego2;
    private ArrayList<Juego> juegos;
 
    @BeforeEach
    public void setUp() {
        juego1 = new Juego(UUID.randomUUID(), "Juego1", 10.99, 5);
        juego2 = new Juego(UUID.randomUUID(), "Juego2", 15.99, 3);
        juegos = new ArrayList<>();
        juegos.add(juego1);
        juegos.add(juego2);
        daoJuego = mock(DaoJuego.class);
        servicioJuego = new ServicioJuego(daoJuego);
    }

    @Test
    void testAltaJuego() {
    	
        when(daoJuego.insertarJuego(any(Juego.class))).thenReturn(1);
        int result = servicioJuego.altaJuego(juego2);
        assertThat(result).isEqualTo(1);
        assertThat(juego2.getId()).isNotNull();
    }

    @Test
    void testObtenerTodosLosJuegos() {
    	
        when(daoJuego.selectAllJuegos()).thenReturn(juegos);
        List<Juego> result = servicioJuego.obtenerTodosLosJuegos();
        assertThat(result).containsExactlyInAnyOrderElementsOf(juegos);
        verify(daoJuego, times(1)).selectAllJuegos();
    }


    @Test
    void testObtenerJuegoPorId() {
    	
        UUID id = juego1.getId();
        when(daoJuego.selectJuegoById(id)).thenReturn(Optional.of(juego1));
        Optional<Juego> result = servicioJuego.obtenerJuegoPorId(id);
        assertThat(result).isPresent().contains(juego1);
    }

    @Test
    void testObtenerJuegoPorIdNoEncontrado() {
    	
        when(daoJuego.selectJuegoById(juego1.getId())).thenReturn(Optional.empty());
        Optional<Juego> result = servicioJuego.obtenerJuegoPorId(juego1.getId());
        assertNotNull(result);
        assertFalse(result.isPresent());
    }
    
    @Test
    void testBorrarJuego() {
    	
        when(daoJuego.borrarJuegoById(juego2.getId())).thenReturn(1);
        int result = servicioJuego.borrarJuego(juego2.getId());
        assertThat(result).isEqualTo(1);
    }
 
    @Test
    @MockitoSettings(strictness = Strictness.WARN)
    void testActualizarJuego() {
    	
        Juego juegoAntiguo = new Juego(juego1.getId(), "Juego antiguo", 20.99, 10);
        Juego juegoActualizado = new Juego(juego2.getId(), "Juego Actualizado", 30.99, 9);
        when(daoJuego.selectJuegoById(juego1.getId())).thenReturn(Optional.of(juegoAntiguo));
        when(daoJuego.actualizarJuegoById(juego1.getId(), juegoActualizado)).thenReturn(1);
        int resultado = servicioJuego.actualizarJuego(juego1.getId(), juegoActualizado);
        assertEquals(1, resultado);
        verify(daoJuego, times(1)).actualizarJuegoById(juego1.getId(), juegoActualizado);
    }
    
}
       
