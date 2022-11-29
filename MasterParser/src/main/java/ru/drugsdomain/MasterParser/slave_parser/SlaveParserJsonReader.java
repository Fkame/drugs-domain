package ru.drugsdomain.MasterParser.slave_parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SlaveParserJsonReader {

    public List<SlaveParserInfo> readDataFromJson(String parserInfoFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        List<SlaveParserInfo> slaveParsers = new ArrayList<>();
        try {
            URL jsonFile = new URL(parserInfoFilePath);
            slaveParsers = mapper.readValue(jsonFile,
                    new TypeReference<List<SlaveParserInfo>>() {});
        } catch (Exception ex) {
            log.error("Error while parsing " + parserInfoFilePath +
                    "\nMessage: " + ex.getMessage());
        }
        return slaveParsers;
    }

}
