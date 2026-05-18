package org.cc.fitnesse.fixture.web;

import fit.Fixture;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

public class WebFixture extends Fixture {

    WebDriver driver;


    // -----------------------------------------------------------------------------------------------------------------
    // Browser
    // -----------------------------------------------------------------------------------------------------------------
    public void startBrowser(String browser) {
        if ("edge".equals(browser)) {
            driver = new EdgeDriver();
        }
        if ("firefox".equalsIgnoreCase(browser)) {
            driver = new FirefoxDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.manage().window().maximize();
    }

    public boolean open(String aHref) {
        // Aus Fitnesse wird ein Element "<a href...>linktext</a> geliefert, obwohl im Wiki nur der URL-Text eingegeben
        // wurde.
        String url;
        var hrefPattern = Pattern.compile("<a[\\s\\S]*?href=\"([^\"]+)\"[\\s\\S]*?>");
        var hrefMatcher = hrefPattern.matcher(aHref);
        if (!hrefMatcher.find()) {
            return false;
        }

        url = hrefMatcher.group(1);
        driver.get(url);

        return true;
    }

    public void stopBrowser() {
        driver.quit();
    }


    // -----------------------------------------------------------------------------------------------------------------
    // Interaktionen
    // -----------------------------------------------------------------------------------------------------------------
    public boolean gibInEin(String input, String selector) {
        var element = getElement(selector);
        element.clear();
        element.sendKeys(input);
        element.sendKeys(Keys.TAB);

        return waitForCallback();
    }

    public boolean klick(String selector) {

        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(getElement(selector))).click();

        return waitForCallback();
    }

    public boolean waehleAusAus(String selectSelectorId, String selectItemId) {
        var element = getElement(selectSelectorId);

        return waitForCallback();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Prüfungen
    // -----------------------------------------------------------------------------------------------------------------
    public boolean pruefeObInSteht(String selector, String text) {
        var element = getElement(selector);

        return element.getText().contains(text);
    }

    public boolean pruefeObAufSeiteSteht(String text) {
        return driver.getPageSource().contains(text);
    }

    WebElement getElement(String selector) {
        System.out.println("Finden des Elements " + selector);
        try {
            return driver.findElement(By.id(selector));
        } catch (Exception exc) {
            System.out.println("Kein Element gefunden mit id " + selector);
        }

        try {
            return driver.findElement(By.className(selector));
        } catch (Exception exc) {
            System.out.println("Kein Element gefunden mit className " + selector);
        }

        try {
            return driver.findElement(By.linkText(selector));
        } catch (Exception exc) {
            System.out.println("Kein Element gefunden mit linkText " + selector);
        }

        try {
            return driver.findElement(By.xpath("//*[text()='" + selector + "']"));
        } catch (Exception exc) {
            System.out.println("Kein Element gefunden mit text " + selector);
        }


        return null;
    }

    public boolean warteSekunden(Long seconds) {
        try {
            Thread.currentThread().wait(seconds * 1000);
        } catch (InterruptedException e) {
        }

        return true;
    }

    public boolean waitForCallback() {
        return true;
    }
}
