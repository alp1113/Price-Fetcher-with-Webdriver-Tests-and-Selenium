package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Optional;
import java.util.DoubleSummaryStatistics;

public class PriceComparisonTest {
    private WebDriver driver;
    private PriceFetcher priceFetcher;

    @BeforeClass
    public void setup() {
        // Setup WebDriver
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        priceFetcher = new PriceFetcher(driver);
    }

    @Test
    public void testFetchPrices() {
        Map<String, Double> prices = priceFetcher.fetchPrices();

        // Print fetched prices
        System.out.println("\nFetched Prices:");
        prices.forEach((site, price) -> {
            if (price != null) {
                System.out.println(site + ": " + price + " TL");
            } else {
                System.out.println(site + ": Price not available");
            }
        });

        // Calculate minimum price and its site
        Optional<Map.Entry<String, Double>> minPriceEntry = prices.entrySet().stream()
                .filter(entry -> entry.getValue() != null) // Filter out null prices
                .min(Map.Entry.comparingByValue());

        // Calculate average price
        DoubleSummaryStatistics stats = prices.values().stream()
                .filter(price -> price != null)
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        // Print results
        if (minPriceEntry.isPresent()) {
            System.out.println("\nMinimum Price: " + minPriceEntry.get().getValue() + " TL");
            System.out.println("Site with Minimum Price: " + minPriceEntry.get().getKey());
        } else {
            System.out.println("\nNo valid minimum price found.");
        }

        if (stats.getCount() > 0) {
            System.out.println("Average Price: " + String.format("%.2f", stats.getAverage()) + " TL");
        } else {
            System.out.println("No valid prices to calculate the average.");
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}