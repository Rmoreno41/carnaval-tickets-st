package co.edu.ucc.carnaval.reservas.infraestructura.salida.pagos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.sun.net.httpserver.HttpServer;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.PasarelaDePagos.Cobro;

/** Prueba el adaptador HTTP contra un servidor falso del JDK: sin Docker y sin el servicio real. */
class PasarelaDePagosHttpTest {

    HttpServer servidor;
    final AtomicReference<String> llaveRecibida = new AtomicReference<>();

    void arrancarServidor(long demoraMs) throws Exception {
        servidor = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        servidor.createContext("/cobros", intercambio -> {
            llaveRecibida.set(intercambio.getRequestHeaders().getFirst("Idempotency-Key"));
            try { Thread.sleep(demoraMs); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            byte[] cuerpo = "{\"aprobado\":true,\"referencia\":\"REF-123\"}".getBytes(StandardCharsets.UTF_8);
            intercambio.getResponseHeaders().add("Content-Type", "application/json");
            intercambio.sendResponseHeaders(200, cuerpo.length);
            intercambio.getResponseBody().write(cuerpo);
            intercambio.close();
        });
        servidor.start();
    }

    PasarelaDePagosHttp adaptador(int timeoutMs) {
        var fabrica = new SimpleClientHttpRequestFactory();
        fabrica.setReadTimeout(Duration.ofMillis(timeoutMs));
        var http = RestClient.builder()
                .baseUrl("http://localhost:" + servidor.getAddress().getPort())
                .requestFactory(fabrica).build();
        return new PasarelaDePagosHttp(http);
    }

    @AfterEach
    void apagar() {
        if (servidor != null) servidor.stop(0);
    }

    @Test
    void cobraYEnviaLaReservaComoLlaveDeIdempotencia() throws Exception {
        arrancarServidor(0);

        var resultado = adaptador(2000).cobrar(new Cobro("reserva-42", "1085", 240000));

        assertTrue(resultado.aprobado());
        assertEquals("REF-123", resultado.referencia());
        assertEquals("reserva-42", llaveRecibida.get());
    }

    @Test
    void siPagosTardaMasQueElTimeoutFallaConResourceAccessException() throws Exception {
        arrancarServidor(1500);

        assertThrows(ResourceAccessException.class,
                () -> adaptador(300).cobrar(new Cobro("reserva-42", "1085", 240000)));
    }
}
