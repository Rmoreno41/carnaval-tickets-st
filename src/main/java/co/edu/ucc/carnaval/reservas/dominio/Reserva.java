package co.edu.ucc.carnaval.reservas.dominio;

import java.time.Instant;
import java.time.Duration;
import java.util.UUID;

public class Reserva {
    public static final int MAXIMO_POR_PERSONA = 6;
    public static final Duration VIGENCIA = Duration.ofMinutes(10);
    private final String id;
    private final String documentoComprador;
    private final String localidadId;
    private final int cantidad;
    private final Instant creadaEn;
    private EstadoReserva estado;

    private Reserva(String id, String documento, String localidadId, int cantidad, Instant creadaEn) {
        this.id = id;
        this.documentoComprador = documento;
        this.localidadId = localidadId;
        this.cantidad = cantidad;
        this.creadaEn = creadaEn;
        this.estado = EstadoReserva.PENDIENTE_PAGO;
    }

    /** Punto único de creación: aquí viven las reglas. */
    public static Reserva crear(String documento, Localidad localidad, int cantidad, Instant ahora) {
        if (cantidad < 1 || cantidad > MAXIMO_POR_PERSONA) {
            throw new ReglaDeNegocioVioladaException(
                    "Se pueden reservar entre 1 y " + MAXIMO_POR_PERSONA + " boletas");
        }
        localidad.apartar(cantidad);
        return new Reserva(UUID.randomUUID().toString(), documento,
                localidad.id(), cantidad, ahora);
    }

    public boolean estaVencida(Instant ahora) {
        return estado == EstadoReserva.PENDIENTE_PAGO &&
                ahora.isAfter(creadaEn.plus(VIGENCIA));
    }

    public void confirmarPago(Instant ahora) {
        if (estaVencida(ahora))
            throw new ReglaDeNegocioVioladaException("La reserva venció");
        if (estado != EstadoReserva.PENDIENTE_PAGO)
            throw new ReglaDeNegocioVioladaException("La reserva ya fue procesada");
        estado = EstadoReserva.PAGADA;
    }

     /** Solo para adaptadores que leen de una base de datos: no aplica 
        reglas, los datos ya se validaron al crear. */
    public static Reserva reconstruir(String id, String documento, String localidadId, int cantidad,
                                    Instant creadaEn, EstadoReserva estado) 
    {
        Reserva r = new Reserva(id, documento, localidadId, cantidad, creadaEn);
        r.estado = estado;
        return r;
    }
    public Instant creadaEn() { return creadaEn;} 

    

    public String id() {
        return id;
    }

    public int cantidad() {
        return cantidad;
    }

    public String localidadId() {
        return localidadId;
    }

    public String documentoComprador() {
        return documentoComprador;
    }

    public EstadoReserva estado() {
        return estado;
    }
}
