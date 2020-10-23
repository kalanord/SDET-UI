package com.aliexpress.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultsPage extends BasePage {

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void closeOverlaysIfAny(){
        try {
            updateAndReportStatus("Looking for overlays...");
            driver.findElement(By.className("next-dialog-close")).click();
            updateAndReportStatus("Overlay closed.");
        } catch (NoSuchElementException e) {
            updateAndReportStatus("No overlay was found.");
        }
    }

    public void goToPageNumber(Integer pageNumber) {
        updateAndReportStatus("Clicking on page number " + pageNumber +"...");
        WebElement element = driver.findElement(By.className("next-pagination-list"))
                            .findElement(By.xpath("button[contains(text(), '" + pageNumber.toString() + "')]"));
        moveToElement(element);
        element.click();
        waitForLoadingOverlayToGoAway();
    }

    public int getItemListSize(){
        updateAndReportStatus("Getting list of items...");
        return driver.findElements(By.cssSelector(".list-item")).size();
    }

    public void clickOnItemNumber(int itemNumber) {
        try {
            updateAndReportStatus("Clicking on item " + itemNumber + "...");
            driver.findElements(By.cssSelector(".list-item")).get(itemNumber).click();
        } catch (IndexOutOfBoundsException e) {
            String message = "The selected item number does not exist. Number " + itemNumber;
            updateAndReportStatus(message);
        }
    }

    private void waitForLoadingOverlayToGoAway() {
        By overlaySelector = By.cssSelector(".next-overlay-wrapper.opened");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(overlaySelector));
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(overlaySelector)));
    }
}
