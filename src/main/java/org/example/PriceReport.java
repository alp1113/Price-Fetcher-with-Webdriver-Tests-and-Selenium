package org.example;

import java.util.Map;

public class PriceReport {
    public void generateReport(Map<String, Double> prices) {
        if (prices.isEmpty()) {
            System.out.println("No prices found. Please check the websites or locators.");
            return;
        }

        double minPrice = prices.values().stream().min(Double::compare).orElse(0.0);
        double maxPrice = prices.values().stream().max(Double::compare).orElse(0.0);
        double avgPrice = prices.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        System.out.println("Price Comparison Report:");
        prices.forEach((site, price) -> System.out.printf("%s: %.2f%n", site, price));
        System.out.printf("Cheapest Price: %.2f%n", minPrice);
        System.out.printf("Most Expensive Price: %.2f%n", maxPrice);
        System.out.printf("Average Price: %.2f%n", avgPrice);
    }
}