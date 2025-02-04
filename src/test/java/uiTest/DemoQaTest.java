package uiTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

public class DemoQaTest {
    private final Locators locators = new Locators();
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1366x768";
        Configuration.headless = false;
        Configuration.timeout = 10000;
    }

    @BeforeEach
    public void prepareForTest(TestInfo testInfo) {
        open(baseUrl);
        executeJavaScript("document.body.style.zoom='50%'");
    }

    @AfterEach
    public void clearBrowser() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        sessionStorage().clear();
    }

    @AfterAll
    public static void tearDownAll() {
        closeWebDriver();
    }


    @Test
    @DisplayName("Open page Elements")
    public void openPageWithElementsButtons() {
        locators.clickCardByName("Elements");
        locators.checkTextInLightButton("Text Box");
        Selenide.back();
    }

    @Test
    @DisplayName("Open page Forms")
    public void openPageWithFormsButtons() {
        locators.clickCardByName("Forms");
        locators.checkTextInLightButton("Practice Form");
        Selenide.back();
    }
}
