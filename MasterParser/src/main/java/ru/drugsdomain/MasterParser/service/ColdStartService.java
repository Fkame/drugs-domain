package ru.drugsdomain.MasterParser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drugsdomain.MasterParser.service.drug.DrugCommandService;
import ru.drugsdomain.MasterParser.service.drug.DrugQueryService;
import ru.drugsdomain.MasterParser.util.CsvDrugsInfo;
import ru.drugsdomain.MasterParser.util.CsvDrugsInfoReader;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ColdStartService {

    private final DrugQueryService drugQueryService;
    private final DrugCommandService drugCommandService;

    public static final String RELATIVE_CSV_FILE_PATH = "coldstart/drugsdata.csv";

    public void prepareDataForColdStart() {
        CsvDrugsInfoReader reader = new CsvDrugsInfoReader();
        CsvDrugsInfo info = reader.readDataFromCsv(RELATIVE_CSV_FILE_PATH);
    }
}
