package PageObject;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.nio.file.Path;

public class ContactForm {
    private final Page page;
    private final Locator enterFirstName;
    private final Locator enterLastName;
    private final Locator enterEmail;
    private final Locator selectSubject;
    private final Locator enterMessage;
    private final Locator attachmentField;
    private final Locator clickSend;


    public ContactForm(Page page) {
        this.page = page;
        this.enterFirstName = page.getByText("First name");
        this.enterLastName = page.getByLabel("Last name");
        this.enterEmail = page.getByLabel("Email address");
        this.enterMessage = page.getByLabel("Message ");
        this.selectSubject = page.getByLabel("Subject");
        this.attachmentField = page.locator("#attachment");
        this.clickSend = page.locator(".btnSubmit");

    }

    public void EnterLastName(String lastName) {
        enterLastName.fill(lastName);
    }

    public void EnterFirstName(String firstName) {
        enterFirstName.fill(firstName);
    }

    public void EnterEmail(String emailAddress) {
        enterEmail.fill(emailAddress);
    }


    public void SelectSubject(String object) {
        selectSubject.selectOption(object);
    }

    public void EnterMessage(String text) {
        enterMessage.fill(text);

    }

    public void uploadFile(Path filePath) {
        attachmentField.setInputFiles(filePath);
    }

    public void SendButon() {
        clickSend.click();

    }

    public void clearField(String field) {
        page.getByTestId(field).click();

    }

}