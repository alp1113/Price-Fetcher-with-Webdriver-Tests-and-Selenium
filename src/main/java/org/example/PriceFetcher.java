package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class PriceFetcher {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public PriceFetcher(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public Map<String, Double> fetchPrices() {
        Map<String, Double> prices = new HashMap<>();

        // Fetch price from MediaMarkt
        prices.put("MediaMarkt", fetchPriceWithJsFallback(
                "https://www.mediamarkt.com.tr/tr/product/_apple-iphone-16-128gb-akilli-telefon-siyah-mye73tua-1239553.html",
                "span[data-test='branded-price-whole-value']"));

        // Fetch price from Trendyol
        prices.put("Trendyol", fetchPriceWithJsFallback(
                "https://www.trendyol.com/apple/iphone-16-128gb-beyaz-p-857296082?boutiqueId=638145&merchantId=968",
                "span.prc-dsc"));

        // Fetch price from Vatan
        prices.put("Vatan", fetchPriceWithJsFallback(
                "https://www.vatanbilgisayar.com/iphone-16-akilli-telefon.html",
                "span.product-list__price"));

        return prices;
    }

    private Double fetchPriceWithJsFallback(String url, String cssSelector) {
        try {
            driver.get(url);

            // Wait for page to fully load
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));

            // Scroll to element
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

            String priceText = element.getText().replaceAll("[^0-9,]", "").replace(",", ".");
            System.out.println("Price fetched using Selenium: " + priceText);
            return Double.parseDouble(priceText);
        } catch (Exception e) {
            // JavaScript fallback to extract the element text
            System.err.println("Selenium failed, trying JavaScript for URL: " + url);
            try {
                driver.get(url);
                String jsPrice = (String) ((JavascriptExecutor) driver)
                        .executeScript("return document.querySelector(arguments[0]).innerText;", cssSelector);
                jsPrice = jsPrice.replaceAll("[^0-9,]", "").replace(",", ".");
                System.out.println("Price fetched using JavaScript: " + jsPrice);
                return Double.parseDouble(jsPrice);
            } catch (Exception jsException) {
                System.err.println("Failed to fetch price using JS: " + jsException.getMessage());
                return null;
            }
        }
    }
}