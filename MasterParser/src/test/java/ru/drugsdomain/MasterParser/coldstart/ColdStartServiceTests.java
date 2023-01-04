package ru.drugsdomain.MasterParser.coldstart;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.drugsdomain.MasterParser.core.drug.Drug;
import ru.drugsdomain.MasterParser.core.drug.DrugQueryService;
import ru.drugsdomain.MasterParser.slave_parser.SlaveParserService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyList;

@SpringBootTest
public class ColdStartServiceTests {

    @MockBean
    SlaveParserService slaveParserService;

    @Autowired
    ColdStartService coldStartService;

    @Autowired
    DrugQueryService drugQueryService;

    @Test
    public void testMainScenario() {

        List<ColdStartInfoDto> drugsData = List.of(
            ColdStartInfoDto.builder()
                    .drugName("Drug#1")
                    .needRecipe(true)
                    .prodCompany("FirstCompany")
                    .build(),
            ColdStartInfoDto.builder()
                    .drugName("Drug#2")
                    .activeSubstances(List.of("1", "2", "3"))
                    .build()
        );

        Mockito.when(slaveParserService.parseMedicalDictionary(anyList()))
                .thenReturn(drugsData);

        coldStartService.coldstart();

        List<Drug> dataFromDb = drugQueryService.findAll();

        assertThat(dataFromDb.size() > 0).isTrue();
    }


}
