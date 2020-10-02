package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage extends WaitablePage {

    private static final String BUTTON_LOGOUT = "btn-logout";
    @FindBy(id = BUTTON_LOGOUT)
    private WebElement logoutButton;

    private static final String NAV_FILES_TAB = "nav-files-tab";
    @FindBy(id = NAV_FILES_TAB)
    private WebElement navFilesTab;

    private static final String NAV_NOTES_TAB = "nav-notes-tab";
    @FindBy(id = NAV_NOTES_TAB)
    private WebElement navNotesTab;

    private static final String NAV_CREDENTIALS_TAB = "nav-credentials-tab";
    @FindBy(id = NAV_CREDENTIALS_TAB)
    private WebElement navCredentialsTab;

    private static final String BUTTON_ADD_NOTES_MODAL = "btn-add-notes-modal";
    @FindBy(id = BUTTON_ADD_NOTES_MODAL)
    private WebElement addNotesModalButton;

    private static final String BUTTON_ADD_CREDENTIALS_MODAL = "btn-add-credential-modal";
    @FindBy(id = BUTTON_ADD_CREDENTIALS_MODAL)
    private WebElement addCredentialModalButton;

    private static final String INPUT_NOTE_TITLE = "note-title";
    @FindBy(id = INPUT_NOTE_TITLE)
    private WebElement noteTitleInput;

    private static final String INPUT_NOTE_DESCRIPTION = "note-description";
    @FindBy(id = INPUT_NOTE_DESCRIPTION)
    private WebElement noteDescriptionInput;

    private static final String BUTTON_SAVE_NOTE = "btn-save-note";
    @FindBy(id = BUTTON_SAVE_NOTE)
    private WebElement saveNoteButton;

    private static final String BUTTON_SAVE_CREDENTIAL = "btn-save-credential";
    @FindBy(id = BUTTON_SAVE_CREDENTIAL)
    private WebElement saveCredentialButton;

    private static final String INPUT_CREDENTIAL_URL = "credential-url";
    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    private static final String INPUT_CREDENTIAL_USERNAME = "credential-username";
    @FindBy(id = INPUT_CREDENTIAL_USERNAME)
    private WebElement credentialUsernameInput;

    private static final String INPUT_CREDENTIAL_PASSWORD = "credential-password";
    @FindBy(id = INPUT_CREDENTIAL_PASSWORD)
    private WebElement credentialPasswordInput;

    private static final String BUTTONS_EDIT_NOTE = "//button[starts-with(@id,'btn-edit-note-')]";
    @FindBy(xpath = BUTTONS_EDIT_NOTE)
    private List<WebElement> editNoteButtons;

    private static final String BUTTONS_DELETE_NOTE = "//a[starts-with(@id,'btn-delete-note-')]";
    @FindBy(xpath = BUTTONS_DELETE_NOTE)
    private List<WebElement> deleteNoteButtons;

    private static final String BUTTONS_EDIT_FILE = "//button[starts-with(@id,'btn-edit-file-')]";
    @FindBy(xpath = BUTTONS_EDIT_FILE)
    private List<WebElement> editFileButtons;

    private static final String BUTTONS_DELETE_FILE = "//a[starts-with(@id,'btn-delete-file-')]";
    @FindBy(xpath = BUTTONS_DELETE_FILE)
    private List<WebElement> deleteFileButtons;

    private static final String BUTTONS_EDIT_CREDENTIAL = "//button[starts-with(@id,'btn-edit-credential-')]";
    @FindBy(xpath = BUTTONS_EDIT_CREDENTIAL)
    private List<WebElement> editCredentialButtons;

    private static final String BUTTONS_DELETE_CREDENTIAL = "//a[starts-with(@id,'btn-delete-credential-')]";
    @FindBy(xpath = BUTTONS_DELETE_CREDENTIAL)
    private List<WebElement> deleteCredentialButtons;

    private static final String NOTE_TABLE = "noteTable";
    @FindBy(id = NOTE_TABLE)
    private WebElement noteTable;

    private static final String FILE_TABLE = "fileTable";
    @FindBy(id = FILE_TABLE)
    private WebElement fileTable;

    private static final String CREDENTIAL_TABLE = "credentialTable";
    @FindBy(id = CREDENTIAL_TABLE)
    private WebElement credentialTable;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded(WebDriver driver) {
        return isElementDisplayed(driver, BUTTON_LOGOUT);
    }

    public void saveNote(String title, String description) {
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.sendKeys(description);
        saveNoteButton.click();
    }

    public void saveNote(WebDriver driver, String title, String description) {
        waitForElement(driver, INPUT_NOTE_TITLE).sendKeys(title);
        waitForElement(driver, INPUT_NOTE_DESCRIPTION).sendKeys(description);
        waitForElement(driver, BUTTON_SAVE_NOTE).click();
    }

    public void saveCredential(WebDriver driver, String url, String username, String password) {
        waitForElement(driver, INPUT_CREDENTIAL_URL).sendKeys(url);
        waitForElement(driver, INPUT_CREDENTIAL_USERNAME).sendKeys(username);
        waitForElement(driver, INPUT_CREDENTIAL_PASSWORD).sendKeys(password);
        waitForElement(driver, BUTTON_SAVE_CREDENTIAL).click();
    }

    public String getLastAddedNoteEditButtonID(){
        return getLastAddedButtonID(editNoteButtons);
    }

    public String getLastAddedNoteDeleteButtonID(){
        return getLastAddedButtonID(deleteNoteButtons);
    }

    public String getLastAddedCredentialEditButtonID(){
        return getLastAddedButtonID(editCredentialButtons);
    }

    public String getLastAddedCredentialDeleteButtonID(){
        return getLastAddedButtonID(deleteCredentialButtons);
    }

    private String getLastAddedButtonID(List<WebElement> buttons) {
        String _lastNoteId = null;
        for (WebElement _button : buttons) {
            _lastNoteId = _button.getAttribute("id");
        }
        return _lastNoteId;

    }

    public void clickNotesTab() {
        navNotesTab.click();
    }

    public void clickNotesTab(WebDriver driver) {
        waitForElement(driver, NAV_NOTES_TAB).click();
    }

    public void clickCredentialsTab() {
        navCredentialsTab.click();
    }

    public void clickCredentialsTab(WebDriver driver) {
        waitForElement(driver, NAV_CREDENTIALS_TAB).click();
    }

    public void clickAddNoteButton() { addNotesModalButton.click(); }

    public void clickAddNoteButton(WebDriver driver) {
        waitForElement(driver, BUTTON_ADD_NOTES_MODAL).click();
    }

    public void clickAddCredentialButton() {
        addCredentialModalButton.click();
    }

    public void clickAddCredentialButton(WebDriver driver) {
        waitForElement(driver, BUTTON_ADD_CREDENTIALS_MODAL).click();
    }

    public void clickEditNoteButton(String id) {
        clickButton(editNoteButtons, id);
    }

    public void clickEditNoteButton(WebDriver driver, String id) {
        clickButton(driver, id);
    }

    public void clickDeleteNoteButton(String id) {
        clickButton(deleteNoteButtons, id);
    }

    public void clickDeleteNoteButton(WebDriver driver, String id) {
        clickButton(driver, id);
    }

    public void clickEditCredentialButton(String id) {
        clickButton(editCredentialButtons, id);
    }

    public void clickEditCredentialButton(WebDriver driver, String id) {
        clickButton(driver, id);
    }

    public void clickDeleteCredentialButton(String id) {
        clickButton(deleteCredentialButtons, id);
    }

    public void clickDeleteCredentialButton(WebDriver driver, String id) {
        clickButton(driver, id);
    }

    private void clickButton(List<WebElement> buttons, String id) {
        for (WebElement _button : buttons) {
            if (_button.getAttribute("id").equals(id)) {
                _button.click();
                break;
            }
        }
    }

    private void clickButton(WebDriver driver, String id) {
        waitForElement(driver, id).click();
    }

    public boolean isAnyNoteDisplayed() {
        return isElementDisplayed(noteTable);
    }

    public boolean isAnyNoteDisplayed(WebDriver driver) {
        return isElementDisplayed(driver, NOTE_TABLE);
    }

    public boolean isAnyCredentialDisplayed() {
        return isElementDisplayed(credentialTable);
    }

    public boolean isAnyCredentialDisplayed(WebDriver driver) {
        return isElementDisplayed(driver, CREDENTIAL_TABLE);
    }

    public String getDecryptedPassword() {
        return credentialPasswordInput.getAttribute("value");
    }

    public String getDecryptedPassword(WebDriver driver) {
        return waitForElement(driver, INPUT_CREDENTIAL_PASSWORD).getAttribute("value");
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }

    public void clickLogoutButton(WebDriver driver) {
        waitForElement(driver, BUTTON_LOGOUT).click();
    }

    private boolean isElementDisplayed(WebElement elem) {
        try {
            return elem.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
