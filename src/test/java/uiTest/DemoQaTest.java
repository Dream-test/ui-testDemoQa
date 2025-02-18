package uiTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.PushGateway;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

@ExtendWith(ExecutionWatcher.class)
public class DemoQaTest {
    private final Locators locators = new Locators();
    private static WebDriver driver;

    static CollectorRegistry registry;
    static Counter requests;
    public static Counter failedRequests;
    public static Counter passedRequests;
    private static String startRemote;
    private static String selenoidUri;


    private static final Logger logger = LoggerFactory.getLogger(DemoQaTest.class);

    private static void cofigurationRemote() throws URISyntaxException, MalformedURLException {
        baseUrl = "https://demoqa.com";
        //Configuration.browserSize = "1920x1080";
        startRemote = System.getenv("StartRemote");
        selenoidUri = System.getenv("SELENOID_URI");

        if ("yes".equalsIgnoreCase(startRemote) && selenoidUri != null) {
            Configuration.remote = System.getenv("SELENOID_URI");
            Configuration.browser = "chrome";

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
        } else {
            Configuration.browser = "chrome";
            Configuration.browserSize = "1366x768";
            Configuration.headless = false;
            Configuration.timeout = 1000;
        }
    }

    @BeforeAll
    public static void setUp() throws MalformedURLException, URISyntaxException {
        cofigurationRemote();

        registry = new CollectorRegistry();
        requests = Counter.build()
                .name("total_tests")
                .help("Number of tests run")
                .register(registry);
        failedRequests = Counter.build()
                .name("failed_tests")
                .help("Number of failed tests")
                .register(registry);
        passedRequests = Counter.build()
                .name("passed_tests")
                .help("Number of passed tests")
                .register(registry);

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

    @AfterEach
    public void testTearDown(TestInfo testInfo) {
        requests.inc();
        if (testInfo.getTags().contains("failed")) {
            failedRequests.inc();
        } else {
            passedRequests.inc();
        }
    }

    @AfterAll
    public static void tearDownAll() throws IOException {
        closeWebDriver();

        boolean isCI = "true".equalsIgnoreCase(System.getenv("GITHUB_ACTIONS"));
        logger.info("is CI: {}", isCI);
        if (!isCI) {
            String uri;
            if ("yes".equalsIgnoreCase(startRemote) && selenoidUri != null) {
                uri = System.getenv("PushGateway_URI");
                logger.info("PushGateway URI: {}", uri);
            } else {
                uri = "localhost:9091";
            }

            PushGateway pg = new PushGateway(uri);
            pg.push(registry, "my_batch_job");
        }
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
