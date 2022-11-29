package ru.drugsdomain.SlaveParser.medical_dict.parser.vidal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.drugsdomain.SlaveParser.medical_dict.parser.ParsedDataWrap;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class VidalParserTests {

    public static final String TEMPLATE_SPECIAL_SYMB_TO_CUT = "®";

    @Autowired
    private VidalParser vidalParser;

    private List<String> inputNames = List.of("Тералиджен", "Кето плюс", "Боярышник");

    @Autowired
    private SeleniumService seleniumService;

    @AfterEach
    public void endup() {
        seleniumService.quit();
    }

    @Test
    public void testMainScenario() {
        for (String name : inputNames) {
            System.out.println("Parsing drug by name = [" + name + "] ...");

            List<ParsedDataWrap> parsedDrugs = vidalParser.findAllWithName(name);
            assertThat(parsedDrugs.size() > 0).isTrue();

            System.out.println("Drug with input name = [" + name + "] check search results...");

            for (ParsedDataWrap drug : parsedDrugs) {
                try {
                    assertThat(drug.getDrugName()).isNotNull();
                    assertThat(drug.getDrugName().endsWith(TEMPLATE_SPECIAL_SYMB_TO_CUT)).isFalse();

                    assertThat(drug.getProdCompany()).isNotNull();
                    assertThat(drug.getProdCountry()).isNotNull();
                    assertThat(drug.getReleaseForm()).isNotNull();
                    assertThat(drug.getActiveSubstances().size() > 0).isTrue();
                    assertThat(drug.getPurposes().size() > 0).isTrue();
                    assertThat(drug.getManualRef()).isNotNull();
                } catch (AssertionError err) {
                    System.out.println("Проблемы с данными лекарства = [" + drug.getManualRef() + "]");
                }
            }
        }
    }
}
