package com.aliexpress.pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected static void updateAndReportStatus(String newStatus) {
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
        updateAndReportStatus("Switching to last opened tab...");
        ArrayList<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
    }

    public void attachScreenshot() throws IOException {
        Reporter.setEscapeHtml(false);
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + ".png";
        Path path = Paths.get(System.getProperty("user.dir"),"test-output", "screenshots", timeStamp);
        File screenshot = new File(path.toString());
        FileUtils.moveFile(scrFile, screenshot);
    }
}
