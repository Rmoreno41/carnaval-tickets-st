package co.edu.ucc.carnaval.reservas.infraestructura.salida.jpa;

import java.time.Instant;

import co.edu.ucc.carnaval.reservas.dominio.EstadoReserva;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva")
public class ReservaJpa {
    @Id
    String id;
    String documento;
    String localidadId;
    int cantidad;
    Instant creadaEn;
    @Enumerated(EnumType.STRING)
    EstadoReserva estado;

    protected ReservaJpa() {
    }
}
