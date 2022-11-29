package ru.drugsdomain.SlaveParser.monitoring;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitoringController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/parsers-status")
    public ResponseEntity<String[]> getParsersStatuses() {
        return null;
    }
}