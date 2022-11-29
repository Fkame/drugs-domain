package ru.drugsdomain.SlaveParser.medical_dict;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drugsdomain.SlaveParser.DrugParamsDto;
import ru.drugsdomain.SlaveParser.medical_dict.parser.ParsedDataWrap;
import ru.drugsdomain.SlaveParser.medical_dict.parser.ParsedDataWrapMapper;
import ru.drugsdomain.SlaveParser.medical_dict.parser.vidal.VidalParserService;
import ru.drugsdomain.SlaveParser.util.DrugParamsDtoMerger;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MedicalDictParserService {

    private final VidalParserService vidalParserService;

    private final ParsedDataWrapMapper parsedPageMapper;

    private final DrugParamsDtoMerger drugParamsMerger;

    public List<DrugParamsDto> parseByNames(List<String> names) {
        List<ParsedDataWrap> parsedDrugs = vidalParserService.parseByNames(names);
        return mergeParsedDrugPagesByName(parsedDrugs);
    }

    public List<DrugParamsDto> mergeParsedDrugPagesByName(List<ParsedDataWrap> dataWraps) {
        List<DrugParamsDto> drugParamsDtos = new ArrayList<>();
        for (ParsedDataWrap drugPage : dataWraps) {
            DrugParamsDto drugParams = findByName(drugPage.getDrugName(), drugParamsDtos);

            if (drugParams == null) {
                drugParamsDtos.add(parsedPageMapper.map(drugPage));
            } else {
                DrugParamsDto duplicateParams = parsedPageMapper.map(drugPage);
                DrugParamsDto mergedParams = drugParamsMerger.mergeByName(drugParams, duplicateParams);

                drugParamsDtos.remove(drugParams);
                drugParamsDtos.add(mergedParams);
            }
        }
        return drugParamsDtos;
    }

    private DrugParamsDto findByName(String name, List<DrugParamsDto> drugParamsDtos) {
        return drugParamsDtos.stream()
                .filter(dto -> dto.getDrugName().equals(name))
                .findAny()
                .orElse(null);
    }
}
