package co.edu.ucc.carnaval;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.sql.Timestamp;
import java.util.*;

@RestController 
public class PedidoController {
    @Autowired
    private JdbcTemplate jdbc;

    @PostMapping("/pedidos")
    public Map<String, Object> comprar(@RequestBody Map<String, Object> req) {
        String documento = (String) req.get("documento");
        String localidadId = (String) req.get("localidadId");
        int cant = (Integer) req.get("cantidad");
        boolean estudiante = Boolean.TRUE.equals(req.get("estudiante"));
        String tarjeta = (String) req.get("tarjeta");

        // 1. Validación y regla de negocio, mezcladas
        if (documento == null || documento.isBlank()) return Map.of("error", "documento obligatorio");
        if (cant < 1 || cant > 6) return Map.of("error", "maximo 6 boletas");

        // 2. Inventario con SQL directo
        Map<String, Object> loc = jdbc.queryForMap("SELECT * FROM localidad WHERE id = ?", localidadId);
        int capacidad = (Integer) loc.get("capacidad");
        int vendidas = (Integer) loc.get("vendidas");
        if (vendidas + cant > capacidad) return Map.of("error", "agotado");

        // 3. Precio y descuentos
        long precio = ((Number) loc.get("precio")).longValue();
        long total = precio * cant;
        if (estudiante) total = total * 80 / 100;
        if (cant >= 4) total = total - 10000;

        // 4. Cobro: se crea el cliente HTTP aquí mismo y la URL está escrita a mano
        RestTemplate rest = new RestTemplate();
        String respuesta = rest.postForObject(
            "http://localhost:8080/pasarela-falsa?tarjeta=" + tarjeta + "&valor=" + total, null, String.class);
        if (!"APROBADO".equals(respuesta)) return Map.of("error", "pago rechazado");

        // 5. Guardar
        jdbc.update("UPDATE localidad SET vendidas = vendidas + ? WHERE id = ?", cant, localidadId);
        String id = UUID.randomUUID().toString();
        jdbc.update("INSERT INTO pedido VALUES (?,?,?,?,?,?,?)",
            id, documento, localidadId, cant, total, "PAGADO", Timestamp.from(Instant.now()));

        // 6. "Correo"
        System.out.println("CORREO a " + documento + ": compraste " + cant + " boletas para "
            + loc.get("nombre") + " por $" + total);

        return Map.of("pedidoId", id, "total", total);
    }

    @GetMapping("/localidades")
    public List<Map<String, Object>> localidades() {
        return jdbc.queryForList("SELECT id, nombre, capacidad - vendidas AS disponibles, precio FROM localidad");
    }

    // La "pasarela de pagos" vive dentro de la misma aplicación
    @PostMapping("/pasarela-falsa")
    public String pasarelaFalsa(@RequestParam String tarjeta, @RequestParam long valor) {
        return tarjeta.startsWith("4") ? "APROBADO" : "RECHAZADO";
    }
}
