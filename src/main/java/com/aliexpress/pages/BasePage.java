package com.aliexpress.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;
import java.util.ArrayList;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public static void updateAndReportStatus(String newStatus) {
        Reporter.log(newStatus);
        System.out.println(newStatus);
    }

    public void scrollDownUntilPageIsFullyLoaded() {
        updateAndReportStatus("Scrolling down progressively so page is fully loaded...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object documentHeight = js.executeScript("return document.body.scrollHeight");

        long y = 500;

        while (y <= (Long) documentHeight) {
            js.executeScript("window.scrollTo(0, " + y + ");");
            waitForPageToBeReadyAgain();
            y += 500;
        }
    }

    protected void moveToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    protected void waitForPageToBeReadyAgain() {
        ExpectedCondition<Boolean> pageLoadCondition =
                driver -> {
                    assert driver != null;
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").
                            equals("complete");
                };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(pageLoadCondition);
    }

    public void switchToLastOpenedTab() {
        updateAndReportStatus("Switching to control last opened tab...");
        ArrayList<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
    }
}
