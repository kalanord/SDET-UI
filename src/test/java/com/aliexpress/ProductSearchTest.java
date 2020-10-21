package com.aliexpress;

import com.UIBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class ProductSearchTest extends UIBaseTest {

    @Test
    public void TestProductSearch() {
        driver.get("http://aliexpress.com");
        //TODO: Check for overlays
        driver.findElement(By.id("search-key")).sendKeys("iphone\n");
        //Need to scroll down first for whole page to be loaded
        scrollUntilFullPageIsLoaded();
        WebElement element = driver.findElement(By.className("next-pagination-list"))
                            .findElement(By.xpath("button[contains(text(), '2')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        element.click();
        driver.findElements(By.cssSelector(".list-item")).get(1).click();
    }

}
