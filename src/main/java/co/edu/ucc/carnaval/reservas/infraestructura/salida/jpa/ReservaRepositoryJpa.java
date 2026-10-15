package co.edu.ucc.carnaval.reservas.infraestructura.salida.jpa;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida.ReservaRepository;
import co.edu.ucc.carnaval.reservas.dominio.Reserva;

@Component
@Profile("postgres")
public class ReservaRepositoryJpa implements ReservaRepository {
    private final ReservaJpaRepo repo;
    ReservaRepositoryJpa(ReservaJpaRepo repo) { this.repo = repo; }

    @Override
    public void guardar(Reserva r) {
        var f = new ReservaJpa();
        f.id = r.id();
        f.documento = r.documentoComprador();
        f.localidadId = r.localidadId();
        f.cantidad = r.cantidad();
        f.creadaEn = r.creadaEn();
        f.estado = r.estado();
        repo.save(f);
    }

    

    @Override
    public Optional<Reserva> buscar(String reservaId) {
        return repo.findById(reservaId).map(f -> Reserva.reconstruir(f.id, f.documento, f.localidadId, f.cantidad,
                f.creadaEn, f.estado));
    }

}
