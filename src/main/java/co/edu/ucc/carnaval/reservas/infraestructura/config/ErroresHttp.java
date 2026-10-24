package co.edu.ucc.carnaval.reservas.infraestructura.config;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

/** Traduce los fallos de red a HTTP. Vive en config y no en el controlador:
 *  la regla 4 de ArchUnit le prohíbe a la entrada conocer lo de salida. */
@RestControllerAdvice
class ErroresHttp {

    @ExceptionHandler(ResourceAccessException.class)
    ResponseEntity<Map<String, String>> pagosNoResponde(ResourceAccessException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "Pagos no responde. Intente de nuevo: no se cobrará dos veces."));
    }
}
