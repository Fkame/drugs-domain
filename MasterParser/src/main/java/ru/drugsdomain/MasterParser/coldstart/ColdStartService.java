package ru.drugsdomain.MasterParser.coldstart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drugsdomain.MasterParser.coldstart.file_read.IDrugsNamesReader;
import ru.drugsdomain.MasterParser.core.active_substance.ActiveSubstanceCommandService;
import ru.drugsdomain.MasterParser.core.drug.Drug;
import ru.drugsdomain.MasterParser.core.drug.DrugCommandService;
import ru.drugsdomain.MasterParser.core.drug.DrugQueryService;
import ru.drugsdomain.MasterParser.core.substance.Substance;
import ru.drugsdomain.MasterParser.core.substance.SubstanceCommandService;
import ru.drugsdomain.MasterParser.slave_parser.SlaveParserService;

import java.net.URL;
import java.util.List;

/**
 * Алгоритм холодного старта:
 * 1. считать из файла имена препаратов
 * 2. обратиться к сервису парсеров для загрузки общей информации о препарате из справочника
 * 3. Запись собранной информации в БД
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ColdStartService {

    public static final String DRUGS_DATASET_PATH = "/coldstart/dataset.json";

    private final DrugQueryService drugQueryService;
    private final DrugCommandService drugCommandService;
    private final SubstanceCommandService substanceCommandService;
    private final ActiveSubstanceCommandService activeSubstanceCommandService;
    private final IDrugsNamesReader fromJsonFileDrugsNameReader;
    private final SlaveParserService slaveParserService;
    private final InfoExtractor infoExtractor;

    public void coldstartIfNeed() {
        if (isColdStart()) {
            coldstart();
        }
    }

    public void coldstart() {
        List<String> drugsNames = parseDrugsNamesFromDataset();
        List<ColdStartInfoDto> drugsParams = slaveParserService.parseMedicalDictionary(drugsNames);
        saveDataToDb(drugsParams);
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

    private void saveDataToDb(List<ColdStartInfoDto> drugsInfos) {
        for (ColdStartInfoDto dto : drugsInfos) {
            Drug drug = infoExtractor.extractDrugInfo(dto);
            List<Substance> substances = infoExtractor.extractSubstancesInfo(dto);

            drugCommandService.saveDrug(drug);
            substanceCommandService.saveSubstances(substances);
            activeSubstanceCommandService.saveActiveSubstances(drug, substances);
        }
    }
}
