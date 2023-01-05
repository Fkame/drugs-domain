package ru.drugsdomain.SlaveParser.medical_shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MedicalShopPriceParsingServiceTest {

    @Autowired
    SeleniumService seleniumService;

    @Autowired
    MedicalShopPriceParsingService priceParsingService;

    @Test
    public void testParsing() {
        String testStr = "Аспирин";
        List<StoreDrugWrap> result = priceParsingService.parsePrice(testStr);
        assertThat(result.size() > 0).isTrue();
    }

    @AfterEach
    public void fin() {
        seleniumService.quit();
    }
}
