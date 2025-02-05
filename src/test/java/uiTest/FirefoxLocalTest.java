package uiTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
/*
public class FirefoxLocalTest {
    private final Locators locators = new Locators();
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.timeout = 10000;
        FirefoxOptions options = new FirefoxOptions();
        //options.addPreference("layout.css.devPixelsPerPx", "0.5");
        //options.addArguments("--width=1366");
        //options.addArguments("--height=768");
        options.setCapability("moz:debuggerAddress", false);
        options.addArguments("--headless");
        options.setCapability("webSocketUrl", true);
        //Configuration.browserSize = "1366x768";
        //Configuration.headless = false;
        //Configuration.timeout = 10000;
        driver = new FirefoxDriver(options);
        WebDriverRunner.setWebDriver(driver);
        open(baseUrl);
    }

    @BeforeEach
    public void scopeBrowser() {
        executeJavaScript("document.body.style.zoom='50%'");
    }

    @AfterEach
    public void clearBrowser() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        sessionStorage().clear();
        refresh();
    }

    @AfterAll
    public static void tearDownAll() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер
        }
        Selenide.closeWebDriver();
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
} */
