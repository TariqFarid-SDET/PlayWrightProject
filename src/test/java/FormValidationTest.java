import BaseSetup.setup;
import PageObject.ContactForm;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@UsePlaywright(setup.class)
public class FormValidationTest {

    ContactForm contactForm;

    @BeforeEach
    void setupurl(Page page) {
        page.navigate("https://practicesoftwaretesting.com/contact");
    }

    @DisplayName("Using POM For this Test")
    @Test
    void fieldsForm(Page page) {
        ContactForm contactForm = new ContactForm(page);

        contactForm.EnterFirstName("Ahmad");
        contactForm.EnterLastName("Farid");
        contactForm.EnterEmail("ahmadtariq.farid@gmail.com");
        contactForm.SelectSubject("return");
        contactForm.EnterMessage("I am an Automation Tester for many Years");
        contactForm.uploadFile(Paths.get("src/test/resources/input.txt"));
        contactForm.SendButon();
        contactForm.clearField("sdsdfs");


    }


    //Get Error Message

    @DisplayName("getErrorMessage")
    @Test
    void getErrorMessage(Page page) {
        page.navigate("https://practicesoftwaretesting.com/contact");
        page.locator(".btnSubmit").click();
        var errorMess = page.getByRole(AriaRole.ALERT).getByText("First name is required");
        assertThat(errorMess).isVisible();


        // get All Error Messages
        //I use page.getByRole(AriaRole.ALERT) to capture all validation messages and then call allTextContents() to extract all error texts at once.
        Locator allErrors = page.getByRole(AriaRole.ALERT).filter(new Locator.FilterOptions().setHasText(""));
        // Locator allErrors = page.locator("[role='alert']"); this is simple one
        List<String> AllErrorMessages = allErrors.allTextContents();
        // we can used in one line print like  System.out.println(AllErrorMessages);
        for (String all : AllErrorMessages) {
            System.out.println("Error messages :  " + all);

        }


    }

    @DisplayName("By HardWait")
    @Test
    void HardWait(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        var dropList = page.locator(".form-select");
        dropList.selectOption("Price (High - Low)");
        dropList.click();
        assertThat(dropList).isVisible();
        page.waitForTimeout(5000);


    }

    @DisplayName("By CheckedBox")
    @Test
    void CheckedBox(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        var chckBox = page.getByLabel(" Screwdriver ");
        chckBox.check();
        assertThat(chckBox).isChecked();


    }

    @BeforeEach
    void setup(Page page) {
        page.navigate("https://practicesoftwaretesting.com");

    }

    @DisplayName(" Used Explicit Wait")
    @Test
    void ExplicitWait(Page page) {

        page.getByRole(AriaRole.MENUBAR).getByText("Categories").click();
        page.getByRole(AriaRole.MENUBAR).getByText("Power Tools").click();

        page.waitForSelector(".card");

        var getAll = page.getByTestId("product-name").allInnerTexts();
        Assertions.assertThat(getAll).contains("Sheet Sander", "Belt Sander");


    }

    @DisplayName("Wait for element to appear and disappear")
    @Test
    void appearDisappearMessage(Page page) {
        page.getByText("Bolt Cutters").click();
        page.locator("#btn-add-to-cart").click();

        assertThat(page.getByRole(AriaRole.ALERT)).isVisible();
        assertThat(page.getByRole(AriaRole.ALERT)).hasText("Product added to shopping cart.");


    }

    @DisplayName(" Count the cart bar")
// User Add an Items in the cart, we check the add items message and amount of items in the cart.
    @Test
    void CountCartBar(Page page) {
        page.getByText("Bolt Cutters").click();
        page.locator("#btn-add-to-cart").click();
        page.getByTestId("cart-quantity").click();
        Locator ItimsCount = page.getByTestId("product-quantity");
        assertThat(ItimsCount).hasValue("1");
        Locator Item = page.getByTestId("product-title");
        Item.waitFor();
        assertThat(Item).hasText("Bolt Cutters");


    }

    @DisplayName("Get All Price")
    @Test
    void getAllPrice(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        page.getByTestId("sort").selectOption("Price (High - Low)");
        page.getByTestId("product-price").first().waitFor();
        var AllPrices = page.getByTestId("product-price")

                .allInnerTexts()
                .stream()
                .toList();

        System.out.println("All Price is: " + AllPrices);


    }

    //This is API Url: https://api.practicesoftwaretesting.com/products?page=0&sort=price,desc&between=price,1,100&is_rental=false
    // instead of getting all price from UI we get them from API directly.
    @DisplayName("Get All Price From API")
    @Test
    void getAllPriceFromAPI(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForResponse("**products?page=0&sort=price**", () -> {
            page.getByTestId("sort").selectOption("Price (High - Low)");
        });

        var AllPrices = page.getByTestId("product-price")

                .allInnerTexts()
                .stream()
                .toList();

        System.out.println("All Price is: " + AllPrices);
        Assertions.assertThat(AllPrices)
                .isNotEmpty()
                .isSortedAccordingTo(Comparator.reverseOrder());
    }
}