package org.cc.fitnesse.fixture.web;

import fit.Fixture;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebFixture extends Fixture {
    WebDriver driver;

    public void startBrowser(String browser) {
        if ("edge".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.edge.driver", "d:\\dev\\projects\\fitest\\msedgedriver.exe");
            driver = new EdgeDriver();
            driver.manage().window().maximize();
        }
    }

    public void open() {
        driver.get("https://showcase.primefaces.org/");
        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("homepage-intro")));
    }

    public void stopBrowser() {
        driver.quit();
    }

    public void gibInEin(String id, String input) {
        var element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(input);
    }

    public void klick(String text) {
        var element = driver.findElement(By.linkText(text));
        element.click();
    }

    public boolean pruefeObVorhanden(String text) {
        return driver.getPageSource().contains(text);
    }
}
