package webdriver;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class Topic_20_Wait_Part_VII_Fluent {
    WebDriver driver;
    FluentWait<WebDriver> fluentWaitDriver;
    FluentWait<WebElement> fluentWaitElement;
    WebDriverWait explicitWait;
    JavascriptExecutor jsExecutor;

    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Fluent() {

        driver.get("https://automationfc.github.io/fluent-wait/");
        WebElement countdownTime = driver.findElement(By.id("javascript_countdown_time"));
        fluentWaitElement = new FluentWait<WebElement>(countdownTime);

        //wait với tổng thời gian là 15s
        fluentWaitElement.withTimeout(Duration.ofSeconds(15))

                //cơ chế tìm lại nếu chưa thỏa mãn điều kiện là 0.5s tìm lại 1 lần
                .pollingEvery(Duration.ofMillis(500))

                //nếu như trong thời gian tìm lại mà k thấy element sẽ bỏ qua
                .ignoring(NoSuchElementException.class)

                //xử lý điều kiện
                .until(new Function<WebElement, Boolean>() {

                    @Override //điều kiện
                    public Boolean apply(WebElement element) {
                        String text = element.getText();
                        System.out.println("Time = "+ text);
                        return text.endsWith("00");
                    }
                });


    }

    @Test
    public void TC_02_Visible() {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.findElement(By.xpath("//button[text()='Start']")).click();

        fluentWaitDriver = new FluentWait<WebDriver>(driver);
        fluentWaitDriver.withTimeout(Duration.ofSeconds(6))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        WebElement helloworldText = fluentWaitDriver.until(new Function<WebDriver, WebElement>() {

            @Override
            public WebElement apply(WebDriver driver) {

                return driver.findElement(By.xpath("//div[@id='finish']/h4"));
            }
        });
        Assert.assertEquals(helloworldText.getText(), "Hello World!");
    }

    @Test
    public void TC_03_Date_Picker() {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.findElement(By.xpath("//button[text()='Start']")).click();

        fluentWaitDriver = new FluentWait<WebDriver>(driver);
        fluentWaitDriver.withTimeout(Duration.ofSeconds(6))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .until(new Function<WebDriver, Boolean>() {

                    @Override
                    public Boolean apply(WebDriver driver) {

                        return driver.findElement(By.xpath("//div[@id='finish']/h4")).getText().equals("Hello World!");
                    }
                });
    }

    @Test
    public void TC_04_() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        waitForElementAndClick(By.xpath("//button[text()='Start']"));

        Assert.assertEquals(getWebElement(By.xpath("//div[@id='finish']/h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_05_() {
        driver.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        driver.findElement(By.xpath("//input[@id='txtUsername']")).sendKeys("Admin");
        driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("admin123");
        driver.findElement(By.id("btnLogin")).click();

        Assert.assertTrue(isQueryLoadedSuccess(driver));




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

    private long sumTime = 30;
    private long pollTime = 1;
    //findElement (Custom)
    public WebElement getWebElement(By locator) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(sumTime))
                .pollingEvery(Duration.ofSeconds(pollTime))
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
        return element;
    }

    public void waitForElementAndClick(By locator) {
        WebElement element = getWebElement(locator);
        element.click();
    }

    public void waitForElementAndClick_1(By locator) { FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(1))
            .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
        element.click();
    }

    public boolean waitForElementAndDisplayed(By locator) {
        WebElement element = getWebElement(locator);
        return element.isDisplayed();
    }

    public boolean waitForElementAndDisplayed_1(By locator) {
        WebElement element = getWebElement(locator);
        FluentWait<WebElement> wait = new FluentWait<WebElement>(element)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        boolean isDisplayed = wait.until(new Function<WebElement, Boolean>() {
            public Boolean apply(WebElement element) {
                boolean flag = element.isDisplayed();
                return flag;
            }
        });
        return isDisplayed;
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

}