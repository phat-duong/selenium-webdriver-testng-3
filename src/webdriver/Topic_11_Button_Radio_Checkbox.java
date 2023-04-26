package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Button_Radio_Checkbox {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        driver = new FirefoxDriver();
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Button() {
        driver.get("https://www.fahasa.com/customer/account/create");
        By btnLogin = By.cssSelector("button.fhs-btn-login");
        By tabLogin = By.cssSelector("li.popup-login-tab-login");

        driver.findElement(tabLogin).click();

        Assert.assertFalse(driver.findElement(btnLogin).isEnabled());

        driver.findElement(By.id("login_username")).sendKeys("abc@gmail.com");
        driver.findElement(By.id("login_password")).sendKeys("123456");
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(btnLogin).isEnabled());

        driver.navigate().refresh();

        driver.findElement(tabLogin).click();
        jsExecutor.executeScript("arguments[0].removeAttribute('disabled')", driver.findElement(btnLogin));
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(btnLogin).isEnabled());

        String btnLoginBGColor = driver.findElement(btnLogin).getCssValue("background-color");
        //RGB
        Assert.assertEquals(btnLoginBGColor, "rgb(201, 33, 39)");

        //Hexa
        String btnLoginHexaColor = Color.fromString(btnLoginBGColor).asHex().toUpperCase();
        Assert.assertEquals(btnLoginHexaColor, "#C92127");

        driver.findElement(btnLogin).click();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Số điện thoại/Email']/following-sibling::div[@class='fhs-input-alert']")).getText(), "Thông tin này không thể để trống");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Mật khẩu']/following-sibling::div[@class='fhs-input-alert']")).getText(), "Thông tin này không thể để trống");


    }

    @Test
    public void TC_02_Radio() {
        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
        By twoPetroRadio = By.xpath("//input[@id='engine3']");
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(twoPetroRadio));
//        sleepInSecond(3);
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(twoPetroRadio));
//        driver.findElement(twoPetroRadio).click();
        Assert.assertTrue(driver.findElement(twoPetroRadio).isSelected());
        Assert.assertEquals(driver.findElement(By.xpath("//input[@id='engine3']/following-sibling::label")).getText(), "2.0 Petrol, 147kW");

    }

    @Test
    public void TC_03_Checkbox() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
        By luggageCheckbox = By.xpath("//input[@id='eq3']");
        By dualZoneCheckbox = By.xpath("//input[@id='eq5']");
        By titleText = By.xpath("//h4[text()='Choose Extra Equipment']");

        driver.findElement(luggageCheckbox).click();
        Assert.assertTrue(driver.findElement(luggageCheckbox).isSelected());
        driver.findElement(luggageCheckbox).click();
        Assert.assertFalse(driver.findElement(luggageCheckbox).isSelected());


        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(titleText));
        driver.findElement(dualZoneCheckbox).click();
        Assert.assertTrue(driver.findElement(dualZoneCheckbox).isSelected());

    }

    @Test
    public void TC_04_MutipleCheckbox() {
        driver.get("https://automationfc.github.io/multiple-fields/");

        List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@class='form-checkbox']"));
        for (WebElement checkbox : checkboxes) {
            if(!checkbox.isSelected()) {
                checkbox.click();
//				sleepInSecond(1);
            }

        }

        for (WebElement checkbox : checkboxes) {
            Assert.assertTrue(checkbox.isSelected());
        }

        for (WebElement checkbox : checkboxes) {
            if(checkbox.isSelected()) {
                checkbox.click();
//				sleepInSecond(1);
            }
        }

        for (WebElement checkbox : checkboxes) {
            Assert.assertFalse(checkbox.isSelected());
        }

    }

    @Test
    public void TC_05_CustomRadio() {
        driver.get("https://material.angular.io/components/radio/examples");

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

    public void checkToCheckbox(By by){
        if(!driver.findElement(by).isSelected()){
            driver.findElement(by).click();
        }
    }

    public void uncheckToCheckbox(By by){
        if(driver.findElement(by).isSelected()){
            driver.findElement(by).click();
        }
    }
}