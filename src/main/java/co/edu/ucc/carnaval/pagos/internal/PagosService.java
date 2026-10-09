package co.edu.ucc.carnaval.pagos.internal;

import co.edu.ucc.carnaval.pagos.PagosApi;
import org.springframework.stereotype.Service;

@Service
class PagosService implements PagosApi {
   
    private final ClienteBanco banco;
    PagosService(ClienteBanco banco) { this.banco = banco; }

    @Override
    public boolean cobrar(String tarjeta, long valor) {
        return "APROBADO".equals(banco.autorizar(tarjeta, valor));
    }
}