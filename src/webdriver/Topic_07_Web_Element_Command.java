package webdriver;

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

public class Topic_07_Web_Element_Command {
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
    public void TC_01_Check_Element_Display() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='mail']"));
        boolean status = emailTextbox.isDisplayed();
        System.out.println(status);
        if (status) {
            System.out.println("Element is displayed");
            emailTextbox.sendKeys("Automation Testing");
        } else {
            System.out.println("Element is not displayed");
        }

        WebElement ageUnder18 = driver.findElement(By.xpath("//input[@id='under_18']"));
        boolean status1 = ageUnder18.isDisplayed();
        System.out.println(status1);
        if (status1) {
            System.out.println("Element is displayed");
            ageUnder18.click();
        } else {
            System.out.println("Element is not displayed");
        }

        WebElement educationTextarea = driver.findElement(By.name("user_edu"));
        boolean status2 = educationTextarea.isDisplayed();
        System.out.println(status2);
        if (status2) {
            System.out.println("Element is displayed");
            educationTextarea.sendKeys("Automation Testing");
        } else {
            System.out.println("Element is not displayed");
        }
    }

    @Test
    public void TC_02_Enabled() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        WebElement password = driver.findElement(By.id("disable_password"));
        boolean status = password.isEnabled();
        System.out.println(status);
        if (status) {
            System.out.println("Element is enabled");
        } else {
            System.out.println("Element is disabled");
        }

        WebElement email = driver.findElement(By.id("mail"));
        boolean status1 = email.isEnabled();
        System.out.println(status1);
        if (status1) {
            System.out.println("Element is enabled");
        } else {
            System.out.println("Element is disabled");
        }
    }

    @Test
    public void TC_03_Selected() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        WebElement ageUnder18 = driver.findElement(By.xpath("//input[@id='under_18']"));
        ageUnder18.click();
        boolean status1 = ageUnder18.isSelected();
        System.out.println(status1);
        if (status1) {
            System.out.println("Element is selected");
        } else {
            System.out.println("Element is not selected");
        }

        WebElement javaCheckbox = driver.findElement(By.xpath("//input[@id='java']"));
        javaCheckbox.click();
        boolean status2 = javaCheckbox.isSelected();
        System.out.println(status2);
        if (status2) {
            System.out.println("Element is selected");
        } else {
            System.out.println("Element is not selected");
        }

        javaCheckbox.click();
        boolean status3 = javaCheckbox.isSelected();
        System.out.println(status3);
        if (status3) {
            System.out.println("Element is selected");
        } else {
            System.out.println("Element is not selected");
        }
    }

    @Test
    public void TC_04_MailChimp() {
        driver.get("https://login.mailchimp.com/signup/");

        driver.findElement(By.id("email")).sendKeys("abc@gmail.com");
        driver.findElement(By.id("new_username")).sendKeys("abc");
        WebElement passwordTextbox = driver.findElement(By.xpath("//input[@id='new_password']"));
        WebElement signupButton = driver.findElement(By.xpath("//button[@id='create-account']"));
        passwordTextbox.click();

        //1 - Lowercase
        passwordTextbox.sendKeys("auto");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='line password-requirements always-open']//li[text()='One lowercase character']")).isDisplayed());
        Assert.assertFalse(signupButton.isEnabled());

        //2 - Uppercase
        passwordTextbox.clear();
        passwordTextbox.sendKeys("Auto");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='line password-requirements always-open']//li[text()='One uppercase character']")).isDisplayed());
        Assert.assertFalse(signupButton.isEnabled());

        //3 - Number
        passwordTextbox.clear();
        passwordTextbox.sendKeys("1234");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed' and text() = 'One number']")).isDisplayed());
        Assert.assertFalse(signupButton.isEnabled());

        //4 - Special Character
        passwordTextbox.clear();
        passwordTextbox.sendKeys("!@#$");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='line password-requirements always-open']//li[text()='One special character']")).isDisplayed());
        Assert.assertFalse(signupButton.isEnabled());

        //5 - 8 characters
        passwordTextbox.clear();
        passwordTextbox.sendKeys("12345678");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed' and text() = '8 characters minimum']")).isDisplayed());
        Assert.assertFalse(signupButton.isEnabled());

        //6 - Combine
        passwordTextbox.clear();
        passwordTextbox.sendKeys("aA1!123456");
        Assert.assertTrue(driver.findElement(By.xpath("//h4[contains(text(),'Your password')]")).isDisplayed());
        Assert.assertTrue(signupButton.isEnabled());
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
