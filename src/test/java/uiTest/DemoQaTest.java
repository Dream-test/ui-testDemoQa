package uiTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

public class DemoQaTest {
    private final Locators locators = new Locators();
    private static WebDriver driver;

    private static void cofigurationRemote() throws URISyntaxException, MalformedURLException {
        baseUrl = "https://demoqa.com";
        //Configuration.browserSize = "1920x1080";

        if (System.getenv("StartRemote").equals("yes")) {
            Configuration.remote = System.getenv("SELENOID_URI");
            Configuration.browser = "chrome";
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--window-size=1920,1080");
        //options.addArguments("--force-device-scale-factor=0.5");

        options.setCapability("browserName", "chrome");
        options.setCapability("acceptInsecureCerts", true);
        options.setCapability("selenoid:options", Map.of("enableVNC", true));

        assert Configuration.remote != null;
        URI selenoidURI = new URI(Configuration.remote);
        driver = new RemoteWebDriver(selenoidURI.toURL(), options);
    }

    @BeforeAll
    public static void setUp() throws MalformedURLException, URISyntaxException {
        cofigurationRemote();
       // baseUrl = "https://demoqa.com";
       // Configuration.browserSize = "1366x768";
       // Configuration.headless = false;
       // Configuration.timeout = 10000;
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
