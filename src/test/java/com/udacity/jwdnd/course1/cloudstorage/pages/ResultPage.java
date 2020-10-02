package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage extends WaitablePage {

    private static final String SUCCESS_MESSAGE = "success-msg";
    @FindBy(id = SUCCESS_MESSAGE)
    private WebElement successMessage;

    private static final String ERROR_MESSAGE = "error-msg";
    @FindBy(id = ERROR_MESSAGE)
    private WebElement errorMessage;

    private static final String NAV_LINK = "nav-link";
    @FindBy(id = NAV_LINK)
    private WebElement navLink;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isSuccessMessage(){
        return successMessage.isDisplayed();
    }

    public boolean isSuccessMessage(WebDriver driver) {
        return isElementDisplayed(driver, SUCCESS_MESSAGE);
    }

    public boolean isErrorMessage() {
        return errorMessage.isDisplayed();
    }

    public boolean isErrorMessage(WebDriver driver) {
        return isElementDisplayed(driver, ERROR_MESSAGE);
    }

    public void clickNavLink(WebDriver driver) {
        this.waitForElement(driver, NAV_LINK).click();
    }

}
