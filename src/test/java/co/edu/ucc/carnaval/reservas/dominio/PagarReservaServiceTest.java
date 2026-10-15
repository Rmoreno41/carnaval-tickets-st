package co.edu.ucc.carnaval.reservas.dominio;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.PasarelaDePagos;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.ReservaRepository;
import co.edu.ucc.carnaval.reservas.aplicacion.servicios.PagarReservaService;
import static org.junit.jupiter.api.Assertions.*;

class PagarReservaServiceTest {
    static final Instant AHORA = Instant.parse("2027-01-02T10:00:00Z");
    final Map<String, Reserva> datos = new HashMap<>();
    final ReservaRepository reservas = new ReservaRepository() {
        public void guardar(Reserva r) {
            datos.put(r.id(), r);
        }

        public Optional<Reserva> buscar(String id) {
            return Optional.ofNullable(datos.get(id));
        }
    };

    String reservaNueva() {
        var r = Reserva.crear("1085", new Localidad("p1", "Palco", 100, 0),
                2, AHORA);
        reservas.guardar(r);
        return r.id();
    }

    @Test
    void siLaPasarelaApruebaLaReservaQuedaPagada() {
        String id = reservaNueva();
        PasarelaDePagos aprueba = cobro -> new PasarelaDePagos.ResultadoPago(true, "REF-1");
        var servicio = new PagarReservaService(reservas, aprueba, () -> AHORA.plusSeconds(60));
        assertTrue(servicio.pagar(id, 240000));
        assertEquals(EstadoReserva.PAGADA, datos.get(id).estado());
    }

    @Test
    void siLaPasarelaRechazaSiguePendiente() {
        String id = reservaNueva();
        PasarelaDePagos rechaza = cobro -> new PasarelaDePagos.ResultadoPago(false, null);
        var servicio = new PagarReservaService(reservas, rechaza, () -> AHORA.plusSeconds(60));
        assertFalse(servicio.pagar(id, 240000));
        assertEquals(EstadoReserva.PENDIENTE_PAGO, datos.get(id).estado());
    }
}
