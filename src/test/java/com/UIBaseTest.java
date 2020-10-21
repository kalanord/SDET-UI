package com;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class UIBaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void scrollUntilFullPageIsLoaded() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object documentHeight = js.executeScript("return document.body.scrollHeight");

        long y = 500;
        Duration timeout = Duration.ofSeconds(20);

        while (y <= (Long) documentHeight) {
            ExpectedCondition<Boolean> pageLoadCondition =
                    driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").
                            equals("complete");
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            js.executeScript("window.scrollTo(0, " + y + ");");
            wait.until(pageLoadCondition);
            y += 500;
        }
    }
}
