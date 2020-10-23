package com.aliexpress;

import com.UIBaseTest;
import com.aliexpress.pages.HomePage;
import com.aliexpress.pages.ProductPage;
import com.aliexpress.pages.SearchResultsPage;
import org.testng.annotations.Test;

import static com.aliexpress.pages.BasePage.updateAndReportStatus;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSearchTest extends UIBaseTest {

    @Test(testName = "Challenge Test")
    public void TestProductSearch() {

        //For the sake of simplicity, test data was input here, but if this test could be fed with a data provider
        int numberOfItemOrder = 2;
        String searchKey = "iphone";
        String url = "http://aliexpress.com";

        updateAndReportStatus("Starting test. Going to " + url);
        driver.get(url);

        HomePage homePage = new HomePage(driver);
        homePage.searchFor(searchKey);

        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.closeOverlaysIfAny();
        searchResultsPage.scrollDownUntilPageIsFullyLoaded();
        searchResultsPage.goToPageNumber(2);
        assertThat(searchResultsPage.getItemListSize()).isGreaterThanOrEqualTo(numberOfItemOrder);
        searchResultsPage.clickOnItemNumber(numberOfItemOrder);

        ProductPage productPage = new ProductPage(driver);
        productPage.switchToLastOpenedTab();

        updateAndReportStatus("Test completed!");
    }
}
