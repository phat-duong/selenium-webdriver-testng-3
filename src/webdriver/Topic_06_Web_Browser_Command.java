package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Browser_Command {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        //Mở trình duyệt lên
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        //Khởi tạo browser
        driver = new ChromeDriver();
        //Set thời gian chờ để tìm được element
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Verify_Url() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "http://live.techpanda.org/index.php/customer/account/login/");

        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
        String url1 = driver.getCurrentUrl();
        Assert.assertEquals(url1, "http://live.techpanda.org/index.php/customer/account/create/");
    }

    @Test
    public void TC_02_Title() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
        String loginPageTitle = driver.getTitle();
        Assert.assertEquals(loginPageTitle, "Customer Login");

        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
        String registerPageTitle = driver.getTitle();
        Assert.assertEquals(registerPageTitle, "Create New Customer Account");
    }

    @Test
    public void TC_03_Navigation() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();

        //Back lại trang login
        driver.navigate().back();
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "http://live.techpanda.org/index.php/customer/account/login/");

        //Forward qua trang register
        driver.navigate().forward();
        String url1 = driver.getCurrentUrl();
        Assert.assertEquals(url1, "http://live.techpanda.org/index.php/customer/account/create/");
    }

    @Test
    public void TC_04_Page_Source() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

        String homePageSource = driver.getPageSource();
        Assert.assertTrue(homePageSource.contains("Login or Create an Account"));

        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
        String registerPageSource = driver.getPageSource();
        Assert.assertTrue(registerPageSource.contains("Create an Account"));
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
