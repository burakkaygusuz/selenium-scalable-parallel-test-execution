package io.github.burakkaygusuz.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Scanner;

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
            driverThreadLocal.set(selectedBrowser.createDriver(getRemoteAddress()));
        } catch (MalformedURLException e) {
            LOGGER.error(String.format("A malformed URL has occurred. No legal protocol could be found or string could not be parsed: \n %s",
                    ExceptionUtils.getRootCause(e)));
        }
        return driverThreadLocal.get();
    }

    private static String getRemoteAddress() {
        String remoteAddress = null;
        try (InputStream stream = Runtime.getRuntime().exec("minikube service --url selenium-router-deployment").getInputStream()) {
            try (Scanner scanner = new Scanner(stream).useDelimiter("\\A")) {
                remoteAddress = scanner.hasNext() ? scanner.next() : null;
            }
        } catch (IOException e) {
            LOGGER.error(String.format("An error occurred while getting the URL : \n %s",
                    ExceptionUtils.getRootCause(e)));
        }
        return remoteAddress;
    }
}
