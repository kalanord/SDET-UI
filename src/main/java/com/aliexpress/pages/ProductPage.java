package com.aliexpress.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean areProductionActionButtonsPresent() {
        String failMessage = "Purchase buttons are not present";
        try {
            if (driver.findElement(By.className("product-action")).isDisplayed()) {
                updateAndReportStatus("Purchase buttons are present");
                return true;
            } else {
                updateAndReportStatus(failMessage);
            }
        } catch (NoSuchElementException e) {
            updateAndReportStatus(failMessage);
        }
        return false;
    }
}
