package ru.drugsdomain.SlaveParser.medicaldict.parser.vidal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.drugsdomain.SlaveParser.medicaldict.parser.ParsedDataWrap;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VidalParserService {

    private final VidalParser vidalParser;

    public List<ParsedDataWrap> parseByNames(List<String> names) {
        List<ParsedDataWrap> parsedDrugsInfo = new ArrayList<>();
        for (String name : names) {
            log.info("Started parsing drugs by name = [" + name + "]");
            parsedDrugsInfo.addAll(vidalParser.findAllWithName(name));
            log.info("Ended parsing drugs by name = [" + name + "]");
        }
        return parsedDrugsInfo;
    }
}
