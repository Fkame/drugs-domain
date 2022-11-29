package ru.drugsdomain.MasterParser.coldstart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drugsdomain.MasterParser.drug.DrugCommandService;
import ru.drugsdomain.MasterParser.drug.DrugQueryService;
import ru.drugsdomain.MasterParser.slave_parser.SlaveParserService;

import java.net.URL;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
/**
 * Алгоритм холодного старта:
 * 1. считать из файла имена препаратов
 * 2. обратиться к сервису парсеров для загрузки общей информации о препарате из справочника
 * 3. Запись собранной информации в БД
 */
public class ColdStartService {

    public static final String DRUGS_DATASET_PATH = "/coldstart/dataset.json";

    private final DrugQueryService drugQueryService;
    private final DrugCommandService drugCommandService;
    private final IDrugsNamesReader fromJsonFileDrugsNameReader;

    private final SlaveParserService slaveParserService;

    public void coldstartIfNeed() {
        if (isColdStart()) {
            List<String> drugsNames = parseDrugsNamesFromDataset();
            List<ColdStartInfoDto> drugsParams = parseMedicalDictionary(drugsNames);
            saveDataToDb(drugsParams);
        }
    }

    private boolean isColdStart() {
        return drugQueryService.getCountAll() == 0;
    }

    private List<String> parseDrugsNamesFromDataset() {
        URL dataset = ColdStartService.class.getResource(DRUGS_DATASET_PATH);
        if (dataset == null) {
            String errorMessage = "Coldstart dataset [" + DRUGS_DATASET_PATH + "] cannot be read";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        List<String> drugsNames = fromJsonFileDrugsNameReader.readDataFrom(dataset);
        if (drugsNames == null) {
            String errorMessage = "Coldstart dataset [" + DRUGS_DATASET_PATH +
                    "] after read return null, but must be datalist";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return drugsNames;
    }

    private List<ColdStartInfoDto> parseMedicalDictionary(List<String> drugsNames) {
        return slaveParserService.parseMedicalDictionary(drugsNames);
    }

    private void saveDataToDb(List<ColdStartInfoDto> drugsInfos) {

    }
}
