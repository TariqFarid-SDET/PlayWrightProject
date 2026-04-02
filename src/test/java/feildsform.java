import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@UsePlaywright
public class feildsform {


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

}