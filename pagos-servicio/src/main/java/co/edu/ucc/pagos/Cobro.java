package co.edu.ucc.pagos;

/** Contrato propio del servicio: no se comparte un .jar con el monolito, se comparte el formato del mensaje. */
public record Cobro(String reservaId, String documento, long valorEnPesos) {}
