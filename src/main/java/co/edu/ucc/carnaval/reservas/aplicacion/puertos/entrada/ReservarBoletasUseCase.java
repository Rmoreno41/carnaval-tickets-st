package co.edu.ucc.carnaval.reservas.aplicacion.puertos.entrada;

public interface ReservarBoletasUseCase {
    record Comando(String documentoComprador, String localidadId, int cantidad) {}
    String reservar(Comando comando); // devuelve el id de la reserva
}
