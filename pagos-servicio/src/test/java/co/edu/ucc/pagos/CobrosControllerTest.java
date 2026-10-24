package co.edu.ucc.pagos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class CobrosControllerTest {

    @Test
    void laMismaLlaveDevuelveElMismoCobro() throws Exception {
        var controller = new CobrosController();
        var cobro = new Cobro("r-1", "1085", 240000);

        var primero = controller.cobrar("r-1", cobro);
        var reintento = controller.cobrar("r-1", cobro);

        assertEquals(primero.referencia(), reintento.referencia());
    }

    @Test
    void llavesDistintasSonCobrosDistintos() throws Exception {
        var controller = new CobrosController();

        var a = controller.cobrar("r-1", new Cobro("r-1", "1085", 240000));
        var b = controller.cobrar("r-2", new Cobro("r-2", "1085", 240000));

        assertNotEquals(a.referencia(), b.referencia());
    }
}
