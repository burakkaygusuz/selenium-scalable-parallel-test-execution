package io.github.burakkaygusuz.config;

import lombok.SneakyThrows;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Browsers {

    CHROME {
        @Override
        @SneakyThrows(MalformedURLException.class)
        protected RemoteWebDriver createDriver() {
            return new RemoteWebDriver(new URL("http://localhost:4444"), getOptions());
        }

        @Override
        protected ChromeOptions getOptions() {
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("profile.managed_default_content_settings.javascript", 1);
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            final ChromeOptions chromeOptions = new ChromeOptions();

            chromeOptions.setLogLevel(ChromeDriverLogLevel.SEVERE)
                    .setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
                    .addArguments("--disable-gpu", "--disable-logging", "--disable-dev-shm-usage")
                    .setAcceptInsecureCerts(true)
                    .setHeadless(HEADLESS)
                    .setExperimentalOption("prefs", prefs);

            return chromeOptions;
        }
    },

    FIREFOX {
        @Override
        @SneakyThrows(MalformedURLException.class)
        protected RemoteWebDriver createDriver() {
            return new RemoteWebDriver(new URL("http://localhost:4444"), getOptions());
        }

        @Override
        protected FirefoxOptions getOptions() {
            final FirefoxOptions firefoxOptions = new FirefoxOptions();
            final FirefoxProfile firefoxProfile = new FirefoxProfile();

            firefoxProfile.setAcceptUntrustedCertificates(true);
            firefoxProfile.setAssumeUntrustedCertificateIssuer(true);

            firefoxOptions.setLogLevel(FirefoxDriverLogLevel.WARN)
                    .addPreference("dom.webnotifications.enabled", false)
                    .addPreference("gfx.direct2d.disabled", true)
                    .addPreference("layers.acceleration.force-enabled", true)
                    .addPreference("javascript.enabled", true)
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

    protected abstract RemoteWebDriver createDriver();

    protected abstract AbstractDriverOptions<?> getOptions();

}
