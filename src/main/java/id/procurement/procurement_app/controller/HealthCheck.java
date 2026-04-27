package id.procurement.procurement_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthCheck {

    @GetMapping
    public ResponseEntity<Map<String, String>> heatlCheck() {
        return ResponseEntity.ok(Map.of("status", "up", "message", "Procurement Service Is Runing"));
    }

}
