package demo.wrappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.annotations.Test;

public class Wrappers {

    public static boolean navigate(WebDriver driver, String url) {
        
            if (!driver.getCurrentUrl().equals(url)) {
                driver.navigate().to(url);
                return true;
            } else {
                return false;
            }
        
    }

     public static boolean sendKeys(WebElement inputBox, String keyToSend) {

            inputBox.clear();
            inputBox.sendKeys(keyToSend);
            return true;
       
    }

    public static boolean click(WebElement elementToClick, WebDriver driver) throws InterruptedException {

            if (elementToClick.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",elementToClick);
                Thread.sleep(2000);
                elementToClick.click();
                return true;
            } else {
                return false;
            }

    }
   
}
