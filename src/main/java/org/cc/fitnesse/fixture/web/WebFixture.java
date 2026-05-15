package org.cc.fitnesse.fixture.web;

import java.time.Duration;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fit.Fixture;

public class WebFixture extends Fixture {
  WebDriver driver;

  public void startBrowser(String browser) {
    if ("edge".equals(browser)) {
      driver = new EdgeDriver();
    }
    if ("firefox".equalsIgnoreCase(browser)) {
      driver = new FirefoxDriver();
    }

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

  public void gibInEin(String input, String id) {
    var element = driver.findElement(By.id(id));
    element.clear();
    element.sendKeys(input);
    element.sendKeys(Keys.TAB);
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void klick(String id) {
    var element = driver.findElement(By.id(id));
    element.click();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    var wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    wait.until(ExpectedConditions.elementToBeClickable(By.id("gevo-form")));
  }

  public boolean pruefeObVorhanden(String text) {
    return driver.getPageSource().contains(text);
  }
}
