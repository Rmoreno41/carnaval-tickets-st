package co.edu.ucc.carnaval.reservas.aplicacion.puertos.salida;

import java.util.Optional;

import co.edu.ucc.carnaval.reservas.dominio.Localidad;

public interface InventarioRepository {
     Optional<Localidad> buscarLocalidad(String localidadId);
    void guardar(Localidad localidad);
}
