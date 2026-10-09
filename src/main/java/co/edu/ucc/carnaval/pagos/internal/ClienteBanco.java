package co.edu.ucc.carnaval.pagos.internal;

import org.springframework.stereotype.Component;

@Component
public class ClienteBanco {           // pública en Java, pero interna para Modulith
    public String autorizar(String tarjeta, long valor) {
        return tarjeta.startsWith("4") ? "APROBADO" : "RECHAZADO";
    }
}
