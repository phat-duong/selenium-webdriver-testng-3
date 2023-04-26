package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Wait_Part_IV_Dead {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Timeout_Less_Than_Element_Displayed() {

        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.findElement(By.xpath("//button[text()='Start']")).click();
        sleepInSecond(3);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_02_Timeout_Equal_Element_Displayed() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.findElement(By.xpath("//button[text()='Start']")).click();
        sleepInSecond(5);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_03_Timeout_Greater_Than_Element_Displayed() {

        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.findElement(By.xpath("//button[text()='Start']")).click();
        sleepInSecond(10);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(), "Hello World!");
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