package co.edu.ucc.carnaval.reservas.aplicacion.puertos.entrada;

public interface PagarReservaUseCase {
    boolean pagar(String reservaId, long valorEnPesos); // true pagada
}
