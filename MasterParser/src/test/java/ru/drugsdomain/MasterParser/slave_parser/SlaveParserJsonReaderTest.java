package ru.drugsdomain.MasterParser.slave_parser;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SlaveParserJsonReaderTest {

    public static final String PATH_TO_SLAVENODES_JSON = "/for_tests/slavenodes.json";

    @Test
    @SneakyThrows
    public void testJsonReader() {
        String jsonFile = this.getClass().getResource(PATH_TO_SLAVENODES_JSON).toString();

        SlaveParserJsonReader jsonReader = new SlaveParserJsonReader();
        List<SlaveParserInfo> parsers = jsonReader.readDataFromJson(jsonFile);

        assertThat(parsers).isNotNull();
        assertThat(parsers.size() > 0).isTrue();
    }
}
