package ru.drugsdomain.SlaveParser.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SeleniumServiceManualTests {

    public static final String SELENIUM_URL= "https://www.selenium.dev/selenium/web/web-form.html";

    private static SeleniumService seleniumService;

    @BeforeAll
    public static void setup() {
        seleniumService = SeleniumService.getService();
    }

    @AfterAll
    public static void finish() {
        seleniumService.quit();
    }

    @Test
    public void testGoTo() {
        seleniumService.goTo(SELENIUM_URL);
    }

    @Test
    @SneakyThrows
    public void testFindElementAndClick() {
        String cssSelector = ".btn";
        seleniumService.goTo(SELENIUM_URL);
        seleniumService.findElementAndClick(cssSelector);
        Thread.sleep(1000);
    }
}
