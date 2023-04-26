package webdriver;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_19_Upload_Sendkey {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String separatorChar = File.separator;
    String uploadFolderLocation = projectPath + separatorChar + "uploadFiles" + separatorChar;

    //Image name: verify
    String seleniumImage = "phuonghoanglua.jpg";
    String appiumImage = "cauvong.jpg";

    //Image location: sendkeys
    String seleniumImageLocation = uploadFolderLocation + seleniumImage;
    String appiumImageLocation = uploadFolderLocation + appiumImage;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_One_Upload_Per_Time() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");
        By uploadFileBy = By.xpath("//input[@type='file']");
        //Load file
        driver.findElement(uploadFileBy).sendKeys(seleniumImageLocation);
        driver.findElement(uploadFileBy).sendKeys(appiumImageLocation);
        sleepInSecond(3);

        //Uploading

        //C1
//		driver.findElement(By.xpath("//span[text()='Start upload']")).click();

        //C2
        List<WebElement> startButtons = driver.findElements(By.xpath("//span[text()='Start']"));
        for (WebElement start : startButtons) {
            start.click();
            sleepInSecond(1);
        }

        //Verify
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[@title='"+ seleniumImage + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[@title='"+ appiumImage + "']")).isDisplayed());
    }

    @Test
    public void TC_02_Multiple_Upload_Per_Time() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");
        By uploadFileBy = By.xpath("//input[@type='file']");

        //Load multiple file -> cộng thêm "\n"
        driver.findElement(uploadFileBy).sendKeys(seleniumImageLocation + "\n" + appiumImageLocation);
        sleepInSecond(3);

        //Uploading

        //C1
//		driver.findElement(By.xpath("//span[text()='Start upload']")).click();

        //C2
        List<WebElement> startButtons = driver.findElements(By.xpath("//span[text()='Start']"));
        for (WebElement start : startButtons) {
            start.click();
            sleepInSecond(1);
        }

        //Verify
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[@title='"+ seleniumImage + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[@title='"+ appiumImage + "']")).isDisplayed());
    }

    @Test
    public void TC_03_() {

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