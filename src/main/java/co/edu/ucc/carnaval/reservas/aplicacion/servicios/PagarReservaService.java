package co.edu.ucc.carnaval.reservas.aplicacion.servicios;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.entrada.PagarReservaUseCase;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.PasarelaDePagos;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.PasarelaDePagos.Cobro;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.Reloj;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.ReservaRepository;
import co.edu.ucc.carnaval.reservas.dominio.ReglaDeNegocioVioladaException;
import co.edu.ucc.carnaval.reservas.dominio.Reserva;

public class PagarReservaService implements PagarReservaUseCase {
    private final ReservaRepository reservas;
    private final PasarelaDePagos pasarela;
    private final Reloj reloj;

    public PagarReservaService(ReservaRepository reservas, PasarelaDePagos 
    pasarela, Reloj reloj) {
    this.reservas = reservas; this.pasarela = pasarela; this.reloj =  reloj;
}
@Override
 public boolean pagar(String reservaId, long valorEnPesos) {
        Reserva reserva = reservas.buscar(reservaId)
            .orElseThrow(() -> new ReglaDeNegocioVioladaException("No existe la reserva " + reservaId));
        if (reserva.estaVencida(reloj.ahora())) {
            throw new ReglaDeNegocioVioladaException("La reserva venció");
        }
        var resultado = pasarela.cobrar(new Cobro(reserva.id(), 
        reserva.documentoComprador(), valorEnPesos));
        if (!resultado.aprobado()) {
            return false; // decisión de negocio
        }
        reserva.confirmarPago(reloj.ahora());
        reservas.guardar(reserva);
        return true;
    }
}

