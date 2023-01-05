package ru.drugsdomain.SlaveParser.medical_dict;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.drugsdomain.SlaveParser.DrugParamsDto;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Deprecated
public class MedicalDictParseController {

    private final MedicalDictParserService medicalDictParserService;

    @PostMapping(value = "/drugs-from-dict",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    public ResponseEntity<List<DrugParamsDto>> getDrugsFromDictByNames(
            @RequestBody List<String> names) {
        List<DrugParamsDto> dtos = medicalDictParserService.parseByNames(names);

        if (dtos == null) {
            return ResponseEntity.internalServerError().build();
        } else if (dtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(dtos);
        }
    }
}
