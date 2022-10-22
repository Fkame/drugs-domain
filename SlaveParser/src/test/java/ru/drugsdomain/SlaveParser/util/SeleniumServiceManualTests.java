package ru.drugsdomain.SlaveParser.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SeleniumServiceManualTests {

    public static final String SELENIUM_URL= "https://www.selenium.dev/selenium/web/web-form.html";

    @Autowired
    private SeleniumService seleniumService;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void finish() {
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

    @Test
    @SneakyThrows
    public void testFindFieldAndWrite() {
        String cssSelector = "#my-text-id";
        String textToWrite = "12345";
        seleniumService.goTo(SELENIUM_URL);
        seleniumService.findFieldAndWrite(cssSelector, textToWrite);
        Thread.sleep(1000);
    }

    @Test
    @SneakyThrows
    public void testGetPageSource() {
        seleniumService.goTo(SELENIUM_URL);
        String html = seleniumService.getPageSource();
        assertThat(html.contains("html")).isTrue();
        Thread.sleep(1000);
    }
}
