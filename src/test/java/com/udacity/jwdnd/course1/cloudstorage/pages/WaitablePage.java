package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A WaitablePage encapsulates the WebDriverWait object for
 * all implementing Pages.
 *
 * The Selenium WebDriver object fails to find page elements
 * outside of the context of the ExpectedConditions function.
 *
 * A note on using ExpectedConditions and WebDriverWaits:
 * if a WebElement that is defined by a @FindBy annotation using
 * the PageFactory is passed to an ExpectedCondition, the
 * method call fails to attach to the current browser DOM
 * in the driver.  The driver.findElement(By.x()) method has to be used
 * inside the ExpectedCondition function call to connect to the
 * active DOM context.
 *
 * Therefore, the @FindBy pattern fails in when placing any type of
 * explicit wait on an element.  Implementing classes have to provide
 * String representations of each dom element that will be searched.
 *
 * To do this the suggested pattern for proper encapsulation of
 * element names away from the JUnit test is to use static final Strings
 * for all element names.  That way any element name is only defined once
 * and can be changed to reflect changes in the DOM page design.
 */
public abstract class WaitablePage {

    private static final int LONG_WAIT_TIME_SECONDS = 5;
    private static final int SHORT_WAIT_TIME_SECONDS = 2;

    /**
     * Sets a wait on the driver until the elementId is found in the DOM.
     * If not found a NoSuchElementException is thrown.
     *
     * @param driver The current WebDriver
     * @param elementId the element to look for.
     * @return the WebElement, if found.
     * @throws NoSuchElementException, if not found.
     */
    protected WebElement waitForElement(WebDriver driver, String elementId) {
        WebDriverWait wait = new WebDriverWait(driver, LONG_WAIT_TIME_SECONDS);
        return wait.until(ExpectedConditions.visibilityOf(
                            driver.findElement(By.id(elementId))));

    }

    /**
     * Validates that an element is visible on the page.
     * @param driver The current WebDriver
     * @param elementId the element to be checked.
     * @return true if found, false if not there.
     */
    protected boolean isElementDisplayed(WebDriver driver, String elementId) {
        try {
            return waitForElement(driver, elementId).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Sets a wait on the driver and searches the entire page for
     * a search text.  Returns the first instance of the text located on
     * the current DOM attached to the driver.
     *
     * @param driver The current WebDriver connect to the DOM to be searched.
     * @param text the search text to be found.
     * @return The text of the first element found using the search text or
     * null if the text is not found.
     */
    public String findOnPage(WebDriver driver, String text) {
        return findOnPage(driver, text, LONG_WAIT_TIME_SECONDS);
    }

    /**
     * Sets a wait on the driver and searches the entire page for
     * a search text.
     *
     * @param driver The current WebDriver connect to the DOM to be searched.
     * @param text the search text to be found.
     * @return true if the text is found.
     */
    public boolean isOnPage(WebDriver driver, String text) {
        return findOnPage(driver, text, LONG_WAIT_TIME_SECONDS) != null;
    }

    /**
     * Sets a wait on the driver and searches the entire page for
     * the existence of search text.
     *
     * @param driver The current WebDriver connect to the DOM to be searched.
     * @param text the search text to be found.
     * @return true if the text is NOT found.
     */
    public boolean isNotOnPage(WebDriver driver, String text) {
        return findOnPage(driver, text, SHORT_WAIT_TIME_SECONDS) == null;
    }

    private String findOnPage(WebDriver driver, String text, int seconds) {
        try {
            WebElement el = new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + text + "']"))));
            return el.getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

}
