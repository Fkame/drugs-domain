package ru.drugsdomain.SlaveParser.medical_dict.parser.vidal;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class VidalParserHtml {

    public static final String INFO_DRUG_NAME = ".breadcrumbs > span";

    public static final String TEMPLATE_SPECIAL_SYMB_TO_CUT = "®";

    public static final String INFO_ACTIVE_SUBSTANCES = ".block > ul > li > a:first-child";

    public static final String INFO_ACTIVE_SUBSTANCE_BLOCK = ".schema div .block:not(.firms):not(#atc_codes)";

    public static final String INFO_ACTIVE_SUBSTANCE_RELATIVE = "a";

    public static final String INFO_PROD_COMPANY = ".firms .owners > a";

    public static final String INFO_CREATION_COUNTRY = ".firms .owners > span";

    public static final String INFO_RECIPE = "#pharm > div";

    public static final String TEMPLATE_RECIPE = "по рецепту";

    public static final String[] TEMPLATE_ACTIVE_SUBSTANCES = {"Активное вещество", "Активные вещества"};

    public static final String INFO_PURPOSE = "#phthgroup > span > a";

    public static final String INFO_RELEASE_FORM = "#composition > .composition > p";

    public String parseDrugName(Document html) {
        Element drugName = html.select(INFO_DRUG_NAME).first();
        if (drugName == null) return null;

        String drugNameStr = drugName.text();

        if (drugNameStr.contains(TEMPLATE_SPECIAL_SYMB_TO_CUT)) {
            drugNameStr = drugNameStr.replace(TEMPLATE_SPECIAL_SYMB_TO_CUT, "");
        }
        return drugNameStr;
    }

    public List<String> parseActiveSubstances(Document html) {
        Element optionalActiveSubstance = html.select(INFO_ACTIVE_SUBSTANCE_BLOCK).first();

        boolean isSoloSubstance = optionalActiveSubstance.text()
                .contains(TEMPLATE_ACTIVE_SUBSTANCES[0]);

        boolean isMultipleSubstances = optionalActiveSubstance.text()
                .contains(TEMPLATE_ACTIVE_SUBSTANCES[1]);

        if (isSoloSubstance) {
            return List.of(
                    optionalActiveSubstance.select(INFO_ACTIVE_SUBSTANCE_RELATIVE)
                            .first()
                            .text());
        } else if (isMultipleSubstances) {
            return html.select(INFO_ACTIVE_SUBSTANCES).eachText();
        }
        return List.of();
    }

    public String parseProdCompany(Document html) {
        Element prodCompany = html.select(INFO_PROD_COMPANY).first();
        if (prodCompany == null) return null;
        return prodCompany.text();
    }

    /*
    Наименование имеет вид "(Россия)" или "(Индия)" - нужно убрать скобки
     */
    public String parseProdCountry(Document html) {
        Element prodCountry = html.select(INFO_CREATION_COUNTRY).first();
        if (prodCountry == null) return null;
        return prodCountry.text()
                .substring(1, prodCountry.text().length() - 1);
    }

    public List<String> parsePurposes(Document html) {
        Element purposeLine = html.select(INFO_PURPOSE).first();
        if (purposeLine == null) return null;

        String[] purposes = purposeLine.text().split("\\.");
        return Arrays.stream(purposes)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public String parseReleaseForm(Document html) {
        Element releaseForm = html.select(INFO_RELEASE_FORM).first();
        return releaseForm == null ? null : releaseForm.text();
    }

    public Boolean parseNeedRecipe(Document html) {
        Element recipeField = html.select(INFO_RECIPE).first();
        return recipeField == null ? null : recipeField.text().contains(TEMPLATE_RECIPE);
    }

    private String safeExtractFirst(Document html, String cssSelector) {
        try {
            return html.select(cssSelector)
                    .first()
                    .text();
        } catch (NullPointerException e) {
            log.warn(cssSelector + " failed");
            return null;
        }
    }
}
