package co.edu.ucc.carnaval.notificaciones.internal;

import co.edu.ucc.carnaval.notificaciones.NotificacionesApi;
import org.springframework.stereotype.Service;

@Service
class NotificadorConsola implements NotificacionesApi {
    @Override
    public void compraConfirmada(String documento, String detalle) {
        System.out.println("CORREO a " + documento + ": " + detalle);
    }
}
