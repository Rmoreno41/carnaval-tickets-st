package co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida;

import java.util.Optional;

import co.edu.ucc.carnaval.reservas.dominio.Reserva;

public interface ReservaRepository {
     void guardar(Reserva reserva);
    Optional<Reserva> buscar(String reservaId);
}
