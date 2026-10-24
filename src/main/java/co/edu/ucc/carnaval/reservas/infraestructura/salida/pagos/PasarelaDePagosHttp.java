package co.edu.ucc.carnaval.reservas.infraestructura.salida.pagos;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.PasarelaDePagos;

@Component
@Profile("pagos-remoto")
class PasarelaDePagosHttp implements PasarelaDePagos {
    private final RestClient http;

    PasarelaDePagosHttp(RestClient clientePagos) {
        this.http = clientePagos;
    }

    @Override
    public ResultadoPago cobrar(Cobro cobro) {
        return http.post().uri("/cobros")
                .header("Idempotency-Key", cobro.reservaId()) // la llave es la reserva, no el intento
                .body(cobro)
                .retrieve()
                .body(ResultadoPago.class);
    }
}
