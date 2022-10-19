package ru.drugsdomain.SlaveParser.medicaldict.parser;

import ru.drugsdomain.SlaveParser.DrugParamsDto;

import java.util.List;

public interface IMedicalDictParser {
    List<DrugParamsDto> parseByNames(List<String> names);
}
