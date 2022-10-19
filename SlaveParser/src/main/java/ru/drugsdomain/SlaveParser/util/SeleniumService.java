package ru.drugsdomain.SlaveParser.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SeleniumService {
    private WebDriver driver;

    private static SeleniumService seleniumService;

    private SeleniumService() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    public SeleniumService goTo(String url) {
        driver.get(url);
        return this;
    }

    public WebElement waitForUiElementClickable(String cssSelector) {
        WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
        return firstResult;
    }

    public WebElement waitForUiElementVisible(String cssSelector) {
        WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
        return firstResult;
    }

    public List<WebElement> waitForUiElementsVisible(String cssSelector) {
        List<WebElement> results = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
        return results;
    }

    public void findFieldAndWrite(String cssSelector, String toWrite) {
        WebElement inputField = waitForUiElementVisible(cssSelector);
        inputField.clear();
        inputField.sendKeys(toWrite);
    }

    public void findElementAndClick(String cssSelector) {
        WebElement inputField = waitForUiElementVisible(cssSelector);
        inputField.click();
    }

    public List<WebElement> findElements(String cssSelector) {
        return null;
    }

    public WebElement findFirstElement(String cssSelector) {
        return null;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getCurrentPageHtml() {
        return driver.getPageSource();
    }

    @Deprecated
    public void close() {
        driver.close();
    }

    public void quit() {
        driver.quit();
        driver = null;
    }

    public static SeleniumService getService() {
        if (seleniumService == null) {
            seleniumService = new SeleniumService();
        }
        return seleniumService;
    }
}
