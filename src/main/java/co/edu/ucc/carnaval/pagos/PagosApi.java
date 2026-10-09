package co.edu.ucc.carnaval.pagos;

public interface PagosApi {
    boolean cobrar(String tarjeta, long valor);
}
