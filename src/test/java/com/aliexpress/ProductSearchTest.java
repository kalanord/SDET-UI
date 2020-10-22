package com.aliexpress;

import com.UIBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;

public class ProductSearchTest extends UIBaseTest {

    @Test
    public void TestProductSearch() {
        driver.get("http://aliexpress.com");
        driver.findElement(By.id("search-key")).sendKeys("iphone\n");

        try {
            driver.findElement(By.className("next-dialog-close")).click();
        } catch (NoSuchElementException e) {
            Reporter.log("No overlay was found");
        }

        scrollUntilFullPageIsLoaded();
        WebElement element = driver.findElement(By.className("next-pagination-list"))
                            .findElement(By.xpath("button[contains(text(), '2')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
        clickOnAd();
        ArrayList<String> windowHandles =  new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
        Reporter.log("Done");
    }

    private void clickOnAd() {
        try {
            driver.findElements(By.cssSelector(".list-item")).get(1).click();
        } catch (StaleElementReferenceException e) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.className("logo-base")));
            clickOnAd();
        }
    }

}
