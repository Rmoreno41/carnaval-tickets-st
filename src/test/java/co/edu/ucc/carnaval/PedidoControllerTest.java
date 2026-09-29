package co.edu.ucc.carnaval;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestRestTemplate
public class PedidoControllerTest {
    
    
    @Autowired
    TestRestTemplate http;

    @Test
    void noPermiteMasDeSeisBoletas() {
        Map<?, ?> respuesta = http.postForObject("/pedidos",
            Map.of("documento", "1085", "localidadId", "palco-6ene", "cantidad", 7, "tarjeta", "4111"),
            Map.class);
        assertEquals("maximo 6 boletas", respuesta.get("error"));
    }
}
