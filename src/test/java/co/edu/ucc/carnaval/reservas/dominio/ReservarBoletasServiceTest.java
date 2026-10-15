package co.edu.ucc.carnaval.reservas.dominio;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.entrada.ReservarBoletasUseCase.Comando;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.*;
import co.edu.ucc.carnaval.reservas.aplicacion.servicios.ReservarBoletasService;
import co.edu.ucc.carnaval.reservas.dominio.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservarBoletasServiceTest {
    // Adaptadores falsos: clases de pocas líneas, sin Mockito y sin Spring
    static class InventarioFalso implements InventarioRepository {
        final Map<String, Localidad> datos = new HashMap<>();

        public Optional<Localidad> buscarLocalidad(String id) {
            return Optional.ofNullable(datos.get(id));
        }

        public void guardar(Localidad l) {
            datos.put(l.id(), l);
        }
    }

    static class ReservasFalso implements ReservaRepository {
        final Map<String, Reserva> datos = new HashMap<>();

        public void guardar(Reserva r) {
            datos.put(r.id(), r);
        }

    public Optional<Reserva> buscar(String id) { return 
    Optional.ofNullable(datos.get(id)); }
    }

    static class NotificadorFalso implements Notificador {
        final List<String> enviados = new ArrayList<>();

        public void reservaCreada(String doc, String id, int cantidad) {
            enviados.add(doc + ":" + cantidad);
        }
    }

    final InventarioFalso inventario = new InventarioFalso();
    final ReservasFalso reservas = new ReservasFalso();
    final NotificadorFalso notificador = new NotificadorFalso();
    final Reloj relojFijo = () -> Instant.parse("2027-01-02T10:00:00Z");
    final ReservarBoletasService servicio = new ReservarBoletasService(inventario, reservas, notificador,
            relojFijo);

    @Test
    void reservaGuardaYNotifica() {
        inventario.guardar(new Localidad("palco", "Palco", 100, 0));
        String id = servicio.reservar(new Comando("1085", "palco", 2));
        assertTrue(reservas.buscar(id).isPresent());
        assertEquals(98,
                inventario.buscarLocalidad("palco").get().disponibles());
        assertEquals(List.of("1085:2"), notificador.enviados);
    }

    @Test
    void siLaReglaFallaNoSeGuardaNiSeNotifica() {
        inventario.guardar(new Localidad("palco", "Palco", 100, 0));
        assertThrows(ReglaDeNegocioVioladaException.class,
                () -> servicio.reservar(new Comando("1085", "palco", 7)));
        assertTrue(reservas.datos.isEmpty());
        assertTrue(notificador.enviados.isEmpty());
    }
}