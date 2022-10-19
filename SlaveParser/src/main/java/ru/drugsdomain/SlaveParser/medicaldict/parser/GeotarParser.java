package ru.drugsdomain.SlaveParser.medicaldict.parser;

import ru.drugsdomain.SlaveParser.DrugParamsDto;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.util.ArrayList;
import java.util.List;

public class GeotarParser implements IMedicalDictParser {

    public static final String DICTIONARY_URL = "https://www.lsgeotar.ru/";

    public List<DrugParamsDto> parseByNames(List<String> names) {
        SeleniumService seleniumService = SeleniumService.getService();
        List<DrugParamsDto> parsedDrugsInfo = new ArrayList<>();
        for (String name : names) {
            parsedDrugsInfo.addAll(findAllWithName(name, seleniumService));
        }
        seleniumService.quit();
        return parsedDrugsInfo;
    }

    private List<DrugParamsDto> findAllWithName(String name, SeleniumService seleniumService) {
        seleniumService.goTo(DICTIONARY_URL);
        return null;
    }
}
