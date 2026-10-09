package co.edu.ucc.carnaval.pedidos.internal;

import co.edu.ucc.carnaval.inventario.InventarioApi;
import co.edu.ucc.carnaval.notificaciones.NotificacionesApi;
import co.edu.ucc.carnaval.pagos.PagosApi;
import co.edu.ucc.carnaval.pagos.internal.ClienteBanco;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
class PedidoService {
    private final InventarioApi inventario;
    private final ClienteBanco banco;
    //private final PagosApi pagos;
    private final NotificacionesApi notificaciones;
    private final JdbcTemplate jdbc;

    PedidoService(InventarioApi inventario, ClienteBanco banco, NotificacionesApi notificaciones, JdbcTemplate jdbc) {
        this.inventario = inventario; this.banco = banco;
        this.notificaciones = notificaciones; this.jdbc = jdbc;
    }

    @Transactional
    public String comprar(String documento, String localidadId, int cantidad, boolean estudiante, String tarjeta) {
        if (cantidad < 1 || cantidad > 6) throw new IllegalArgumentException("maximo 6 boletas");
        var localidad = inventario.consultar(localidadId);
        long total = localidad.precio() * cantidad;
        if (estudiante) total = total * 80 / 100;
        if (cantidad >= 4) total = total - 10000;
        //if (!pagos.cobrar(tarjeta, total)) throw new IllegalStateException("pago rechazado");
        if (!"APROBADO".equals(banco.autorizar(tarjeta, total))) throw new IllegalStateException("pago rechazado");
        inventario.apartar(localidadId, cantidad);
        String id = UUID.randomUUID().toString();
        jdbc.update("INSERT INTO pedido VALUES (?,?,?,?,?,?,?)",
            id, documento, localidadId, cantidad, total, "PAGADO", Timestamp.from(Instant.now()));
        notificaciones.compraConfirmada(documento, cantidad + " boletas para " + localidad.nombre());
        return id;
    }
}
