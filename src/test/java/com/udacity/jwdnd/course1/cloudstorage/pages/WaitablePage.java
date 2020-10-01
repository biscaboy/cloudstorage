package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WaitablePage {

    private static final int WAIT_TIME_SECONDS = 10;

    protected WebElement waitForElement(WebDriver driver, String elementId) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME_SECONDS);
        WebElement el = wait.until(ExpectedConditions.visibilityOf(
                    driver.findElement(By.id(elementId))
                )
            );
        return el;
    }
}
