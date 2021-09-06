package io.github.burakkaygusuz.tests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GoogleTest extends TestBase {

    @Test
    @DisplayName("Search Google")
    public void searchGoogle() {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("selenium.dev" + Keys.ENTER);

        wait.until(ExpectedConditions.urlContains("/search?q=selenium.dev"));
        WebElement firstResult = driver.findElement(By.xpath("(//h3[@class='LC20lb DKV0Md'])[position()=1]"));
        Assertions.assertThat(firstResult.getText()).isEqualTo("Selenium");
    }
}
