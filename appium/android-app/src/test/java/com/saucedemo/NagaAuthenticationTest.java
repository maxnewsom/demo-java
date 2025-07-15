package com.saucedemo;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver; // Or io.appium.java_client.AppiumDriver
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.Set;
import java.util.HashMap;
import java.util.Map; // Import Map interface
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.JavascriptExecutor;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableList;
import org.openqa.selenium.NoSuchElementException;
import java.util.List;

public class NagaAuthenticationTest extends TestBase {


@Test
    public void acceptTermsButton() throws InterruptedException {

        By acceptCheckbox = AppiumBy.xpath("//android.view.View[@resource-id='privacy_main']/android.view.View[2]");
        By acknowledgeButton = AppiumBy.xpath("//android.widget.Button[@resource-id='ackBtn']");
        JavascriptExecutor js = driver;
        int scrollAttempts = 0;
        int maxScrollAttempts = 10;

        //Keeps scrolling until it finds the accept button at the bottom
        while (scrollAttempts < maxScrollAttempts) {
            // Check if the element is present and displayed
            List<WebElement> elements = driver.findElements(acceptCheckbox);
            if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                js.executeScript("sauce:context=Accept checkbox found!");
                break;
            }
            // Log context and scroll down
            js.executeScript("sauce:context=Scrolling down");
            driver.executeScript("mobile: shell", ImmutableMap.of(
                    "command", "input",
                    "args", ImmutableList.of("swipe", "500", "1000", "500", "300", "1000")
            ));

            scrollAttempts++;
        }

        if (scrollAttempts == maxScrollAttempts) {
            throw new NoSuchElementException("acceptCheckbox not found after " + maxScrollAttempts + " scrolls.");
        }


        waitAndClick(acceptCheckbox);
        waitAndClick(acknowledgeButton);

        //Thread.sleep(10000);
    }

    private void waitAndClick(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
}