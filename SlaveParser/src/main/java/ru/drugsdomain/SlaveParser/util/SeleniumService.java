package ru.drugsdomain.SlaveParser.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import ru.drugsdomain.SlaveParser.aspect.logging.ToLog;

import java.time.Duration;
import java.util.List;

@Component
public class SeleniumService {
    private WebDriver driver;

    private String currTab;
    private String prevTab;

    public SeleniumService() {
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

    @ToLog
    public WebElement waitForUiElementVisible(String cssSelector) {
        WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
        return firstResult;
    }

    @ToLog
    public WebElement findWithoutWait(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }

    @ToLog
    public void createNewTabOnRef(String ref) {
        prevTab = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        this.goTo(ref);
        currTab = driver.getWindowHandle();
    }

    @ToLog
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
        return waitForUiElementsVisible(cssSelector);
    }

    public WebElement findFirstElement(String cssSelector) {
        return waitForUiElementVisible(cssSelector);
    }

    public String getPageSource() {
        return this.getDriver().getPageSource();
    }

    public String getUrl() {
        return getDriver().getCurrentUrl();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getCurrentPageHtml() {
        return driver.getPageSource();
    }

    @ToLog
    public void closeCurrentTab() {
        driver.close();
        driver.switchTo().window(prevTab);
    }

    public void quit() {
        driver.quit();
        driver = null;
    }
}
