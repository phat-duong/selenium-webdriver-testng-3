package webdriver;

import java.io.File;
import java.io.IOException;
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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Topic_19_Upload_Robot {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String separatorChar = File.separator;
    String uploadFolderLocation = projectPath + separatorChar + "uploadFiles" + separatorChar;
    String autoITFolderLocation = projectPath + separatorChar + "autoIT" + separatorChar;

    // Image name: verify
    String seleniumImage = "phuonghoanglua.jpg";
    String appiumImage = "2.png";

    // Image location: sendkeys
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
    public void TC_01_One_Upload_Per_Time() throws IOException, AWTException {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        // Copy duong dan cua file vao Clipboard
        StringSelection select = new StringSelection(seleniumImageLocation);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);

        // Click để bật open file dialog
        driver.findElement(By.cssSelector("span.fileinput-button")).click();
        sleepInSecond(2);
        // Load file
        loadFileByJavaRobot();

        // Copy duong dan cua file vao Clipboard
        select = new StringSelection(appiumImageLocation);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);

        // Click để bật open file dialog
        driver.findElement(By.cssSelector("span.fileinput-button")).click();
        sleepInSecond(2);
        // Load file
        loadFileByJavaRobot();

        // Uploading

        // C1
//		driver.findElement(By.xpath("//span[text()='Start upload']")).click();

        // C2
        List<WebElement> startButtons = driver.findElements(By.xpath("//span[text()='Start']"));
        for (WebElement start : startButtons) {
            start.click();
            sleepInSecond(1);
        }

        // Verify
        Assert.assertTrue(
                driver.findElement(By.xpath("//p[@class='name']//a[@title='" + seleniumImage + "']")).isDisplayed());
//		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']//a[@title='"+ appiumImage + "']")).isDisplayed());
    }

    @Test
    public void TC_02_Multiple_Upload_Per_Time() {

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

    public void loadFileByJavaRobot() throws AWTException {
        // Load file
        Robot robot = new Robot();

        // Nhan phim Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Nhan xuong Ctrl - V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);

        // Nha Ctrl - V
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        sleepInSecond(1);

        // Nhan Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        sleepInSecond(1);
    }
}