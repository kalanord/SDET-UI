package com;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

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

    @AfterSuite
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
        updateAndReportStatus("Done! Screenshots available in test-output/screenshots");
    }

    protected static void updateAndReportStatus(String newStatus) {
        Reporter.log(newStatus);
        System.out.println(newStatus);
    }
}
