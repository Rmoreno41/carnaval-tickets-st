package co.edu.ucc.carnaval;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModulosTest {

    ApplicationModules modulos = ApplicationModules.of(CarnavalApplication.class);

    @Test
    void lasFronterasSeRespetan() {
        modulos.forEach(System.out::println); // imprime cada módulo y sus dependencias
        modulos.verify();
    }

    @Test
    void generarDocumentacion() {
        new Documenter(modulos).writeDocumentation();
    }
}
