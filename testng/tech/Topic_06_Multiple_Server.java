package tech;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_06_Multiple_Server {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    By emailTextbox = By.xpath("//*[@id='email']");
    By passwordTextbox = By.xpath("//*[@id='pass']");
    By loginButton = By.xpath("//*[@id='send2']");

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {

        switch (browserName){
            case "chrome":
                System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Browser name is not valid");
                break;
        }

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Parameters("server")
    @Test
    public void TC_01_LoginToSystem(String serverName)  {
        String serverUrl = getServerUrl(serverName);
        System.out.println(serverUrl);
        driver.get("http://" + serverUrl + "/index.php/customer/account/login/");

        driver.findElement(emailTextbox).sendKeys("selenium_11_01@gmail.com");
        driver.findElement(passwordTextbox).sendKeys("111111");
        driver.findElement(loginButton).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//p")).getText().contains("selenium_11_01@gmail.com"));

        // ....

        driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
        driver.findElement(By.xpath("//a[text()='Log Out']")).click();
    }

    private String getServerUrl(String serverName){
        switch (serverName){
            case "DEV":
                serverName = "live.techpanda.org";
                break;
            case "STAGING":
                serverName = "stg.techpanda.org";
                break;
            case "PRODUCTION":
                serverName = "prod.techpanda.org";
                break;
            default:
                System.out.println("Server name is not valid");
                break;
        }
        return serverName;
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
