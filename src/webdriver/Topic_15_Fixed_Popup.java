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

public class Topic_15_Fixed_Popup {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Fixed_Popup() {
        driver.get("https://ngoaingu24h.vn/");
        //Nên define thành 1 biến By, Không nên define thành 1 biến WebElement
        By modalLoginPopup = By.xpath("//div[@id='modal-login-v1']");

        //Kiểm tra không hiển thị
        Assert.assertFalse(driver.findElement(modalLoginPopup).isDisplayed());

        //Click button Login
        driver.findElement(By.cssSelector("button.login_")).click();
        sleepInSecond(3);

        //Kiểm tra hiển thị
        Assert.assertTrue(driver.findElement(modalLoginPopup).isDisplayed());

        driver.findElement(By.id("account-input")).sendKeys("automationfc");
        driver.findElement(By.id("password-input")).sendKeys("123456");
        driver.findElement(By.cssSelector("button.btn-login-v1")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='modal-login-v1']//div[@class='row error-login-panel']")).getText(), "Tài khoản không tồn tại!");

        driver.findElement(By.xpath("//div[@id='modal-login-v1']//button[@class='close']")).click();
        sleepInSecond(2);

        //Kiểm tra không hiển thị
        Assert.assertFalse(driver.findElement(modalLoginPopup).isDisplayed());
    }

    @Test
    public void TC_02_Random_Popup_In_DOM() {
        driver.get("https://www.facebook.com/");
    }

    @Test
    public void TC_03_Random_Popup_Not_In_DOM() {
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