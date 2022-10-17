package ru.drugsdomain.MasterParser.service.drug;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.drugsdomain.MasterParser.SpringBootProfileTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootProfileTest
public class DrugQueryServiceTests {

    @Autowired
    private DrugQueryService drugQueryService;

    @Autowired
    private DrugRepository drugRepository;

    @Test
    @Transactional
    public void testGetCountAll() {
        assertThat(drugQueryService.getCountAll()).isEqualTo(0);

        DrugEntity drug1 = new DrugEntity();
        drug1.setDrugName("drug1");
        drugRepository.save(drug1);

        DrugEntity drug2 = new DrugEntity();
        drug2.setDrugName("drug2");
        drugRepository.save(drug2);

        assertThat(drugQueryService.getCountAll()).isEqualTo(2);
    }
}
