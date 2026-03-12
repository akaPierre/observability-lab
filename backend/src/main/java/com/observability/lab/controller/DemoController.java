package com.observability.lab.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
public class DemoController {
    
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);
    private final Counter errorCounter;
    private final Counter helloCounter;
    private final Random random = new Random();

    public DemoController(MeterRegistry registry) {
        this.errorCounter = Counter.builder("app.errors.total")
                .description("Total de erros simulados")
                .register(registry);
        this.helloCounter = Counter.builder("app.hello.total")
                .description("Total de chamadas ao /hello")
                .register(registry);
    }

    // Endpoint simples
    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello() {
        helloCounter.increment();
        log.info("GET /hello - requisição recebida");
        return ResponseEntity.ok(Map.of(
            "message", "Hello from Observability Lab!",
            "status", "ok"
        ));
    }

    // Simula requisição lenta (entre 1 e 3 segundos)
    @GetMapping("/slow")
    public ResponseEntity<Map<String, String>> slow() throws InterruptedException {
        long delay = 1000 + random.nextInt(2000);
        log.warn("GET /slow - simulando latência de {}ms", delay);
        Thread.sleep(delay);
        return ResponseEntity.ok(Map.of(
            "message", "Resposta lenta entregue",
            "delay_ms", String.valueOf(delay)
        ));
    }

    // Simula erro HTTP 500
    @GetMapping("/error")
    public ResponseEntity<Map<String, String>> error() {
        errorCounter.increment();
        log.error("GET /error - erro simulado gerado");
        return ResponseEntity.internalServerError().body(Map.of(
            "message", "Erro simulado para fins de observabilidade",
            "status", "error"
        ));
    }

    // Simula carga de CPU
    @GetMapping("/cpu")
    public ResponseEntity<Map<String, String>> cpu() {
        log.info("GET /cpu - iniciando simulação de carga de CPU");
        long resultado = 0;
        for (long i = 0; i < 5_000_000L; i++) {
            resultado += (long) Math.sqrt(i);
        }
        log.info("GET /cpu - simulação concluída, resultado={}", resultado);
        return ResponseEntity.ok(Map.of(
            "message", "Simulação de CPU concluída",
            "computed", String.valueOf(resultado)
        ));
    }

    // Health check manual
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> status() {
        log.info("GET /status - verificação de saúde");
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "app", "observability-lab",
            "version", "1.0.0"
        ));
    }
}