package io.github.burakkaygusuz.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    private static final Logger LOGGER = (Logger) LogManager.getLogger(DriverFactory.class.getName());
    private final ThreadLocal<RemoteWebDriver> driverThreadLocal = new ThreadLocal<>();
    private final Browsers selectedBrowser;

    public DriverFactory() {
        Browsers defaultBrowser = Browsers.CHROME;
        String browser = System.getProperty("browser", defaultBrowser.toString()).toUpperCase();

        try {
            defaultBrowser = Browsers.valueOf(browser);
        } catch (IllegalArgumentException | NullPointerException ignored) {
            LOGGER.warn(String.format("Unknown driver specified, defaulting to '%s'...", defaultBrowser));
        }
        this.selectedBrowser = defaultBrowser;
    }

    public synchronized RemoteWebDriver getDriver() {
        driverThreadLocal.set(selectedBrowser.createDriver());
        return driverThreadLocal.get();
    }
}
