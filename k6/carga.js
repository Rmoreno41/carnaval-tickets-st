// Carga contra el servicio de Pagos directamente (no contra reservas: las 100 sillas del palco se acaban).
import http from 'k6/http';
import { check } from 'k6';

export const options = { vus: 20, iterations: 1000 };

export default function () {
  const llave = `r-${__VU}-${__ITER}`;
  const cuerpo = JSON.stringify({ reservaId: llave, documento: '1085', valorEnPesos: 120000 });
  const r = http.post('http://localhost:8081/cobros', cuerpo, {
    headers: { 'Content-Type': 'application/json', 'Idempotency-Key': llave },
  });
  check(r, { 'respondió 200': (res) => res.status === 200 });
}
