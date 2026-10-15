package co.edu.ucc.carnaval.reservas.infraestructura.salida.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "localidad")
public class LocalidadJpa {
    @Id
    String id;
    String nombre;
    int capacidad;
    int vendidas;
    @Version
    Long version;


    protected LocalidadJpa() {
    } 

    LocalidadJpa(String id) {
        this.id = id;
    }
}    

