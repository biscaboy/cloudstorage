package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.Data;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.util.ArrayList;
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

    // Notes
    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

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

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        return btn != null;
    }

    public void saveNote(String title, String description) {
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.sendKeys(description);
        saveNoteButton.click();
    }

    public void clickNotesTab() {
        navNotesTab.click();
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

    public void clickEditNoteButton(int id) {
        clickButton(editNoteButtons, id);
    }

    public void clickDeleteNoteButton(int id) {
        clickButton(deleteNoteButtons, id);
    }

    public void clickEditFileButton(int id) {
        clickButton(editFileButtons, id);
    }

    public void clickDeleteFileButton(int id) {
        clickButton(deleteFileButtons, id);
    }

    public void clickEditCredentialButton(int id) {
        clickButton(editCredentialButtons, id);
    }

    public void clickDeleteCredentialButton(int id) {
        clickButton(deleteCredentialButtons, id);
    }

    private void clickButton(List<WebElement> buttons, int id) {
        for (WebElement button : buttons) {
            if (button.getAttribute("id").indexOf("-" + id) > 0 ) {
                button.click();
                break;
            }
        }
    }

    public boolean isAnyNotes() {
        return noteTable != null;
    }

    public boolean isAnyFiles() {
        return noteTable != null;
    }

    public boolean isAnyCredentials() {
        return noteTable != null;
    }


}
