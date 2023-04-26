package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_00_Demo {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");

    WebElement element;
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
        String tabID = driver.getWindowHandle();
        System.out.println(tabID);


    }

    //Post-condition
    @AfterClass
    public void afterClass() {
        //Tắt trình duyệt đi
        driver.quit();
    }

}
