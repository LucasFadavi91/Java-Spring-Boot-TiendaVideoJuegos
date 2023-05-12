package com.lfsdesign.tiendaVideoJuegos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lfsdesign.tiendaVideoJuegos.dao.DaoCliente;
import com.lfsdesign.tiendaVideoJuegos.models.Cliente;
import com.lfsdesign.tiendaVideoJuegos.services.ServicioCliente;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ServicioClienteTest {

    @Mock
    private DaoCliente daoCliente;

    @InjectMocks
    private ServicioCliente servicioCliente;

    private Cliente cliente1;
    private Cliente cliente2;
    private ArrayList<Cliente> clientes;
    
    @BeforeEach
    public void setUp() {
    	
    	String dni1 = "12345678A";
        String dni2 = "45235325B";
    	cliente1 = new Cliente(dni1, "Cliente1", "test1@test.com", "Dirección1");
        cliente2 = new Cliente(dni2, "Cliente2", "test2@test.com", "Dirección2");
        daoCliente = mock(DaoCliente.class);
        servicioCliente = new ServicioCliente(daoCliente);
    }

    @Test
    public void testAltaCliente() {
   
        when(daoCliente.insertarCliente(cliente1.getDni(), cliente1)).thenReturn(1);
        int result = servicioCliente.altaCliente(cliente1);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testObtenerTodosLosClientes() {
    	
        clientes = new ArrayList<>();
        clientes.add(cliente1);
        clientes.add(cliente2);
        when(daoCliente.selectAllClientes()).thenReturn(clientes);

        List<Cliente> result = servicioCliente.obtenerTodosLosClientes();
        assertThat(result).isEqualTo(clientes);
    }

    @Test
    public void testObtenerClientePorDni() {
    
        when(daoCliente.selectClienteByDni(cliente1.getDni())).thenReturn(Optional.of(cliente1));
        Optional<Cliente> result = servicioCliente.obtenerClientePorDni(cliente1.getDni());
        assertThat(result).isEqualTo(Optional.of(cliente1));
    }

    @Test
    public void testBorrarCliente() {
    	
        when(daoCliente.borrarClienteByDni(cliente1.getDni())).thenReturn(1);
        int result = servicioCliente.borrarCliente(cliente1.getDni());
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testActualizarCliente() {
    	
        when(daoCliente.actualizarClienteByDni(cliente2.getDni(), cliente2)).thenReturn(1);
        int result = servicioCliente.actualizarCliente(cliente2.getDni(), cliente2);
        assertThat(result).isEqualTo(1);
    }
}
