package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage extends WaitablePage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "back-to-login")
    private WebElement backLink;

    @FindBy(id = "cont-to-login")
    private WebElement continueLink;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signUp(WebDriverWait wait, String firstName, String lastName, String username, String password){
        wait.until(ExpectedConditions.elementToBeClickable(By.id("inputFirstName"))).sendKeys(firstName);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("inputLastName"))).sendKeys(lastName);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("inputUsername"))).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("inputPassword"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-button"))).click();
    }

    public void signUp(String firstName, String lastName, String username, String password){
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    public String getSuccessMsg(WebDriverWait wait){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-msg"))).getText();
    }

    public String getSuccessMsg(){
        return successMsg.getText();
    }

    public String getErrorMsg(WebDriverWait wait){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-msg"))).getText();
    }

    public String getErrorMsg(){
        return errorMsg.getText();
    }

    public void clickContinueLink(WebDriver driver) {
        waitForElement(driver, "cont-to-login").click();
    }

    public void clickContinueLink() {
        continueLink.click();
    }

    public void clickGoBackLink(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("back-to-login"))).click();
    }

    public void clickGoBackLink() {
        backLink.click();
    }

    public boolean waitUntilLoaded(WebDriverWait wait) {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-button")));
        return btn != null;
    }
}

