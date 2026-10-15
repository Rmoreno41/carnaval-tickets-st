package co.edu.ucc.carnaval.reservas.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocalidadTest {

    @Test
    void apartaSillasMientrasHayaCupo() {
        var palco = new Localidad("p1", "Palco", 10, 6);
        palco.apartar(4);
        assertEquals(0, palco.disponibles());
    }

    @Test
    void noVendeMasDeLaCapacidad() {
        var palco = new Localidad("p1", "Palco", 10, 8);
        var error = assertThrows(ReglaDeNegocioVioladaException.class, () -> palco.apartar(3));
        assertEquals("Solo quedan 2 sillas en Palco", error.getMessage());
        assertEquals(2, palco.disponibles()); // no apartó nada
    }

    @Test
    void laCapacidadDebeSerPositiva() {
        assertThrows(IllegalArgumentException.class, () -> new Localidad("p1", "Palco", 0, 0));
    }
}