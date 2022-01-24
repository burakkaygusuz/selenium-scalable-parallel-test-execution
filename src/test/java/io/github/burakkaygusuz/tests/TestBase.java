package io.github.burakkaygusuz.tests;

import io.github.burakkaygusuz.config.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBase {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(TestBase.class.getName());
    protected RemoteWebDriver driver;
    protected WebDriverWait wait;

    @BeforeAll
    public void setUp() {
        driver = new DriverFactory().createInstance();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        LOGGER.info(String.format("Operating System : %s", System.getProperty("os.name")));
        LOGGER.info(String.format("OS Version       : %s", System.getProperty("os.version")));
        LOGGER.info(String.format("Arch             : %s", System.getProperty("os.arch")));
        LOGGER.info(String.format("Browser          : %s", driver.getCapabilities().getBrowserName()));
        LOGGER.info(String.format("Browser Version  : %s", driver.getCapabilities().getBrowserVersion()));
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        LOGGER.info(String.format("Test: %s started", testInfo.getDisplayName()));
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        LOGGER.info(String.format("Test: %s finished", testInfo.getDisplayName()));
    }

    @AfterAll
    public void tearDown() {
        if (driver != null)
            driver.quit();
        driver = null;
    }
}
