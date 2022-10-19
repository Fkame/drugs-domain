package ru.drugsdomain.SlaveParser.medicaldict;

import org.springframework.stereotype.Service;
import ru.drugsdomain.SlaveParser.DrugParamsDto;
import ru.drugsdomain.SlaveParser.medicaldict.parser.GeotarParser;
import ru.drugsdomain.SlaveParser.medicaldict.parser.IMedicalDictParser;

import java.util.List;

@Service
public class MedicalDictParserService {

    public List<DrugParamsDto> parseByNames(List<String> names) {
        IMedicalDictParser parser = new GeotarParser();
        return parser.parseByNames(names);
    }
}
