package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "btn-logout")
    private WebElement logoutButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        return btn != null;
    }

}
