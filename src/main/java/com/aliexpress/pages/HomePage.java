package com.aliexpress.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void searchFor(String key) {
        updateAndReportStatus("Searching for '" + key + "'");
        driver.findElement(By.id("search-key")).sendKeys(key + "\n");
    }
}
