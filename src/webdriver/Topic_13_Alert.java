package webdriver;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Alert {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    Alert alert;
    WebDriverWait explicitWait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String authenChrome = projectPath + "\\autoIT\\authen_chrome.exe";
    String authenFirefox = projectPath + "\\autoIT\\authen_firefox.exe";

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("Drive ID = " + driver.toString());

        jsExecutor = (JavascriptExecutor) driver;
        explicitWait = new WebDriverWait(driver, 30);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Accept_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        //switch qua: alert/frame(iframe)/window
//		alert = driver.switchTo().alert();

        //wait trước khi switch qua alert
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        Assert.assertEquals(alert.getText(), "I am a JS Alert");

        alert.accept();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked an alert successfully");

    }

    @Test
    public void TC_02_Confirm_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();


        //wait trước khi switch qua alert
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        Assert.assertEquals(alert.getText(), "I am a JS Confirm");

        alert.accept();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked: Ok");

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        alert.dismiss();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked: Cancel");
    }

    @Test
    public void TC_03_Prompt_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        String text = "testing";

        //wait trước khi switch qua alert
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        Assert.assertEquals(alert.getText(), "I am a JS prompt");
        alert.sendKeys(text);
        alert.accept();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You entered: "+ text);

        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        alert.dismiss();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You entered: null");
    }

    @Test
    public void TC_04_Authentication_Alert() {
        String username = "admin";
        String password = "admin";

        //Chưa xử lý: http://the-internet.herokuapp.com/basic_auth
        //Sau xử lý: http://username:password@the-internet.herokuapp.com/basic_auth

        //http://user:password@url
        driver.get("http://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth");

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='content']//p")).getText().trim(), "Congratulations! You must have the proper credentials.");
    }

    @Test
    public void TC_05_Authentication_Alert_1() {

        String username = "admin";
        String password = "admin";
        driver.get("http://the-internet.herokuapp.com");
        //http://user:password@url
        WebElement authenticationLink = driver.findElement(By.xpath("//a[text()='Basic Auth']"));
        driver.get(getAuthenticationUrl(authenticationLink.getAttribute("href"), username, password));

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='content']//p")).getText().trim(), "Congratulations! You must have the proper credentials.");
    }

    @Test
    public void TC_06_Authentication_Alert_AutoIT() throws IOException {

        String username = "admin";
        String password = "admin";

        //AutoIT execute
//		if(driver.toString().contains("firefox")) {
//			Runtime.getRuntime().exec(new String[] {authenFirefox, username, password});
//		} else if (driver.toString().contains("chrome")) {
//			Runtime.getRuntime().exec(new String[] {authenChrome, username, password});
//		} else {
//			throw new RuntimeException("Browser is not supported");
//		}

        Runtime.getRuntime().exec(new String[] {authenFirefox, username, password});

        driver.get("http://the-internet.herokuapp.com/basic_auth");
        sleepInSecond(5);

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='content']//p")).getText().trim(), "Congratulations! You must have the proper credentials.");
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

    public String getAuthenticationUrl(String url, String username, String password) {
        String[] urlValues = url.split("//");
        url = urlValues[0] + "//" + username + ":" + password + "@" + urlValues[1];
        return url;
    }
}