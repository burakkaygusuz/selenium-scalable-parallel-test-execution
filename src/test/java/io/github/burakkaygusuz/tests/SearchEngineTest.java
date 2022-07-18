package io.github.burakkaygusuz.tests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchEngineTest extends TestBase {

    @Test
    @DisplayName("Search on Google")
    public void searchOnGoogle() {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("selenium.dev" + Keys.ENTER);

        wait.until(ExpectedConditions.urlContains("/search?q=selenium.dev"));
        WebElement firstResult = driver.findElement(By.xpath("(//h3[@class='LC20lb MBeuO DKV0Md'])[position()=1]"));
        Assertions.assertThat(firstResult.getText()).contains("Selenium");
    }

    @Test
    @DisplayName("Search on Bing")
    public void searchOnBing() {
        driver.get("https://bing.com");
        driver.findElement(By.cssSelector("#sb_form_q")).sendKeys("selenium.dev" + Keys.ENTER);

        wait.until(ExpectedConditions.urlContains("search?q=selenium"));
        WebElement firstResult = driver.findElement(By.xpath("//h2[@class=' b_topTitle']/a"));
        Assertions.assertThat(firstResult.getText()).contains("Selenium");
    }
}
