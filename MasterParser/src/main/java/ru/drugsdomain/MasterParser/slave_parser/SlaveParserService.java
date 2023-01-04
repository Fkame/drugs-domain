package ru.drugsdomain.MasterParser.slave_parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drugsdomain.MasterParser.coldstart.ColdStartInfoDto;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SlaveParserService {

    public static final String DICTIONARY_PARSE_HANDLER = "/drugs-from-dict";

    public static final String PARSERS_INFO_FILE_PATH = "/slavenodes.json";


    public List<ColdStartInfoDto> parseMedicalDictionary(List<String> names) {
        /// TODO добавить отправку асинхронного сообщения в кафку
        return List.of();
    }
}
