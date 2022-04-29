package io.github.burakkaygusuz.config;

import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceBuilder;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;

public enum Browsers {

    CHROME {
        @Override
        protected RemoteWebDriver createDriver() throws MalformedURLException {
            return new RemoteWebDriver(getRemoteAddress(), getOptions());
        }

        @Override
        protected ChromeOptions getOptions() {
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("profile.managed_default_content_settings.javascript", 1);
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            final LoggingPreferences chromeLogPrefs = new LoggingPreferences();
            chromeLogPrefs.enable(LogType.PERFORMANCE, Level.SEVERE);

            final ChromeOptions chromeOptions = new ChromeOptions();

            chromeOptions.setCapability(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            chromeOptions.setCapability(ChromeDriverService.CHROME_DRIVER_VERBOSE_LOG_PROPERTY, "true");
            chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, chromeLogPrefs);

            chromeOptions.setLogLevel(ChromeDriverLogLevel.SEVERE)
                    .setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
                    .addArguments("--disable-gpu", "--disable-logging", "--disable-dev-shm-usage")
                    .setAcceptInsecureCerts(true)
                    .setPageLoadStrategy(PageLoadStrategy.NORMAL)
                    .setHeadless(HEADLESS)
                    .setExperimentalOption("prefs", prefs);

            return chromeOptions;
        }
    },

    FIREFOX {
        @Override
        protected RemoteWebDriver createDriver() throws MalformedURLException {
            return new RemoteWebDriver(getRemoteAddress(), getOptions());
        }

        @Override
        protected FirefoxOptions getOptions() {
            final FirefoxOptions firefoxOptions = new FirefoxOptions();
            final FirefoxProfile firefoxProfile = new FirefoxProfile();

            firefoxProfile.setAcceptUntrustedCertificates(true);
            firefoxProfile.setAssumeUntrustedCertificateIssuer(true);

            final LoggingPreferences firefoxLogPrefs = new LoggingPreferences();
            firefoxLogPrefs.enable(LogType.PERFORMANCE, Level.SEVERE);

            firefoxOptions.setCapability(FirefoxDriver.Capability.MARIONETTE, true);
            firefoxOptions.setCapability(CapabilityType.LOGGING_PREFS, firefoxLogPrefs);
            firefoxOptions.setCapability(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

            firefoxOptions.setLogLevel(FirefoxDriverLogLevel.WARN)
                    .addPreference("dom.webnotifications.enabled", false)
                    .addPreference("gfx.direct2d.disabled", true)
                    .addPreference("layers.acceleration.force-enabled", true)
                    .addPreference("javascript.enabled", true)
                    .setPageLoadStrategy(PageLoadStrategy.NORMAL)
                    .setHeadless(HEADLESS)
                    .setProfile(firefoxProfile);

            return firefoxOptions;
        }
    };

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    private final static boolean HEADLESS = Boolean.getBoolean("headless");

    protected abstract RemoteWebDriver createDriver() throws MalformedURLException;

    protected abstract AbstractDriverOptions<?> getOptions();

    private static URL getRemoteAddress() throws MalformedURLException {
        String remoteAddress;
        try {
            InputStream stream = Runtime.getRuntime().exec("minikube service --url selenium-router-deployment").getInputStream();
            try (Scanner scanner = new Scanner(stream).useDelimiter("\\A")) {
                remoteAddress = scanner.hasNext() ? scanner.next() : null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new URL(Objects.requireNonNull(remoteAddress));
    }
}
