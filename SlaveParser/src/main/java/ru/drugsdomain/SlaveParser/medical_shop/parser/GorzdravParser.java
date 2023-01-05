package ru.drugsdomain.SlaveParser.medical_shop.parser;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.drugsdomain.SlaveParser.medical_shop.StoreDrugWrap;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Qualifier("gorzdrav")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GorzdravParser implements IPriceParser {

    private static final String URL = "https://gorzdrav.org/";

    private static final String SEARCH_FIELD = ".search__input";

    private static final String MOBILE_SEARCH_ON_BTN = ".tile-btn--search";

    private static final String SEARCH_BUTTON = ".search__btn";

    private static final String SEARCH_ELEMENTS = ".Product-item";

    private static final String PRODUCT_NAME = ".Product-item__name";

    private static final String PRODUCT_PRICE = ".Product-prices__price  .Product-prices__price--bold";

    public final SeleniumService seleniumService;

    public List<StoreDrugWrap> parseByName(String name) {
        seleniumService.goTo(URL);

        WebElement mobileBtn = seleniumService.findWithoutWait(MOBILE_SEARCH_ON_BTN);
        if (mobileBtn != null)
            mobileBtn.click();
        seleniumService.findFieldAndWrite(SEARCH_FIELD, name);

        seleniumService.findElementAndClick(SEARCH_BUTTON);

        List<StoreDrugWrap> storeDrugs = new ArrayList<>();
        List<WebElement> productCards = seleniumService.waitForUiElementsVisible(SEARCH_ELEMENTS);

        for (WebElement prodCard : productCards) {
            String nameInStore = prodCard.findElement(By.cssSelector(PRODUCT_NAME)).getText();
            String priceStr = prodCard.findElement(By.cssSelector(PRODUCT_PRICE)).getText();

            // чтобы убрать знак рубля после цены
            priceStr = priceStr.substring(0, priceStr.length() - 2);
            BigDecimal price = BigDecimal.valueOf(Integer.valueOf(priceStr));

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
