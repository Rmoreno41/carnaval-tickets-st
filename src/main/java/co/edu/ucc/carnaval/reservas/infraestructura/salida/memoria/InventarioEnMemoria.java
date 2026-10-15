package co.edu.ucc.carnaval.reservas.infraestructura.salida.memoria;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.InventarioRepository;
import co.edu.ucc.carnaval.reservas.dominio.Localidad;

@Component
@Profile("memoria")
public class InventarioEnMemoria implements InventarioRepository {

    private final Map<String, Localidad> datos = new ConcurrentHashMap<>();

    InventarioEnMemoria() {
    guardar(new Localidad("palco-6ene", "Palco Desfile Magno 6 de enero",  100, 0));
    guardar(new Localidad("tribuna-4ene", "Tribuna Carnavalito 4 de  enero", 300, 0));
    guardar(new Localidad("graderia-5ene", "Gradería Canto a la Tierra 5     de enero", 500, 0));
    }

    @Override
    public Optional<Localidad> buscarLocalidad(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public void guardar(Localidad l) {
        datos.put(l.id(), l);
    }
}
