package demo;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.openqa.selenium.JavascriptExecutor;
import demo.wrappers.Wrappers;
import org.openqa.selenium.support.ui.WebDriverWait; 
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class TestCases {
    ChromeDriver driver;
    @Test(enabled = true)
    public void testCase01() throws InterruptedException{

        System.out.println("Start TestCase01");
        String url = "https://www.flipkart.com/";
        Wrappers.navigate(driver,url);
        Thread.sleep(2000);
        WebElement search = driver.findElement(By.xpath("//input[@placeholder='Search for Products, Brands and More']"));
        String searchkey = "Washing Machine";
        Wrappers.sendKeys(search,searchkey);
        WebElement submit = driver.findElement(By.xpath("//button[@title='Search for Products, Brands and More']"));
        Wrappers.click(submit,driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement popularity = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='Popularity']")));
        popularity.click();
         Thread.sleep(5000);
        List<WebElement> ratings = driver.findElements(By.xpath("//span[@class='Y1HWO0']"));
        int count = 0;
        for (WebElement ratingElement : ratings) {
           String ratingText = ratingElement.getText();
           //System.out.println(ratingText);
           if (!ratingText.isEmpty()) {
            try {
                double rating = Double.parseDouble(ratingText);
                if (rating <= 4.2) {
                    count++;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid rating: " + ratingText);
            }
          }

        }

        System.out.println("Number of items with rating <= 4 stars: " + count);  
        System.out.println("End TestCase01");
   }

    @Test(enabled = true)
    public void testCase02() throws InterruptedException{

        System.out.println("Start TestCase02");
       // String url = "https://www.flipkart.com/";
       // Wrappers.navigate(driver,url);
        driver.get("https://www.flipkart.com/");
        Thread.sleep(2000);
        WebElement search = driver.findElement(By.xpath("//input[@placeholder='Search for Products, Brands and More']"));
        String searchkey = "iPhone";
        Wrappers.sendKeys(search,searchkey);
        WebElement submit = driver.findElement(By.xpath("//button[@title='Search for Products, Brands and More']"));
        Wrappers.click(submit,driver);
        Thread.sleep(5000);

        List<WebElement> titles = driver.findElements(By.xpath("//div[@class='KzDlHZ']"));

        List<WebElement> discounts = driver.findElements(By.xpath("//div[@class='UkUFwK']/span"));
        int countMoreThan17 = 0;

        for (int i = 0; i < discounts.size(); i++) {
           String discountText = discounts.get(i).getText().replace("% off", "").trim();
           String titleText = titles.get(i).getText();

           try {
              int discount = Integer.parseInt(discountText);
              if (discount > 17) {
                countMoreThan17++;
                System.out.println("Title: " + titleText + " | Discount: " + discount + "%");
               }
            } catch (NumberFormatException e) {
            System.out.println("Invalid discount format: " + discountText);
            }
        }

        System.out.println("End TestCase02");
    }

    @Test(enabled = true)
    public void testCase03() throws InterruptedException{

        System.out.println("Start TestCase03");
        driver.get("https://www.flipkart.com/");
        Thread.sleep(2000);
        WebElement search = driver.findElement(By.xpath("//input[@placeholder='Search for Products, Brands and More']"));
        String searchkey = "Coffee Mug";
        Wrappers.sendKeys(search,searchkey);
        WebElement submit = driver.findElement(By.xpath("//button[@title='Search for Products, Brands and More']"));
        Wrappers.click(submit,driver);
        Thread.sleep(5000);
        // WebElement checkbox = driver.findElement(By.xpath("(//div[@class='XqNaEv'])[1]"));
        // Thread.sleep(5000);
        // Wrappers.click(checkbox,driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='XqNaEv'])[1]")));
        checkbox.click();
        Thread.sleep(5000);

        List<WebElement> titles = driver.findElements(By.xpath("//a[@class='wjcEIp']"));
        List<WebElement> reviews = driver.findElements(By.xpath("//span[@class='Wphh3N']"));
        List<WebElement> images = driver.findElements(By.xpath("//img[@class='DByuf4']"));

        System.out.println("Top 5 products with highest reviews:");

        // Iterate through the first 5 products
        for (int i = 0; i < Math.min(5, titles.size()); i++) {
            String title = titles.get(i).getText();
            String reviewCount = reviews.get(i).getText();
            String imageUrl = images.get(i).getAttribute("src");

            System.out.println("Title: " + title);
            System.out.println("Reviews: " + reviewCount);
            System.out.println("Image URL: " + imageUrl);
            System.out.println("-----------------------------------");
        }


    }

    
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}