# Observability Lab

Ambiente de observabilidade local com Spring Boot, Prometheus e Grafana.

## Stack
- Java 17 + Spring Boot 3.5.11
- Spring Actuator + Micrometer
- Prometheus
- Grafana
- Docker Compose

## Como rodas
```bash
docker compose up --build
```

| Serviço | URL |
| Backend | http://localhost:8080 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 (admin/admin123)

### Simulação de incidentes
- `GET /slow` → latência alta
- `GET /error` → erro 500
- `Get /cpu` → carga de CPU
