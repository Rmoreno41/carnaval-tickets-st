# Carnaval Tickets

Caso guía del Módulo 2 – Estilos y Patrones de Arquitectura.
Diplomado en Arquitectura de Software · Universidad Cooperativa de Colombia, sede Pasto.

Venta de boletas para los desfiles del Carnaval de Negros y Blancos. El mismo
sistema evoluciona en cada sesión, y cada etapa vive en su propia rama.

## Requisitos
- JDK 21
- Docker Desktop (en Windows, con WSL 2)
- Git
- Un IDE: IntelliJ IDEA Community o VS Code con Extension Pack for Java
- Postman o Bruno

## Cómo empezar
    git clone https://github.com/Rmoreno41/carnaval-tickets-st
    cd carnaval-tickets
    docker compose up -d
    ./mvnw test          (en Windows: .\mvnw test)
    ./mvnw spring-boot:run

La aplicación queda en http://localhost:8080.

## Ramas
Cada rama se publica después de la sesión que la trabaja.

| Rama | Etapa |
| --- | --- |
| 00-barro | El punto de partida: todo en un solo controlador |