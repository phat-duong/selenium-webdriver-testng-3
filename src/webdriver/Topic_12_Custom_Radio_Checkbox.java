package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Custom_Radio_Checkbox {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_CustomRadio() {
        driver.get("https://material.angular.io/components/radio/examples");
        By winterRadioInput = By.xpath("//input[@value='Winter']");
        By winterRadioSpan = By.xpath("//input[@value='Winter']/preceding-sibling::span[@class='mat-radio-outer-circle']");
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(winterRadioSpan));
        driver.findElement(winterRadioSpan).click();
        Assert.assertTrue(driver.findElement(winterRadioInput).isSelected());
    }

    @Test
    public void TC_02_CustomRadio_JS() {
        driver.get("https://material.angular.io/components/radio/examples");
        By winterRadioInput = By.xpath("//input[@value='Winter']");

        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(winterRadioInput));
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(winterRadioInput));
        sleepInSecond(5);
        Assert.assertTrue(driver.findElement(winterRadioInput).isSelected());
    }

    @Test
    public void TC_03_Custom_Checkbox() {
        driver.get("https://material.angular.io/components/checkbox/examples");
        By checkedCheckbox = By.xpath("//label[contains(text(),'Checked')]/preceding-sibling::div/input");

//		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(checkedCheckbox));
        checkToCheckbox(checkedCheckbox);
        sleepInSecond(3);
        Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());
        uncheckToCheckbox(checkedCheckbox);
        Assert.assertFalse(driver.findElement(checkedCheckbox).isSelected());
    }

    @Test
    public void TC_04_Custom_Radio_1() {
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
        By radioHCM = By.xpath("//div[@aria-label='Hồ Chí Minh']");
        By radioSelectedHCM = By.xpath("//div[@aria-label='Hồ Chí Minh' and @aria-checked='true']");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Hồ Chí Minh' and @aria-checked='false']")).isDisplayed());
//		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(checkedCheckbox));
        selectToRadio(radioHCM);
        sleepInSecond(3);
        Assert.assertTrue(driver.findElement(radioSelectedHCM).isDisplayed());

    }

    @Test
    public void TC_05_Custom_Checkbox_1() {
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");

        List<WebElement> beforeCheckboxex = driver.findElements(By.xpath("//div[@role='checkbox']"));
        for (WebElement checkbox : beforeCheckboxex) {
            checkbox.click();
        }

        List<WebElement> afterCheckboxex = driver.findElements(By.xpath("//div[@role='checkbox' and @aria-checked='true']"));
        for (WebElement checkbox : afterCheckboxex) {
            Assert.assertTrue(checkbox.isDisplayed());
        }
    }

    public void checkToCheckbox(By by) {
        WebElement element = driver.findElement(by);
        if(!element.isSelected()) {
            jsExecutor.executeScript("arguments[0].click();", element);
        }
    }

    public void uncheckToCheckbox(By by) {
        WebElement element = driver.findElement(by);
        if(element.isSelected()) {
            jsExecutor.executeScript("arguments[0].click();", element);
        }
    }

    public void selectToRadio(By by) {
        WebElement element = driver.findElement(by);
        if(!element.isSelected()) {
            jsExecutor.executeScript("arguments[0].click();", element);
        }
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