package ru.drugsdomain.SlaveParser.medical_shop.parser;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.drugsdomain.SlaveParser.medical_shop.StoreDrugWrap;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * У аптеки защита от роботов
 */
@Deprecated
@Component
@Qualifier("planetaZdorovia")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanetaZdoroviaParser implements IPriceParser{

    private static final String URL = "https://planetazdorovo.ru/";

    private static final String SEARCH_FIELD = ".search .input-field .input";

    private static final String SEARCH_BUTTON = ".search .input-field .btn";

    private static final String SEARCH_ELEMENTS = ".product-card";

    private static final String PRODUCT_CARD_CONTENT = ".product-card__content";

    private static final String ATTRIBUTE_NAME = "data-card-name";

    private static final String ATTRIBUTE_PRICE = "data-card-price";


    //private static final String PRODUCT_TITLE = ".product-card__title";
    //private static final String PRODUCT_PRICE = ".product-card__price";

    private final SeleniumService seleniumService;

    public List<StoreDrugWrap> parseByName(String name) {
        seleniumService.goTo(URL);
        seleniumService.findFieldAndWrite(SEARCH_FIELD, name);
        seleniumService.findElementAndClick(SEARCH_BUTTON);

        List<StoreDrugWrap> storeDrugs = new ArrayList<>();
        List<WebElement> productCards = seleniumService.waitForUiElementsVisible(PRODUCT_CARD_CONTENT);
        for (WebElement prodCard : productCards) {
            String priceStr = prodCard.getAttribute(ATTRIBUTE_PRICE);
            BigDecimal price = BigDecimal.valueOf(Integer.valueOf(priceStr));
            String nameInStore = prodCard.getAttribute(ATTRIBUTE_NAME);

            StoreDrugWrap drugInStore = StoreDrugWrap.builder()
                    .drugName(nameInStore)
                    .price(price)
                    .originName(name)
                    .url(seleniumService.getUrl())
                    .build();

            storeDrugs.add(drugInStore);
        }
        return storeDrugs;
    }
}
