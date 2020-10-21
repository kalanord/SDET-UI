package com.aliexpress;

import com.UIBaseTest;
import org.testng.annotations.Test;

public class ProductSearchTest extends UIBaseTest {

    @Test
    public void TestProductSearch() {
        driver.get("http://aliexpress.com");
    }

}
