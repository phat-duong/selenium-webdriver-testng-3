package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Handle_Frame_Iframe {
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
    public void TC_01_IFrame() {
        driver.get("https://kyna.vn/");

        //Switch qua iframe Facebook
        driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='face-content']/iframe")));

        String kynaFacebookLikes = driver.findElement(By.xpath("//a[@title='Kyna.vn']/parent::div/following-sibling::div")).getText();
        Assert.assertEquals(kynaFacebookLikes, "165K likes");

        //Switch về trang parent
        driver.switchTo().defaultContent();

        //Switch qua iframe chat
        driver.switchTo().frame(driver.findElement(By.id("cs_chat_iframe")));
        Assert.assertTrue(driver.findElement(By.cssSelector("div.button_bar")).isDisplayed());
        driver.findElement(By.cssSelector("div.button_bar")).click();
        sleepInSecond(5);

        driver.findElement(By.cssSelector("input.input_name")).sendKeys("abc");
        driver.findElement(By.cssSelector("input.input_phone")).sendKeys("123456789");
        new Select(driver.findElement(By.id("serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");

        driver.findElement(By.name("message")).sendKeys("abc");

        //Switch về trang parent
        String keyContent = "Excel";
        driver.switchTo().defaultContent();
        driver.findElement(By.id("live-search-bar")).sendKeys(keyContent);
        driver.findElement(By.cssSelector("button.search-button")).click();
        sleepInSecond(2);

        List<WebElement> courseNames = driver.findElements(By.cssSelector("div.content>h4"));
        for (WebElement course : courseNames) {
            System.out.println(course.getText().toLowerCase());
            Assert.assertTrue(course.getText().toLowerCase().contains(keyContent.toLowerCase()));
        }
    }

    @Test
    public void TC_02_Frame() {
        driver.get("https://netbanking.hdfcbank.com/netbanking/");

        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='login_page']")));
        driver.findElement(By.xpath("//div[@class='inputfield ibvt loginData']/input")).sendKeys("automation");
        driver.findElement(By.cssSelector("a.login-btn")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector("input#keyboard")).isDisplayed());
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