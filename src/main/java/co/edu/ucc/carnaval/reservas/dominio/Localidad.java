package co.edu.ucc.carnaval.reservas.dominio;

public class Localidad {
    private final String id;
    private final String nombre;
    private final int capacidad;
    private int vendidas;

    public Localidad(String id, String nombre, int capacidad, int vendidas) {
        if (capacidad <= 0) throw new IllegalArgumentException("La capacidad debe ser positiva");
        this.id = id; this.nombre = nombre; this.capacidad = capacidad; this.vendidas = vendidas;
    }

    public int disponibles() { return capacidad - vendidas; }

    
    /** Regla: no se puede vender más de lo que cabe. */
    public void apartar(int cantidad) {
        if (cantidad > disponibles()) {
            throw new ReglaDeNegocioVioladaException(
                "Solo quedan " + disponibles() + " sillas en " + nombre);
        }
        vendidas += cantidad;
    }

    public String id() { return id; }
    public String nombre() { return nombre; }
    public int capacidad() { return capacidad; }
    public int vendidas() { return vendidas; }
}