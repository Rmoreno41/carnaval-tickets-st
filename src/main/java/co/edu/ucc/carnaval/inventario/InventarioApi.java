package co.edu.ucc.carnaval.inventario;

public interface InventarioApi {
    record LocalidadInfo(String id, String nombre, int disponibles, long precio) {}

    LocalidadInfo consultar(String localidadId);
    void apartar(String localidadId, int cantidad); // lanza IllegalStateException si no alcanza
}
