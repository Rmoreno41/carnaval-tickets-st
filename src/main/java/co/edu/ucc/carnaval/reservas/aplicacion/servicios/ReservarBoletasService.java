package co.edu.ucc.carnaval.reservas.aplicacion.servicios;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.entrada.ReservarBoletasUseCase;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.InventarioRepository;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.Notificador;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.Reloj;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.ReservaRepository;
import co.edu.ucc.carnaval.reservas.dominio.*;

public class ReservarBoletasService implements ReservarBoletasUseCase {
    private final InventarioRepository inventario;
    private final ReservaRepository reservas;
    private final Notificador notificador;
    private final Reloj reloj;

    public ReservarBoletasService(InventarioRepository inventario,
            ReservaRepository reservas,
            Notificador notificador, Reloj reloj) {
        this.inventario = inventario;
        this.reservas = reservas;
        this.notificador = notificador;
        this.reloj = reloj;
    }

    @Override
    public String reservar(Comando c) {
        Localidad localidad = inventario.buscarLocalidad(c.localidadId()).orElseThrow(() -> new ReglaDeNegocioVioladaException("No existe la localidad " + c.localidadId()));
        Reserva reserva = Reserva.crear(c.documentoComprador(), localidad, 
        c.cantidad(), reloj.ahora()); // decide el dominio
        inventario.guardar(localidad);
        reservas.guardar(reserva);
        notificador.reservaCreada(c.documentoComprador(), reserva.id(), 
        reserva.cantidad());
        return reserva.id();
    }
}
