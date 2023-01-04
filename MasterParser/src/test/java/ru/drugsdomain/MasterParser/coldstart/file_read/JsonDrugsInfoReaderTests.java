package ru.drugsdomain.MasterParser.coldstart.file_read;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JsonDrugsInfoReaderTests {

    public static final String DATASET_PATH = "/coldstart/dataset.json";

    @Test
    public void readDataFrom() {
        URL dataset = JsonDrugsInfoReaderTests.class.getResource(DATASET_PATH);
        assertThat(dataset).isNotNull();

        JsonFileDrugsNamesReader reader = new JsonFileDrugsNamesReader();
        List<String> drugsNames = reader.readDataFrom(dataset);

        assertThat(drugsNames).isNotNull();
        assertThat(drugsNames.size() > 0).isTrue();

        for (String name : drugsNames) {
            assertThat(name.contains("[\\;\\,\\.\\(\n]")).isFalse();
        }
    }
}
