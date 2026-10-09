package co.edu.ucc.carnaval.inventario.internal;

import co.edu.ucc.carnaval.inventario.InventarioApi;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
class InventarioJdbc implements InventarioApi {
    private final JdbcTemplate jdbc;

    InventarioJdbc(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Override
    public LocalidadInfo consultar(String id) {
        return jdbc.queryForObject(
            "SELECT id, nombre, capacidad - vendidas AS disponibles, precio FROM localidad WHERE id = ?",
            (rs, n) -> new LocalidadInfo(rs.getString("id"), rs.getString("nombre"),
                                         rs.getInt("disponibles"), rs.getLong("precio")),
            id);
    }

    @Override
    public void apartar(String id, int cantidad) {
        int filas = jdbc.update(
            "UPDATE localidad SET vendidas = vendidas + ? WHERE id = ? AND capacidad - vendidas >= ?",
            cantidad, id, cantidad);
        if (filas == 0) throw new IllegalStateException("No hay sillas suficientes en " + id);
    }
}