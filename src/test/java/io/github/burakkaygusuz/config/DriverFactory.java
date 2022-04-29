package io.github.burakkaygusuz.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

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

    public synchronized RemoteWebDriver createInstance() {
        try {
            driverThreadLocal.set(selectedBrowser.createDriver());
        } catch (MalformedURLException e) {
            LOGGER.error(String.format("A malformed URL has occurred. No legal protocol could be found or string could not be parsed: \n %s",
                    ExceptionUtils.getRootCause(e)));
        }
        return driverThreadLocal.get();
    }
}
