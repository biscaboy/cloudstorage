package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id = "nav-link")
    private WebElement navLink;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isSuccessMessage(){
        return successMessage.isDisplayed();
    }

    public boolean isErrorMessage() {
        return errorMessage.isDisplayed();
    }

    public void clickNavLink() {
        navLink.click();
    }

    public boolean waitUntilLoaded(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(navLink));
        return btn != null;
    }

}
