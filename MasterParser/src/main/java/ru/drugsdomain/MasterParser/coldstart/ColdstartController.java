package ru.drugsdomain.MasterParser.coldstart;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ColdstartController {

    private final ColdStartService coldStartService;

    @PostMapping(value = "/coldstart/start", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> startColdstart() {
        try {
            coldStartService.coldstartIfNeed();
            return ResponseEntity.ok("OK;0");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
}
