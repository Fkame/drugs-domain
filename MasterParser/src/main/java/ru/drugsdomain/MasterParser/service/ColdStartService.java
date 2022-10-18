package ru.drugsdomain.MasterParser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drugsdomain.MasterParser.service.drug.DrugCommandService;
import ru.drugsdomain.MasterParser.service.drug.DrugQueryService;
import ru.drugsdomain.MasterParser.coldstart.DrugInfo;
import ru.drugsdomain.MasterParser.coldstart.JsonDrugsInfoReader;

import java.net.URL;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ColdStartService {

    private final DrugQueryService drugQueryService;
    private final DrugCommandService drugCommandService;

    public static final String RELATIVE_JSON_FILE_PATH = "/coldstart/dataset.json";

    public void prepareDataForColdStart() {
        URL dataset = ColdStartService.class.getResource(RELATIVE_JSON_FILE_PATH);
        if (dataset == null) {
            log.error("Coldstart dataset [" + RELATIVE_JSON_FILE_PATH + "] cannot be read");
            return;
        }

        JsonDrugsInfoReader reader = new JsonDrugsInfoReader();
        List<DrugInfo> drugsInfo = reader.readDataFrom(dataset);
        if (drugsInfo == null) {
            log.error("Coldstart dataset [" + RELATIVE_JSON_FILE_PATH +
                    "] after read return null, but must be datalist");
            return;
        }

        // Считывание из справочника

        // Занесение в БД
    }
}
