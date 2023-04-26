package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Wait_Part_II_Find_Element {
    WebDriver driver;
    WebDriverWait explicitWait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();

        explicitWait = new WebDriverWait(driver, 20);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Find_Element() {
        driver.get("https://www.facebook.com/");
        WebElement element = null;

        //Không có element nào tìm thấy
//		element = driver.findElement(By.id("selenium"));

        //Có 1 element được tìm thấy
//		element = driver.findElement(By.id("email"));

        //Có nhiều hơn 1 element được tìm thấy --> thao tác với element đầu tiên
        element = driver.findElement(By.xpath("//input[@id='email'or @id='pass']"));
    }

    @Test
    public void TC_02_Find_Elements() {
        driver.navigate().refresh();
        driver.get("https://www.facebook.com/");
        List<WebElement> links = null;

        //Không có element nào tìm thấy
//		links = driver.findElements(By.tagName("selenium"));
//		System.out.println("Element size = " + links.size());

        //Có 1 element được tìm thấy
//		links = driver.findElements(By.id("email"));
//		System.out.println("Element size = " + links.size());

        //Có nhiều hơn 1 element được tìm thấy
        links = driver.findElements(By.tagName("a"));
        System.out.println(links.size());
        for (WebElement tagname : links) {
            System.out.println(tagname.getAttribute("href"));

        }
    }


    @Test
    public void TC_03_Presence_In_UI() {
        driver.get("https://www.facebook.com/");

    }


    @Test
    public void TC_04_Staleness() {
        driver.get("https://www.facebook.com/");

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void sleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getRandomNumber() {
        Random rand = new Random();
        // random 1 số có 5 chữ số từ 0 -> 99999
        return rand.nextInt(99999);
    }
}