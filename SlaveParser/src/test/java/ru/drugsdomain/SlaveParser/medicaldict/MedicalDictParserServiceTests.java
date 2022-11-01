package ru.drugsdomain.SlaveParser.medicaldict;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Parsed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.drugsdomain.SlaveParser.DrugParamsDto;
import ru.drugsdomain.SlaveParser.medicaldict.parser.ParsedDataWrap;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MedicalDictParserServiceTests {

    @Autowired
    MedicalDictParserService medicalDictParserService;


    @Autowired
    SeleniumService seleniumService;

    @AfterEach
    public void endup() {
        seleniumService.quit();
    }

    @Test
    public void testMergeParsedDrugPagesByName() {
        ParsedDataWrap dataWithNulls = ParsedDataWrap.builder()
                .drugName("1")
                .needRecipe(false)
                .releaseForm("11")
                .activeSubstances(List.of("sub22"))
                .purposes(List.of())
                .build();

        ParsedDataWrap dataWithoutNulls = ParsedDataWrap.builder()
                .drugName("1")
                .releaseForm("22")
                .purposes(List.of("purpose for 2"))
                .activeSubstances(List.of("sub1", "sub2"))
                .prodCompany("comp2")
                .prodCountry("contr2")
                .manualRef("url/1234")
                .needRecipe(false)
                .build();

        List<DrugParamsDto> result = medicalDictParserService
                .mergeParsedDrugPagesByName(List.of(dataWithNulls, dataWithoutNulls));

        assertThat(result.size() == 1).isTrue();
        DrugParamsDto resultDto = result.get(0);

        assertThat(resultDto.getActiveSubstances().size() == 3).isTrue();
        assertThat(resultDto.getPurposes().size() == 1).isTrue();
        assertThat(resultDto.getReleaseForm().size() == 2).isTrue();

        System.out.println();
    }


}
