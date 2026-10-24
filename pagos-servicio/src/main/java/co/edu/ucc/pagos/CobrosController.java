package co.edu.ucc.pagos;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CobrosController {
    private final Map<String, ResultadoPago> porLlave = new ConcurrentHashMap<>();

    @Value("${DEMORA_MS:0}")
    long demoraMs;

    @PostMapping("/cobros")
    ResultadoPago cobrar(@RequestHeader("Idempotency-Key") String llave,
                         @RequestBody Cobro cobro) throws InterruptedException {
        Thread.sleep(demoraMs); // para la práctica de fallos
        return porLlave.computeIfAbsent(llave, k -> {
            System.out.println("COBRANDO " + cobro.valorEnPesos() + " a " + cobro.documento()
                    + " (llave " + llave + ")");
            return new ResultadoPago(true, "REF-" + UUID.randomUUID());
        });
    }
}
