package co.edu.ucc.carnaval.reservas.dominio;

public class ReglaDeNegocioVioladaException extends RuntimeException {
    public ReglaDeNegocioVioladaException(String mensaje) { super(mensaje); }
}