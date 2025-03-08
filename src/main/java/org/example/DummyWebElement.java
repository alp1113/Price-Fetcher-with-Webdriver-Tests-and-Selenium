package org.example;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DummyWebElement implements WebElement {
    private final String text;

    public DummyWebElement(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    // Implement getScreenshotAs method
    @Override
    public <X> X getScreenshotAs(OutputType<X> target) {
        throw new UnsupportedOperationException("Screenshots are not supported in DummyWebElement.");
    }

    // Stub methods for all other WebElement methods
    @Override public void click() {}
    @Override public void submit() {}
    @Override public void sendKeys(CharSequence... keysToSend) {}
    @Override public void clear() {}
    @Override public String getTagName() { return null; }
    @Override public String getAttribute(String name) { return null; }
    @Override public boolean isSelected() { return false; }
    @Override public boolean isEnabled() { return true; }
    @Override public String getCssValue(String propertyName) { return null; }
    @Override public boolean isDisplayed() { return true; }
    @Override public org.openqa.selenium.Point getLocation() { return null; }
    @Override public org.openqa.selenium.Dimension getSize() { return null; }
    @Override public org.openqa.selenium.Rectangle getRect() { return null; }
    @Override public List<WebElement> findElements(org.openqa.selenium.By by) { return null; }
    @Override public WebElement findElement(org.openqa.selenium.By by) { return null; }
}