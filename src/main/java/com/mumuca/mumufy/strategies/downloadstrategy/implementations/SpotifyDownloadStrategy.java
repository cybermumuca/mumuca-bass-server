package com.mumuca.mumufy.strategies.downloadstrategy.implementations;

import com.mumuca.mumufy.strategies.downloadstrategy.DownloadStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FilenameFilter;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SpotifyDownloadStrategy implements DownloadStrategy {
    private static final String DOWNLOAD_DIR = "/home/sa1n/projetos/mumufy/src/main/resources/musics";
    private static final String PAGE = "https://lucida.to/?url=%s&country=BR";

    @Override
    public void downloadMusic(String url) {
        System.out.println("Baixando música do Spotify: " + url);

        ChromeOptions options = new ChromeOptions();

        addOptions(options);

        WebDriver driver = new ChromeDriver(options);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            driver.get(PAGE.formatted(url));

            WebElement convertButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#convert")));
            convertButton.click();
            WebElement flacOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='flac']")));
            flacOption.click();

            WebElement downSettingButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#downsetting")));
            downSettingButton.click();
            WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#downsetting option:nth-child(2)")));
            firstOption.click();

            WebElement hideFromTickerCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hide-from-ticker")));
            hideFromTickerCheckbox.click();

            WebElement downloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div > div.skeleton > div.main > div.home > div.right > div.dl-track.svelte-6pt9ji > div > div:nth-child(2) > button")));
            downloadButton.click();

            waitForDownloadToComplete(DOWNLOAD_DIR, driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
    }

    private static void waitForDownloadToComplete(String downloadDir, WebDriver driver) {
        File dir = new File(downloadDir);
        FilenameFilter filter = (file, name) -> name.endsWith(".flac");

        while (true) {
            File[] files = dir.listFiles(filter);
            if (files != null) {
                for (File file : files) {
                    if (file.exists() && file.length() > 0) {
                        System.out.println("Download concluído: " + file.getName());
                        driver.quit();
                        return;
                    }
                }
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addOptions(ChromeOptions options) {
        //options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("window-size=1920,1080");

        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-features=InterestFeedContentSuggestions");
        options.addArguments("--disable-features=Translate");
        options.addArguments("--mute-audio");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--no-first-run");
        options.addArguments("--ash-no-nudges");
        options.addArguments("--disable-search-engine-choice-screen");

        options.addArguments("--aggressive-cache-discard");
        options.addArguments("--disable-features=BackForwardCache");

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-features=BackForwardCache");

        Map<String, Object> prefs = new HashMap<>();

        prefs.put("download.default_directory", DOWNLOAD_DIR);
        prefs.put("download.prompt_for_download", false);
        prefs.put("safebrowsing.enabled", true);

        options.setExperimentalOption("prefs", prefs);
    }

    public static void main(String[] args) throws InterruptedException {
        String trackURL = "https://open.spotify.com/track/1IA7U01tZDZmMarnf9QIa4";

        ChromeOptions options = new ChromeOptions();

        addOptions(options);

        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(PAGE.formatted(trackURL));

        WebElement convertButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#convert")));
        convertButton.click();

        WebElement flacOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='flac']")));
        flacOption.click();

        WebElement downSettingButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#downsetting")));
        downSettingButton.click();

        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#downsetting option:nth-child(2)")));
        firstOption.click();

        WebElement hideFromTickerCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hide-from-ticker")));
        hideFromTickerCheckbox.click();

        WebElement downloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div > div.skeleton > div.main > div.home > div.right > div.dl-track.svelte-6pt9ji > div > div:nth-child(2) > button")));
        downloadButton.click();

        waitForDownloadToComplete(DOWNLOAD_DIR, driver);
    }
}
