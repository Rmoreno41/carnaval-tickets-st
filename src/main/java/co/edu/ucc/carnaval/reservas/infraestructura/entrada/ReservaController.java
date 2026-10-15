package co.edu.ucc.carnaval.reservas.infraestructura.entrada;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ucc.carnaval.reservas.aplicacion.puertos.entrada.PagarReservaUseCase;
import co.edu.ucc.carnaval.reservas.aplicacion.puertos.entrada.ReservarBoletasUseCase;
import co.edu.ucc.carnaval.reservas.dominio.ReglaDeNegocioVioladaException;

@RestController
@RequestMapping("/reservas")
class ReservaController {
    private final ReservarBoletasUseCase reservar;
    private final PagarReservaUseCase pagar;

    ReservaController(ReservarBoletasUseCase reservar, PagarReservaUseCase pagar) {
        this.reservar = reservar;
        this.pagar = pagar;
    }

    @PostMapping
    ResponseEntity<Map<String, String>> crear(@RequestBody ReservarBoletasUseCase.Comando comando) {
        String id = reservar.reservar(comando);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("reservaId", id));
    }

    record PagoRequest(long valorEnPesos) {
    }

    @PostMapping("/{id}/pago")
    Map<String, Object> pagar(@PathVariable String id, @RequestBody PagoRequest pago) {
        return Map.of("reservaId", id, "pagada", pagar.pagar(id,
                pago.valorEnPesos()));
    }

    @ExceptionHandler(ReglaDeNegocioVioladaException.class)
    ResponseEntity<Map<String, String>> 


    reglaViolada(ReglaDeNegocioVioladaException e) {
        return ResponseEntity.unprocessableEntity().body(Map.of("error",
                e.getMessage()));
    }
}
