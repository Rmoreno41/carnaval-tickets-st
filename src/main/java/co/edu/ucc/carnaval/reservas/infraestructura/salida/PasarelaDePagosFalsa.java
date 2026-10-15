package co.edu.ucc.carnaval.reservas.infraestructura.salida;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.PasarelaDePagos;

@Component
@Profile("!pagos-remoto")
class PasarelaDePagosFalsa implements PasarelaDePagos {
    
@Override
public ResultadoPago cobrar(Cobro cobro) {
return new ResultadoPago(true, "FALSA-" + cobro.reservaId());
}
}