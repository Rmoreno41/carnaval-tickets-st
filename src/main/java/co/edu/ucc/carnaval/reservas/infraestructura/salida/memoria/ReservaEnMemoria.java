package co.edu.ucc.carnaval.reservas.infraestructura.salida.memoria;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.ReservaRepository;
import co.edu.ucc.carnaval.reservas.dominio.Reserva;

@Component
@Profile("memoria")
public class ReservaEnMemoria  implements ReservaRepository  {
    private final Map<String, Reserva> datos = new ConcurrentHashMap<>();
    @Override public void guardar(Reserva r) { datos.put(r.id(), r); }
    @Override public Optional<Reserva> buscar(String id) { return 
    Optional.ofNullable(datos.get(id)); }
}

