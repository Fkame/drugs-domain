package ru.drugsdomain.MasterParser.core.drug;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.drugsdomain.MasterParser.core.drug.Drug;
import ru.drugsdomain.MasterParser.core.drug.DrugQueryService;
import ru.drugsdomain.MasterParser.core.drug.DrugRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DrugQueryServiceTests {

    @Autowired
    private DrugQueryService drugQueryService;

    @Autowired
    private DrugRepository drugRepository;

    @Test
    @Transactional
    public void testGetCountAll() {
        assertThat(drugQueryService.getCountAll()).isEqualTo(0);

        Drug drug1 = new Drug();
        drug1.setDrugName("drug1");
        drugRepository.save(drug1);

        Drug drug2 = new Drug();
        drug2.setDrugName("drug2");
        drugRepository.save(drug2);

        assertThat(drugQueryService.getCountAll()).isEqualTo(2);
    }
}
