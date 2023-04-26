package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Wait_Part_V_Explicit {
    WebDriver driver;
    WebDriverWait explicitWait;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
        explicitWait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Invisible() {

        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.findElement(By.xpath("//button[text()='Start']")).click();

        // Wait cho loading progress bar biến mất
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='loading']")));

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_02_Visible() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.findElement(By.xpath("//button[text()='Start']")).click();

        // Wait cho tới khi text hiển thị
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='finish']/h4")));

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_03_Date_Picker() {
        driver.get(
                "https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

        explicitWait
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("ctl00_ContentPlaceholder1_Panel1")));

        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='2']"))).click();

//		explicitWait.until(
//				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id$='RadCalendar1'] div.raDiv")));

        Assert.assertTrue(isJQueryAndAjaxlconLoadedSuccess(driver));

        explicitWait
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("ctl00_ContentPlaceholder1_Panel1")));
        explicitWait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//td[@class='rcSelected']/a[text()='2']")));

        Assert.assertTrue(driver.findElement(By.xpath("//td[@class='rcSelected']/a[text()='2']")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1")).getText(),
                "Sunday, April 2, 2023");
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

    public boolean isQueryLoadedSuccess(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, 20);
        jsExecutor = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
            }
        };
        return explicitWait.until(jQueryLoad);
    }

    public boolean isJQueryAndAjaxlconLoadedSuccess(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, 20);
        jsExecutor = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> ajaxIconLoading = new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return $('.raDiv').is(':visible')").toString().equals("false");
            }

        };
        return explicitWait.until(jQueryLoad)&&explicitWait.until(ajaxIconLoading);
    }

    public boolean isJQueryAndPageLoadedSuccess(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, 20);
        jsExecutor = (JavascriptExecutor) driver;


        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return((Long)jsExecutor.executeScript("return jQuery.active")==0);
                }catch(Exception e) {
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };
        return explicitWait.until(jQueryLoad)&&explicitWait.until(jsLoad);
    }
}