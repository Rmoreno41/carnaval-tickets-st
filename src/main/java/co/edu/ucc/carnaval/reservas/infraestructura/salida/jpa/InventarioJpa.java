package co.edu.ucc.carnaval.reservas.infraestructura.salida.jpa;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.InventarioRepository;
import co.edu.ucc.carnaval.reservas.dominio.Localidad;

@Component
@Profile("postgres")
public class InventarioJpa  implements InventarioRepository {

    private final LocalidadJpaRepo repo;
    InventarioJpa(LocalidadJpaRepo repo) { this.repo = repo; }

    @Override
    public Optional<Localidad> buscarLocalidad(String localidadId) {
        return repo.findById(localidadId).map(f -> new Localidad(f.id, f.nombre, f.capacidad, f.vendidas));
}
    

    @Override
    public void guardar(Localidad l) {
        LocalidadJpa fila = repo.findById(l.id()).orElseGet(() -> new 
        LocalidadJpa(l.id()));
        fila.nombre = l.nombre();
        fila.capacidad = l.capacidad();
        fila.vendidas = l.vendidas();
        repo.save(fila);
    }
  }
    

