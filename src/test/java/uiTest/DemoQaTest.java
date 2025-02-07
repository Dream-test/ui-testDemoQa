package uiTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

public class DemoQaTest {
    private static final Logger logger = LoggerFactory.getLogger(DemoQaTest.class);
    private final Locators locators = new Locators();
    private static WebDriver driver;

    private static void configurationRemote() throws URISyntaxException, MalformedURLException {
        baseUrl = "https://demoqa.com";

        if ("yes".equalsIgnoreCase(System.getenv("StartRemote"))) {
            Configuration.remote = System.getenv("SELENOID_URI");
            Configuration.browser = System.getenv("BROWSER");
            logger.info("Get environment SELENOID_URI: {}", Configuration.remote);
            logger.info("Get environment BROWSER: {}", Configuration.browser);
        } else {
            Configuration.remote = "http://localhost:4444/wd/hub";
            Configuration.browser = "firefox";
        }

        MutableCapabilities options;

        if ("firefox".equalsIgnoreCase(Configuration.browser)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();

            options = firefoxOptions;
            logger.info("FirefoxOptions: {}", options);
            logger.info("set firefox options");
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.addArguments("--window-size=1920,1080"); //1366, 768

            options = chromeOptions;
            logger.info("ChromeOptions: {}", options);
        }

        options.setCapability("acceptInsecureCerts", true);
        options.setCapability("selenoid:options", Map.of("enableVNC", true));

        assert Configuration.remote != null;
        URI selenoidURI = new URI(Configuration.remote);
        driver = new RemoteWebDriver(selenoidURI.toURL(), options);
        logger.info("WebDriver options: {}", options);
        logger.info("Current WebDriver: {}", driver);
    }

    @BeforeAll
    public static void setUp() throws MalformedURLException, URISyntaxException {
        DemoQaTest.configurationRemote();
    }

    @BeforeEach
    public void prepareForTest(TestInfo testInfo) {
        open(baseUrl);
        if (Objects.equals(Configuration.browser, "firefox")) {
            executeJavaScript("document.body.style.transform = 'scale(0.5)';");
            executeJavaScript("document.body.style.transformOrigin = '0 0';");
        } else {
            executeJavaScript("document.body.style.zoom='50%'");
        }
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
        try {
            locators.clickCardByName("Elements");
            locators.checkTextInLightButton("Text Box");
            Selenide.back();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    @Test
    @DisplayName("Open page Forms")
    public void openPageWithFormsButtons() {
        try {
            locators.clickCardByName("Forms");
            locators.checkTextInLightButton("Practice Form");
            Selenide.back();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
