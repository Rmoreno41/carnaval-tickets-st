package co.edu.ucc.carnaval.reservas.infraestructura.salida;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.Notificador;

@Component
@Profile("!whatsapp")
class NotificadorConsola implements Notificador {
    
@Override
public void reservaCreada(String documento, String reservaId, int cantidad) {
System.out.println("CORREO a " + documento + ": reservaste " + 
cantidad + " boletas (" + reservaId + ")");
}
}