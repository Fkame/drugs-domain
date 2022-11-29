package ru.drugsdomain.MasterParser.slave_parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.drugsdomain.MasterParser.coldstart.ColdStartInfoDto;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SlaveParserService {

    public static final String DICTIONARY_PARSE_HANDLER = "/drugs-from-dict";

    public static final String PARSERS_INFO_FILE_PATH = "/slavenodes.json";

    @Value("${parse_name_batch: 100}")
    private int PARSE_NAME_BATCH;

    private List<ParserWithTasks> slaveParsersWithTasks = null;
    private final SlaveParserJsonReader slaveParserJsonReader;
    private final SlaveParserClient slaveParserClient;

    public List<ColdStartInfoDto> parseMedicalDictionary(List<String> names) {
        readSlaveParsersIfNeed();

        // Выбор наименее загруженного парсера
        // TODO: сделать потокобезопасным
        //slaveParsersWithTasks.sort((elem1, elem2) -> Integer.compare(elem1.getTasks(), elem2.getTasks()));

        ParserWithTasks chosen = findLeastLoaded();
        SlaveParserInfo currentParser = chosen.getSlaveParserInfo();

        chosen.addTask();
        List<ColdStartInfoDto> parsedDrugs = slaveParserClient.getDrugInfoFromDictionary(
                currentParser.getHost(),
                String.valueOf(currentParser.getPort()),
                DICTIONARY_PARSE_HANDLER,
                names);
        chosen.minusTask();

        return parsedDrugs;
    }

    private ParserWithTasks findLeastLoaded() {
        int minIdx = 0;
        for (int i = 1; i < slaveParsersWithTasks.size(); i++) {
            if (slaveParsersWithTasks.get(i).getTasks() < slaveParsersWithTasks.get(minIdx).getTasks()) {
                minIdx = i;
            }
        }
        return slaveParsersWithTasks.get(minIdx);
    }

    private void readSlaveParsersIfNeed() {
        if (slaveParsersWithTasks != null) {
            return;
        }
        slaveParsersWithTasks = slaveParserJsonReader.readDataFromJson(PARSERS_INFO_FILE_PATH)
                .stream()
                .map(elem -> new ParserWithTasks(elem, 0))
                .toList();
    }

    @Data
    @AllArgsConstructor
    static class ParserWithTasks {
        private SlaveParserInfo slaveParserInfo;
        private int tasks = 0;

        public void addTask() {
            tasks += 1;
        }

        public void minusTask() {
            tasks -= 1;
            if (tasks < 0) {
                tasks = 0;
            }
        }
    }
}
