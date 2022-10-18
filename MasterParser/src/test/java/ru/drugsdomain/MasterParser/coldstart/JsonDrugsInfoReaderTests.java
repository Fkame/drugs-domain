package ru.drugsdomain.MasterParser.coldstart;

import org.junit.jupiter.api.Test;
import ru.drugsdomain.MasterParser.SpringBootProfileTest;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootProfileTest
public class JsonDrugsInfoReaderTests {

    public static final String RELATIVE_CSV_FILE_PATH = "/coldstart/dataset.json";

    @Test
    public void readDataFrom() {
        URL dataset = JsonDrugsInfoReaderTests.class.getResource(RELATIVE_CSV_FILE_PATH);
        assertThat(dataset).isNotNull();

        JsonDrugsInfoReader reader = new JsonDrugsInfoReader();
        List<DrugInfo> drugsInfo = reader.readDataFrom(dataset);

        assertThat(drugsInfo).isNotNull();
        assertThat(drugsInfo.size() > 0).isTrue();

        for (DrugInfo drugInfo : drugsInfo) {
            assertThat(drugInfo.getName().contains("[\\;\\,\\.\\(\n]")).isFalse();
        }
    }
}
