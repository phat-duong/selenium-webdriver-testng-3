package loop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_08_Loop_Register {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");

    String loginPageUrl, userID, userPassword, email;

    @BeforeMethod
    public void beforeMethod() {

        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        loginPageUrl = "http://demo.guru99.com/v4";
        email = "abc" + getRandomNumber() + "@gmail.net";
    }

    @Test(invocationCount = 3)
    public void Register()  {
        driver.get(loginPageUrl);

        driver.findElement(By.xpath("//a[text()='here']")).click();
        driver.findElement(By.name("emailid")).sendKeys(email);
        driver.findElement(By.name("btnLogin")).click();
        userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
        userPassword = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();

        //Sau khi đăng ký xong thì lấy thông tin userID/ userPassword lưu vào 1 file nào đó
        //In ra console
        System.out.println(userID);
        System.out.println(userPassword);

    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    public int getRandomNumber() {
        Random rand = new Random();
        // random 1 số có 5 chữ số từ 0 -> 99999
        return rand.nextInt(99999);
    }
}
