package webdriver;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Xpath_Css_Part_I {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Register_With_Empty_Data() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        String actualMessage1 = driver.findElement(By.id("txtFirstname-error")).getText();
        String expectedMessage1 = "Vui lòng nhập họ tên";

        //verify
        Assert.assertEquals(actualMessage1, expectedMessage1);

        String actualMessage2 = driver.findElement(By.id("txtEmail-error")).getText();
        String expectedMessage2 = "Vui lòng nhập email";
        Assert.assertEquals(actualMessage2, expectedMessage2);

        String actualMessage3 = driver.findElement(By.id("txtCEmail-error")).getText();
        String expectedMessage3 = "Vui lòng nhập lại địa chỉ email";
        Assert.assertEquals(actualMessage3, expectedMessage3);

        String actualMessage4 = driver.findElement(By.id("txtPassword-error")).getText();
        String expectedMessage4 = "Vui lòng nhập mật khẩu";
        Assert.assertEquals(actualMessage4, expectedMessage4);

        String actualMessage5 = driver.findElement(By.id("txtCPassword-error")).getText();
        String expectedMessage5 = "Vui lòng nhập lại mật khẩu";
        Assert.assertEquals(actualMessage5, expectedMessage5);

        String actualMessage6 = driver.findElement(By.id("txtPhone-error")).getText();
        String expectedMessage6 = "Vui lòng nhập số điện thoại.";
        Assert.assertEquals(actualMessage6, expectedMessage6);

    }

    @Test
    public void TC_02_Register_With_Invalid_Email() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("John");
        driver.findElement(By.id("txtEmail")).sendKeys("123@@");
        driver.findElement(By.id("txtCEmail")).sendKeys("456@@");
        driver.findElement(By.id("txtPassword")).sendKeys("123456");
        driver.findElement(By.id("txtCPassword")).sendKeys("123456");
        driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        //verify
        String actualMessage1 = driver.findElement(By.id("txtEmail-error")).getText();
        String expectedMessage1 = "Vui lòng nhập email hợp lệ";
        Assert.assertEquals(actualMessage1, expectedMessage1);

        String actualMessage2 = driver.findElement(By.id("txtCEmail-error")).getText();
        String expectedMessage2 = "Email nhập lại không đúng";
        Assert.assertEquals(actualMessage2, expectedMessage2);

    }

    @Test
    public void TC_03_Register_With_Incorrect_Confirm_Email() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("John");
        driver.findElement(By.id("txtEmail")).sendKeys("john@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("456@@");
        driver.findElement(By.id("txtPassword")).sendKeys("123456");
        driver.findElement(By.id("txtCPassword")).sendKeys("123456");
        driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        //verify
        String actualMessage1 = driver.findElement(By.id("txtCEmail-error")).getText();
        String expectedMessage1 = "Email nhập lại không đúng";
        Assert.assertEquals(actualMessage1, expectedMessage1);
    }

    @Test
    public void TC_04_Register_With_Password_Less_Than_6_Characters() {

        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("John");
        driver.findElement(By.id("txtEmail")).sendKeys("john@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("john@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("123");
        driver.findElement(By.id("txtCPassword")).sendKeys("123");
        driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        //verify
        String actualMessage1 = driver.findElement(By.id("txtPassword-error")).getText();
        String expectedMessage1 = "Mật khẩu phải có ít nhất 6 ký tự";
        Assert.assertEquals(actualMessage1, expectedMessage1);

        String actualMessage2 = driver.findElement(By.id("txtCPassword-error")).getText();
        String expectedMessage2 = "Mật khẩu phải có ít nhất 6 ký tự";
        Assert.assertEquals(actualMessage2, expectedMessage2);

    }

    @Test
    public void TC_05_Register_With_Incorrect_Confirm_Password() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("John");
        driver.findElement(By.id("txtEmail")).sendKeys("john@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("john@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("123456");
        driver.findElement(By.id("txtCPassword")).sendKeys("1234567");
        driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        //verify
        String actualMessage1 = driver.findElement(By.id("txtCPassword-error")).getText();
        String expectedMessage1 = "Mật khẩu bạn nhập không khớp";
        Assert.assertEquals(actualMessage1, expectedMessage1);
    }

    @Test
    public void TC_06_Register_With_Invalid_Phone_Number() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("John");
        driver.findElement(By.id("txtEmail")).sendKeys("john@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("john@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("123456");
        driver.findElement(By.id("txtCPassword")).sendKeys("123456");
        driver.findElement(By.id("txtPhone")).sendKeys("012345678910");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        //verify
        String actualMessage1 = driver.findElement(By.id("txtPhone-error")).getText();
        String expectedMessage1 = "Số điện thoại phải từ 10-11 số.";
        Assert.assertEquals(actualMessage1, expectedMessage1);

        driver.findElement(By.id("txtPhone")).clear();
        driver.findElement(By.id("txtPhone")).sendKeys("12345678");
        String actualMessage2 = driver.findElement(By.id("txtPhone-error")).getText();
        String expectedMessage2 = "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019";
        Assert.assertEquals(actualMessage2, expectedMessage2);
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
}
