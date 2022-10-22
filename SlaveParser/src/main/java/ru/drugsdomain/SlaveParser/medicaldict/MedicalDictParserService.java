package ru.drugsdomain.SlaveParser.medicaldict;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drugsdomain.SlaveParser.DrugParamsDto;
import ru.drugsdomain.SlaveParser.medicaldict.parser.LsgeotarParser;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MedicalDictParserService {

    private final LsgeotarParser lsgeotarParser;

    public List<DrugParamsDto> parseByNames(List<String> names) {
        return lsgeotarParser.parseByNames(names);
    }
}
