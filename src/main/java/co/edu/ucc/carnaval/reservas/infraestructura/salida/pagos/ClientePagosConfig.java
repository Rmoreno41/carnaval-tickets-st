package co.edu.ucc.carnaval.reservas.infraestructura.salida.pagos;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@Profile("pagos-remoto")
class ClientePagosConfig {

    @Bean
    RestClient clientePagos(RestClient.Builder builder,
                            @Value("${pagos.url}") String url,
                            @Value("${pagos.timeout-ms:2000}") int timeoutMs) {
        var fabrica = new SimpleClientHttpRequestFactory();
        fabrica.setConnectTimeout(Duration.ofSeconds(1));
        fabrica.setReadTimeout(Duration.ofMillis(timeoutMs)); // 0 = esperar para siempre
        return builder.baseUrl(url).requestFactory(fabrica).build();
    }
}
