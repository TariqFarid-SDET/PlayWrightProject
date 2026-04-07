import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;

import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@UsePlaywright(setup.class)
public class FormValidationTest {


    @DisplayName("By fieldsText")
    @Test
    void fieldsText(Page page) {
        page.navigate("https://practicesoftwaretesting.com/contact");
        var firstName = page.getByLabel("First name");
        var lastName = page.getByLabel("Last name");
        var emailAddress = page.getByLabel("Email address");
        var message = page.getByLabel("Message ");

        firstName.fill("tariq");
        lastName.fill("farid");
        emailAddress.fill("ahmadtariq.farid@gmail.com");
        message.fill("I am an Automation Tester for many Years");


        assertThat(firstName).hasValue("tariq");
        assertThat(lastName).hasValue("farid");
        assertThat(emailAddress).hasValue("ahmadtariq.farid@gmail.com");
        assertThat(message).hasValue("I am an Automation Tester for many Years");


    }

    @DisplayName("By dropDown")
    @Test
    void byDropDown(Page page) {
        page.navigate("https://practicesoftwaretesting.com/contact");
        var byDrpDwn = page.getByLabel("Subject");
        byDrpDwn.selectOption("return");
        assertThat(byDrpDwn).hasValue("return");
    }

    @DisplayName("upLoadFile")
    @Test
    void upLoadFile(Page page) {
        page.navigate("https://practicesoftwaretesting.com/contact");

        // first option:
       /* Locator upload = page.locator("#attachment");
        upload.setInputFiles(Paths.get("src/test/resources/input.txt"));
        */

        //Second option:
        page.setInputFiles("#attachment", Paths.get("src/test/resources/input.txt"));


        //third option: we can used when attachment accepted multiple file.
       /* Locator upload = page.locator("#attachment");
        upload.setInputFiles(new Path[]{
                Paths.get("src/test/resources/input.txt"),
                Paths.get("src/test/resources/input2.txt")
        });
*/

    }
    //Get Error Message

    @DisplayName("getErrorMessage")
    @Test
    void getErrorMessage(Page page) {
        page.navigate("https://practicesoftwaretesting.com/contact");
        var bottom = page.locator(".btnSubmit");
        bottom.click();
        var errorMess = page.getByRole(AriaRole.ALERT).getByText("First name is required");
        assertThat(errorMess).isVisible();

        // get All Error Messages
        //I use page.getByRole(AriaRole.ALERT) to capture all validation messages and then call allTextContents() to extract all error texts at once.
        Locator allErrors = page.getByRole(AriaRole.ALERT).filter(new Locator.FilterOptions().setHasText(""));
        // Locator allErrors = page.locator("[role='alert']"); this is simple one
        List<String> AllErrorMessages = allErrors.allTextContents();
        // we can used in one line print like  System.out.println(AllErrorMessages);
        for (String all : AllErrorMessages) {
            System.out.println("All messages " + all);
        }


    }


}