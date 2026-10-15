package co.edu.ucc.carnaval.reservas.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class ReservaTest {
    static final Instant AHORA = Instant.parse("2027-01-02T10:00:00Z");

    @Test
    void noPermiteMasDeSeisBoletas() {
        var palco = new Localidad("p1", "Palco", 100, 0);
        var error = assertThrows(ReglaDeNegocioVioladaException.class,
                () -> Reserva.crear("1085", palco, 7, AHORA));
        assertEquals("Se pueden reservar entre 1 y 6 boletas",
                error.getMessage());
    }

    @Test
    void alReservarSeApartanLasSillas() {
        var palco = new Localidad("p1", "Palco", 100, 0);
        Reserva.crear("1085", palco, 4, AHORA);
        assertEquals(96, palco.disponibles());
    }

    @Test
    void venceALosDiezMinutos() {
        var reserva = Reserva.crear("1085", new Localidad("p1", "Palco", 100,
    0), 2, AHORA);
        assertFalse(reserva.estaVencida(AHORA.plusSeconds(9 * 60)));
        assertTrue(reserva.estaVencida(AHORA.plusSeconds(11 * 60)));
    }
    @Test
    void noSePuedePagarUnaReservaVencida() {
        var reserva = Reserva.crear("1085", new Localidad("p1", "Palco", 100, 
0), 2, AHORA);
        assertThrows(ReglaDeNegocioVioladaException.class,
        () -> reserva.confirmarPago(AHORA.plusSeconds(11 * 60)));
    }
    }


