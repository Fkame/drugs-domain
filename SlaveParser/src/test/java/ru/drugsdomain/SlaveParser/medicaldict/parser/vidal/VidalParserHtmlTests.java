package ru.drugsdomain.SlaveParser.medicaldict.parser.vidal;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class VidalParserHtmlTests {

    private VidalParserHtml vidalParserHtml;

    private String teraligen1Page = "/html_for_parser/teraligen1.htm";
    private String teraligen2Page = "/html_for_parser/teraligen2.htm";
    private String ketoplus = "/html_for_parser/ketoplus.htm";

    private String boyarishnik = "/html_for_parser/boyarishnik.html";

    File teraligen1File;
    File teraligen2File;
    File ketoplusFile;

    File boyarishnikFile;

    @BeforeEach
    @SneakyThrows
    public void setup() {
        vidalParserHtml = new VidalParserHtml();

        URI teraligen1Uri = VidalParserHtmlTests.class.getResource(teraligen1Page).toURI();
        URI teraligen2Uri = VidalParserHtmlTests.class.getResource(teraligen2Page).toURI();
        URI ketoplusUri = VidalParserHtmlTests.class.getResource(ketoplus).toURI();
        URI boyarishnikUri = VidalParserHtmlTests.class.getResource(boyarishnik).toURI();

        teraligen1File = new File(teraligen1Uri.getPath());
        teraligen2File = new File(teraligen2Uri.getPath());
        ketoplusFile = new File(ketoplusUri.getPath());
        boyarishnikFile = new File(boyarishnikUri.getPath());
    }

    @Test
    @SneakyThrows
    public void testDrugName() {
        String teraligenExpected = "Тералиджен";
        String ketoplusExpected = "Кето плюс";

        Document doc = Jsoup.parse(teraligen1File, "UTF-8");
        String actualTeraligen1 = vidalParserHtml.parseDrugName(doc);
        assertThat(actualTeraligen1).isEqualTo(teraligenExpected);
        System.out.println(String.join(" | ", actualTeraligen1));

        doc = Jsoup.parse(teraligen2File, "UTF-8");
        String actualTeraligen2 = vidalParserHtml.parseDrugName(doc);
        assertThat(actualTeraligen2).isEqualTo(teraligenExpected);
        System.out.println(String.join(" | ", actualTeraligen2));

        doc = Jsoup.parse(ketoplusFile, "UTF-8");
        String actualKetoplus = vidalParserHtml.parseDrugName(doc);
        assertThat(actualKetoplus).isEqualTo(ketoplusExpected);
        System.out.println(String.join(" | ", actualKetoplus));
    }

    @Test
    @SneakyThrows
    public void testParseActiveSubstances() {
        Document doc = Jsoup.parse(teraligen1File, "UTF-8");
        List<String> actualTeraligen1 = vidalParserHtml.parseActiveSubstances(doc);
        assertThat(actualTeraligen1.size() > 0).isTrue();
        System.out.println(String.join(" | ", actualTeraligen1));

        doc = Jsoup.parse(teraligen2File, "UTF-8");
        List<String> actualTeraligen2 = vidalParserHtml.parseActiveSubstances(doc);
        assertThat(actualTeraligen2.size() > 0).isTrue();
        System.out.println(String.join(" | ", actualTeraligen2));

        doc = Jsoup.parse(ketoplusFile, "UTF-8");
        List<String> actualKetoplus = vidalParserHtml.parseActiveSubstances(doc);
        assertThat(actualKetoplus.size() > 0).isTrue();
        System.out.println(String.join(" | ", actualKetoplus));

        doc = Jsoup.parse(boyarishnikFile, "UTF-8");
        List<String> actualBoyarishnik = vidalParserHtml.parseActiveSubstances(doc);
        assertThat(actualBoyarishnik.size() > 0).isTrue();
        System.out.println(String.join(" | ", actualBoyarishnik));
    }

    @Test
    @SneakyThrows
    public void testProdCountry() {
        Document doc = Jsoup.parse(teraligen1File, "UTF-8");
        String actualTeraligen1 = vidalParserHtml.parseProdCountry(doc);
        assertThat(actualTeraligen1).isNotNull();
        System.out.println(actualTeraligen1);

        doc = Jsoup.parse(teraligen2File, "UTF-8");
        String actualTeraligen2 = vidalParserHtml.parseProdCountry(doc);
        assertThat(actualTeraligen2).isNotNull();
        System.out.println(actualTeraligen2);

        doc = Jsoup.parse(ketoplusFile, "UTF-8");
        String actualKetoplus = vidalParserHtml.parseProdCountry(doc);
        assertThat(actualKetoplus).isNotNull();
        System.out.println(actualKetoplus);
    }

    @Test
    @SneakyThrows
    public void testProdCompany() {
        Document doc = Jsoup.parse(teraligen1File, "UTF-8");
        String actualTeraligen1 = vidalParserHtml.parseProdCompany(doc);
        assertThat(actualTeraligen1).isNotNull();
        System.out.println(actualTeraligen1);

        doc = Jsoup.parse(teraligen2File, "UTF-8");
        String actualTeraligen2 = vidalParserHtml.parseProdCompany(doc);
        assertThat(actualTeraligen2).isNotNull();
        System.out.println(actualTeraligen2);

        doc = Jsoup.parse(ketoplusFile, "UTF-8");
        String actualKetoplus = vidalParserHtml.parseProdCompany(doc);
        assertThat(actualKetoplus).isNotNull();
        System.out.println(actualKetoplus);
    }

    @Test
    @SneakyThrows
    public void testReleaseForm() {
        Document doc = Jsoup.parse(teraligen1File, "UTF-8");
        String actualTeraligen1 = vidalParserHtml.parseReleaseForm(doc);
        assertThat(actualTeraligen1).isNotNull();
        System.out.println(actualTeraligen1);

        doc = Jsoup.parse(teraligen2File, "UTF-8");
        String actualTeraligen2 = vidalParserHtml.parseReleaseForm(doc);
        assertThat(actualTeraligen2).isNotNull();
        System.out.println(actualTeraligen2);

        doc = Jsoup.parse(ketoplusFile, "UTF-8");
        String actualKetoplus = vidalParserHtml.parseReleaseForm(doc);
        assertThat(actualKetoplus).isNotNull();
        System.out.println(actualKetoplus);
    }

    @Test
    @SneakyThrows
    public void testNeedRecipe() {
        Document doc = Jsoup.parse(teraligen1File, "UTF-8");
        Boolean actualTeraligen1 = vidalParserHtml.parseNeedRecipe(doc);
        assertThat(actualTeraligen1).isNull();
        System.out.println(actualTeraligen1);

        doc = Jsoup.parse(teraligen2File, "UTF-8");
        Boolean actualTeraligen2 = vidalParserHtml.parseNeedRecipe(doc);
        assertThat(actualTeraligen2).isNull();
        System.out.println(actualTeraligen2);

        doc = Jsoup.parse(ketoplusFile, "UTF-8");
        Boolean actualKetoplus = vidalParserHtml.parseNeedRecipe(doc);
        assertThat(actualKetoplus).isNotNull();
        System.out.println(actualKetoplus);
    }

    @Test
    @SneakyThrows
    public void testPurposes() {
        Document doc = Jsoup.parse(teraligen1File, "UTF-8");
        List<String> actualTeraligen1 = vidalParserHtml.parsePurposes(doc);
        assertThat(actualTeraligen1.size() > 0).isTrue();
        System.out.println(actualTeraligen1);

        doc = Jsoup.parse(teraligen2File, "UTF-8");
        List<String> actualTeraligen2 = vidalParserHtml.parsePurposes(doc);
        assertThat(actualTeraligen2.size() > 0).isTrue();
        System.out.println(actualTeraligen2);

        doc = Jsoup.parse(ketoplusFile, "UTF-8");
        List<String> actualKetoplus = vidalParserHtml.parsePurposes(doc);
        assertThat(actualKetoplus.size() > 0).isTrue();
        System.out.println(actualKetoplus);
    }
}
