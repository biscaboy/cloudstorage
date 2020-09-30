package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    public static final String FILE_TAB = "File";
    public static final String NOTE_TAB = "Note";
    public static final String CREDENTIAL_TAB = "Credential";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_DELETE = "delete";

    @FindBy(id = "btn-logout")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "btn-add-notes-modal")
    private WebElement addNotesModalButton;

    @FindBy(id = "btn-add-file-modal")
    private WebElement addFileModalButton;

    @FindBy(id = "btn-add-credential-modal")
    private WebElement addCredentialModalButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "btn-save-note")
    private WebElement saveNoteButton;

    @FindBy(id = "btn-save-credential")
    private WebElement saveCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInput;

    @FindBy(xpath = "//button[starts-with(@id,'btn-edit-note-')]")
    private List<WebElement> editNoteButtons;

    @FindBy(xpath = "//a[starts-with(@id,'btn-delete-note-')]")
    private List<WebElement> deleteNoteButtons;

    @FindBy(xpath = "//button[starts-with(@id,'btn-edit-file-')]")
    private List<WebElement> editFileButtons;

    @FindBy(xpath = "//a[starts-with(@id,'btn-delete-file-')]")
    private List<WebElement> deleteFileButtons;

    @FindBy(xpath = "//button[starts-with(@id,'btn-edit-credential-')]")
    private List<WebElement> editCredentialButtons;

    @FindBy(xpath = "//a[starts-with(@id,'btn-delete-credential-')]")
    private List<WebElement> deleteCredentialButtons;

    @FindBy(id = "noteTable")
    private WebElement noteTable;

    @FindBy(id = "fileTable")
    private WebElement fileTable;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id ="note-row")
    private List<WebElement> noteRows;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean waitUntilLoaded(WebDriver driver) {
        WebDriverWait _wait = new WebDriverWait(driver, 5000);
        WebElement _btn = _wait.until(ExpectedConditions.elementToBeClickable(addCredentialModalButton));
        return _btn != null;
    }

    public void saveNote(String title, String description) {
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.sendKeys(description);
        saveNoteButton.click();
    }

    public void saveCredential(String url, String username, String password) {
        credentialUrlInput.sendKeys(url);
        credentialUsernameInput.sendKeys(username);
        credentialPasswordInput.sendKeys(password);
        saveCredentialButton.click();
    }

    public String getLastAddedNoteEditButtonID(){
        return getLastAddedButtonID(editNoteButtons);
    }

    public String getLastAddedNoteDeleteButtonID(){
        return getLastAddedButtonID(deleteNoteButtons);
    }

    public String getLastAddedFileEditButtonID(){
        return getLastAddedButtonID(editFileButtons);
    }

    public String getLastAddedFileDeleteButtonID(){
        return getLastAddedButtonID(deleteFileButtons);
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

    public void clickCredentailsTab() {
        navCredentialsTab.click();
    }

    public void clickAddNoteButton() {
        addNotesModalButton.click();
    }

    public void clickAddFileButton() {
        addFileModalButton.click();
    }

    public void clickAddCredentialButton() {
        addCredentialModalButton.click();
    }

    public void clickEditNoteButton(String id) {
        clickButton(editNoteButtons, id);
    }

    public void clickDeleteNoteButton(String id) {
        clickButton(deleteNoteButtons, id);
    }

    public void clickEditFileButton(String id) {
        clickButton(editFileButtons, id);
    }

    public void clickDeleteFileButton(String id) {
        clickButton(deleteFileButtons, id);
    }

    public void clickEditCredentialButton(String id) {
        clickButton(editCredentialButtons, id);
    }

    public void clickDeleteCredentialButton(String id) {
        clickButton(deleteCredentialButtons, id);
    }

    private void clickButton(List<WebElement> buttons, String id) {
        for (WebElement _button : buttons) {
            if (_button.getAttribute("id").equals(id)) {
                _button.click();
                break;
            }
        }
    }

    public boolean isAnyNoteDisplayed() {
        return isElementDisplayed(noteTable);
    }

    public boolean isAnyCredentialDisplayed() {
        return isElementDisplayed(credentialTable);
    }

    public boolean isAnyFileDisplayed() {
        return isElementDisplayed(fileTable);
    }

    private boolean isElementDisplayed(WebElement elem) {
        try {
            elem.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAnyFiles() {
        return noteTable != null;
    }

    public String getDecryptedPassword() {
        return credentialPasswordInput.getAttribute("value");
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }

}
