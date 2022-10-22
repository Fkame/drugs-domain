package ru.drugsdomain.SlaveParser.medicaldict.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.drugsdomain.SlaveParser.DrugParamsDto;
import ru.drugsdomain.SlaveParser.aspect.logging.ToLog;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LsgeotarParser implements IMedicalDictParser {

    public static final String DICTIONARY_URL = "https://www.lsgeotar.ru/";

    public static final String WAIT_ELEM = "#RDS_BODY";

    public static final String SEARCH_FIELD_SELECTOR = "#SearchText";

    public static final String SEARCH_ELEMENTS_SELECTOR = ".title-tn-link";

    public static final String SEARCH_BUTTON_SELECTOR = ".search-button";

    public static final String INFO_ACTIVE_SUBSTANCE = ".mnn-from-tn-link";

    public static final String INFO_CREATOR = ".ls-proizvoditel .ls-procreator-child-title";

    public static final String INFO_CREATION_COUNTRY = ".ls-vladelec-reg-ud .ls-procreator-child-country";

    public static final String INFO_RECIPE = ".ls-uslovia-otpuska";

    public static final String TEMPLATE_RECIPE = "По рецепту";

    public static final String INFO_PURPOSE = ".ls-farm-group";

    public static final String INFO_RELEASE_FORM = ".ls-dosage-form";

    public static final String BLOCK_SCREEN_CLOSE_BUTTON = ".backdrop-close";

    private final SeleniumService seleniumService;

    public List<DrugParamsDto> parseByNames(List<String> names) {
        seleniumService.goTo(DICTIONARY_URL);

        List<DrugParamsDto> parsedDrugsInfo = new ArrayList<>();
        for (String name : names) {
            log.info("Started parsing drug [" + name + "]");
            parsedDrugsInfo.addAll(findAllWithName(name, seleniumService));
            log.info("Ended parsing drug [" + name + "]");
        }
        //seleniumService.quit();
        return parsedDrugsInfo;
    }

    @ToLog
    private List<DrugParamsDto> findAllWithName(String name, SeleniumService seleniumService) {
        tryCloseBlockingPanel();
        seleniumService.findFieldAndWrite(SEARCH_FIELD_SELECTOR, name);
        seleniumService.findElementAndClick(SEARCH_BUTTON_SELECTOR);

        log.info("Search started");

        List<DrugParamsDto> drugs = new ArrayList<>();

        List<WebElement> drugsWithName = seleniumService.findElements(SEARCH_ELEMENTS_SELECTOR);
        List<String> refsOnDrug = drugsWithName.stream()
                .map(elem -> elem.getAttribute("href"))
                .toList();

        log.info("Search finished, found " + drugsWithName.size() + " drugs with name [" + name + "]");
        for (String drugRef : refsOnDrug) {
            seleniumService.createNewTabOnRef(drugRef);
            log.info("New Tab created!");

            tryCloseBlockingPanel();
            seleniumService.waitForUiElementsVisible(WAIT_ELEM);

            log.info("Info found, start parsing");

            Document htmlToParse = Jsoup.parse(seleniumService.getPageSource());
            DrugParamsDto parsedDrug = parseDrugPage(htmlToParse);

            log.info("Drug parsed, adding name and url to data");
            if (parsedDrug != null) {
                parsedDrug.setDrugName(name);
                parsedDrug.setManualRef(seleniumService.getUrl());
                drugs.add(parsedDrug);
            }

            log.info("Closing tab!");
            seleniumService.closeCurrentTab();
        }
        return drugs;
    }

    @ToLog
    private void tryCloseBlockingPanel() {
        try {
            WebElement blockBtn = seleniumService.findWithoutWait(BLOCK_SCREEN_CLOSE_BUTTON);
            blockBtn.click();
            log.info("Blocking panel successfuly closed!");
        } catch (NoSuchElementException ex) {
            log.warn("No blocking panel found");
        }
    }

    @ToLog
    private DrugParamsDto parseDrugPage(Document html) {
        try {
            tryCloseBlockingPanel();
            return DrugParamsDto.builder()
                    .activeSubstance(html.select(INFO_ACTIVE_SUBSTANCE)
                            .first()
                            .text())
                    .prodCompany(html.select(INFO_CREATOR)
                            .first()
                            .text())
                    .prodCompany(html.select(INFO_CREATION_COUNTRY)
                            .first()
                            .text())
                    .needRecipe(html.select(INFO_RECIPE)
                            .first()
                            .text()
                            .equals(TEMPLATE_RECIPE))
                    .purpose(html.select(INFO_PURPOSE)
                            .first()
                            .text())
                    .releaseForm(html.select(INFO_RELEASE_FORM)
                            .first()
                            .text())
                    .build();
        } catch (NullPointerException ex) {
            log.warn("One of selectors failed in parser");
            return null;
        }
    }
}
