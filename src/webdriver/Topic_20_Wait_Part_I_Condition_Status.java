package webdriver;

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

public class Topic_20_Wait_Part_I_Condition_Status {
    WebDriver driver;
    WebDriverWait explicitWait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();

        explicitWait = new WebDriverWait(driver, 20);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Visible() {
        driver.get("https://www.facebook.com/");
        explicitWait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']")))
                .click();
//		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']")))
                .sendKeys("abc@gmail.com");
//		driver.findElement(By.xpath("//input[@name='reg_email__']")).sendKeys("abc@gmail.com");

        // wait cho element được visible/displayed
        WebElement confirmEmailTextbox = explicitWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
        Dimension confirmEmailSize = confirmEmailTextbox.getSize();
        System.out.println("Confirm email height = " + confirmEmailSize.getHeight());
        System.out.println("Confirm email width = " + confirmEmailSize.getWidth());
    }

    @Test
    public void TC_02_Invisible_In_DOM() {
        driver.get("https://www.facebook.com/");
        explicitWait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']")))
                .click();

        sleepInSecond(3);

        // wait cho element được invisible/undisplayed
        // element không hiển thị trên UI nhưng vẫn có trong DOM
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
        WebElement confirmEmailTextbox = driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']"));

        Dimension confirmEmailSize = confirmEmailTextbox.getSize();
        System.out.println("Confirm email height = " + confirmEmailSize.getHeight());
        System.out.println("Confirm email width = " + confirmEmailSize.getWidth());
    }

    @Test
    public void TC_02_Invisible_Not_In_DOM() {
        driver.get("https://www.facebook.com/");


        // wait cho element được invisible/undisplayed
        // element không hiển thị trên UI và không có trong DOM
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));

    }

    @Test
    public void TC_03_Presence_In_UI() {
        driver.get("https://www.facebook.com/");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']"))).click();

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("abc@gmail.com");

        explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
    }

    @Test
    public void TC_03_Presence_Not_In_UI() {
        driver.get("https://www.facebook.com/");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']"))).click();

        explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
    }

    @Test
    public void TC_04_Staleness() {
        driver.get("https://www.facebook.com/");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']"))).click();
        sleepInSecond(3);

        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));

        //Element đang có trong DOM (visible/ presence/ invisble in DOM)
        WebElement confirmEmailTextbox = driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']"));

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='reg_box']/parent::div/parent::div/parent::div/img"))).click();

        //Wait cho confirm email staleness (k còn trong DOM)
        explicitWait.until(ExpectedConditions.stalenessOf(confirmEmailTextbox));

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