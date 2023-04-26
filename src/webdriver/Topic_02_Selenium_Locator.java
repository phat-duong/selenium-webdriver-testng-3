package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Selenium_Locator {

    //biến toàn cục
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");

    //Pre-condition
    @BeforeClass
    public void beforeClass() {
        //Mở trình duyệt lên
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        //Khởi tạo browser
        driver = new ChromeDriver();
        //Set thời gian chờ để tìm được element
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        //Mở app lên
        driver.get("https://www.facebook.com/r.php");
    }

    @Test
    public void TC_01_ID() {
        driver.findElement(By.id("password_step_input")).sendKeys("123456");
        sleepInSecond(3);
    }

    @Test
    public void TC_02_Class() {
        driver.findElement(By.className("registration_redesign")).isDisplayed();
        sleepInSecond(3);
    }

    @Test
    public void TC_03_Name() {
        driver.findElement(By.name("reg_email__")).sendKeys("aaa@gmail.com");
        sleepInSecond(3);
    }

    @Test
    public void TC_04_LinkText() {
        driver.findElement(By.linkText("Already have an account?")).click();
        sleepInSecond(3);
    }

    @Test
    public void TC_05_PartialLinkText() {
        driver.findElement(By.partialLinkText("account")).click();
        sleepInSecond(3);
    }

    @Test
    public void TC_06_TagName() {
        //bao nhiêu thẻ input ở màn hình quên mật khẩu
        int inputSize = driver.findElements(By.tagName("input")).size();
        System.out.println("Input size = " + inputSize);

    }

    @Test
    public void TC_07_Css() {
        //Css thông thường có cú pháp: tagName[Atrribute='Value']
        driver.findElement(By.cssSelector("input[id='identify_email']")).sendKeys("123456");
        sleepInSecond(3);
    }

    @Test
    public void TC_08_Xpath() {
        //Xpath thông thường: //tagName[@Atrribute='Value']
        driver.findElement(By.xpath("//button[@id='did_submit']")).click();
        sleepInSecond(3);
    }

    //Post-condition
    @AfterClass
    public void afterClass() {
        //Tắt trình duyệt đi
        driver.quit();
    }

    //gọi hàm khác để dùng
    public void sleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
