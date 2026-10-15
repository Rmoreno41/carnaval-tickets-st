package co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida;

public interface Notificador {
    void reservaCreada(String documentoComprador, String reservaId, int cantidad);
}
