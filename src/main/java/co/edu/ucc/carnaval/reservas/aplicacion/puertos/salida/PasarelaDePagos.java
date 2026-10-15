package co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida;

public interface PasarelaDePagos {
    record Cobro(String reservaId, String documento, long valorEnPesos) {}
    record ResultadoPago(boolean aprobado, String referencia) {}
    ResultadoPago cobrar(Cobro cobro);
}
