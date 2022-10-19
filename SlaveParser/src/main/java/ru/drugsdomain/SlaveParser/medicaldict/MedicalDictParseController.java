package ru.drugsdomain.SlaveParser.medicaldict;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.drugsdomain.SlaveParser.DrugParamsDto;

import java.util.List;

@RestController
public class MedicalDictParseController {

    @PostMapping(value = "/drugs-from-dict",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DrugParamsDto>> getDrugsFromDictByNames(
            @RequestBody List<String> names) {
        MedicalDictParserService parserService = new MedicalDictParserService();
        List<DrugParamsDto> dtos = parserService.parseByNames(names);

        if (dtos == null) {
            return ResponseEntity.internalServerError().build();
        } else if (dtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(dtos);
        }
    }
}
