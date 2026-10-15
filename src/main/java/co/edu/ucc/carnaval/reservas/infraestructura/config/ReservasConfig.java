package co.edu.ucc.carnaval.reservas.infraestructura.config;

import java.time.Instant;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.entrada.PagarReservaUseCase;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.entrada.ReservarBoletasUseCase;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.InventarioRepository;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.Notificador;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.PasarelaDePagos;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.ReservaRepository;
import co.edu.ucc.carnaval.reservas.aplicacion.servicios.PagarReservaService;
import co.edu.ucc.carnaval.reservas.aplicacion.servicios.ReservarBoletasService;

@Configuration
class ReservasConfig {
   

    @Bean
    PagarReservaUseCase pagarReserva(ReservaRepository reservas,
            PasarelaDePagos pasarela) {
        return new PagarReservaService(reservas, pasarela, Instant::now);
    }

    @Bean
    ReservarBoletasUseCase reservarBoletas(InventarioRepository inventario,
            ReservaRepository reservas,
            Notificador notificador,
            ObjectProvider<PlatformTransactionManager> transacciones) {
        ReservarBoletasUseCase servicio = new ReservarBoletasService(inventario, reservas, notificador, Instant::now);

        PlatformTransactionManager tm = transacciones.getIfAvailable();
        if (tm == null)
            return servicio; // perfil memoria: no hay transacciones

        var tx = new TransactionTemplate(tm);
        return comando -> tx.execute(estado -> servicio.reservar(comando));
    }
}
