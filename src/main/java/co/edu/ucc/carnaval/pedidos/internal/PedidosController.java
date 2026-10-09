package co.edu.ucc.carnaval.pedidos.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
class PedidoController {
    private final PedidoService servicio;
    PedidoController(PedidoService servicio) { this.servicio = servicio; }

    record CompraRequest(String documento, String localidadId, int cantidad, boolean estudiante, String tarjeta) {}

    @PostMapping("/pedidos")
    ResponseEntity<Map<String, String>> comprar(@RequestBody CompraRequest r) {
        String id = servicio.comprar(r.documento(), r.localidadId(), r.cantidad(), r.estudiante(), r.tarjeta());
        return ResponseEntity.status(201).body(Map.of("pedidoId", id));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    ResponseEntity<Map<String, String>> error(RuntimeException e) {
        return ResponseEntity.unprocessableEntity().body(Map.of("error", e.getMessage()));
    }
}
