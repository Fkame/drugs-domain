package ru.drugsdomain.SlaveParser.medical_dict.parser.vidal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.drugsdomain.SlaveParser.medical_dict.parser.ParsedDataWrap;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VidalParser {

    public static final String DICTIONARY_URL = "https://www.vidal.ru/";

    public static final String WAIT_ELEM = "#vidal";

    public static final String SEARCH_FIELD_SELECTOR = ".search-query";

    public static final String SEARCH_BUTTON_SELECTOR = ".search-submit";

    public static final String SEARCH_ELEMENTS_SELECTOR = ".products-table-name > a";

    private final SeleniumService seleniumService;
    private final VidalParserHtml vidalParserHtml;

    public List<ParsedDataWrap> findAllWithName(String name) {
        seleniumService.goTo(DICTIONARY_URL);
        List<String> refsOnDrug = parseLinksOfDrugsWithName(name);
        log.info("Search finished, found " + refsOnDrug.size() + " drugs with name [" + name + "]");
        return parseDrugsByLinks(refsOnDrug, name);
    }

    private List<ParsedDataWrap> parseDrugsByLinks(List<String> refsOnDrug, String name) {
        List<ParsedDataWrap> drugs = new ArrayList<>();
        for (String drugRef : refsOnDrug) {
            seleniumService.goTo(drugRef);
            seleniumService.waitForUiElementClickable(WAIT_ELEM);

            Document htmlToParse = Jsoup.parse(seleniumService.getPageSource());
            ParsedDataWrap parsedDrug = parseDrugPage(htmlToParse);

            log.info("Drug parsed, adding name and url to data");
            if (parsedDrug != null) {
                parsedDrug.setManualRef(seleniumService.getUrl());
                drugs.add(parsedDrug);
            } else {
                log.warn(name + " not found!");
            }
        }
        return drugs;
    }

    private List<String> parseLinksOfDrugsWithName(String name) {
        seleniumService.findFieldAndWrite(SEARCH_FIELD_SELECTOR, name);
        seleniumService.findElementAndClick(SEARCH_BUTTON_SELECTOR);

        Document htmlToParse = Jsoup.parse(seleniumService.getPageSource());
        Elements drugs_labels = htmlToParse.select(SEARCH_ELEMENTS_SELECTOR);

        List<String> refsOnDrug = drugs_labels.stream()
                .map(drugLabel -> drugLabel.attr("href"))
                .map(subUrl -> DICTIONARY_URL + subUrl)
                .toList();

        return refsOnDrug;
    }

    private ParsedDataWrap parseDrugPage(Document html) {

        String drugName = vidalParserHtml.parseDrugName(html);
        List<String> activeSubstances = vidalParserHtml.parseActiveSubstances(html);
        String prodCompany = vidalParserHtml.parseProdCompany(html);
        String prodCountry = vidalParserHtml.parseProdCountry(html);
        List<String> purposes = vidalParserHtml.parsePurposes(html);
        String releaseForm = vidalParserHtml.parseReleaseForm(html);
        Boolean needRecipe = vidalParserHtml.parseNeedRecipe(html);

        return ParsedDataWrap.builder()
                .drugName(drugName)
                .activeSubstances(activeSubstances)
                .prodCompany(prodCompany)
                .prodCountry(prodCountry)
                .purposes(purposes)
                .releaseForm(releaseForm)
                .needRecipe(needRecipe)
                .build();
    }
}
